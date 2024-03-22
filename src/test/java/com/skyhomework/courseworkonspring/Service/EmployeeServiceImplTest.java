package com.skyhomework.courseworkonspring.Service;

import com.skyhomework.courseworkonspring.Exception.EmployeeAlreadyAddedException;
import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.Exception.EmployeeStorageIsFullException;
import com.skyhomework.courseworkonspring.model.Employee;
import com.skyhomework.courseworkonspring.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeServiceImplTest {
    //Константы для тестов
    private static final String DEFAULT_NAME = "Ivan";
    private static final String DEFAULT_LAST_NAME = "Ivanov";
    private static final int DEFAULT_DEPARTMENT = 1;
    private static final int DEFAULT_SALARY = 100_000;
    private static final int DEFAULT_SIZE_EMPLOYEE_COLLECTION = 4;
    private static final String ERROR_MESSAGE_ILLEGALARGUMENT_EXCEPTION = "Переданы некорректные аргументы";
    private static final String ERROR_MESSAGE_EMPLOYEE_NOT_FOUND = "Такого работника не существует";
    private static final String ERROR_MESSAGE_LIMIT_EMPLOYEE = "Нельзя больше добавить сотрудников, превышен лимит работников";
    private static final String ERROR_MESSAGE_EMPLOYEE_ALREADY_EXSIST = "Такой сотрудник уже есть в списке";
    //сервис который тестируем
    final EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    //добавим поля для более удобного использования
    // Работник с корректными параметрами
    private Employee employeeWIthCorrectParametr;
    //Перед каждым тестом запускаем метод для инициалиции полей тестового класса
    @BeforeEach
    public void set_up(){
        employeeWIthCorrectParametr = new Employee(DEFAULT_NAME,
                DEFAULT_LAST_NAME,
                DEFAULT_DEPARTMENT,
                DEFAULT_SALARY);
        employeeService.add("Nick", "Nickolson", 1, 120_000);
        employeeService.add("Rick", "Grime", 2, 110_000);
        employeeService.add("Robert", "Potision", 4, 140_000);
        employeeService.add("Fill", "Baskov", 3, 90_000);

    }
    //----------------------------START UNITTEST ------------------------------------------------------//
    // проверяем что метод добавления возращает добавленног работника
    // и сохраняет
    @Test
    @DisplayName("Метод добавляет значение и возращает добавленный объект")
    public void whenAddNewEmployeesShouldReturnAddedEmployee(){
        final Employee expected = employeeWIthCorrectParametr;
        final Employee actual = employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        assertEquals(expected, actual);
        assertEquals(DEFAULT_SIZE_EMPLOYEE_COLLECTION + 1, employeeService.getEmployee().size());
    }
    // проверяем что метод удаления возращает удаленного работника
    // и удаляет его из хранения
    @Test
    @DisplayName("Метод удаляет из хранения и возращает удаленный объект")
    public void whenRemoveEmployeesShouldReturnRemovedEmployee(){
        final Employee expected = employeeWIthCorrectParametr;
        //добавим работника в список работников чтобы проверить метод
        employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        final Employee actual = employeeService.remove(DEFAULT_NAME, DEFAULT_LAST_NAME);
        assertEquals(expected, actual);
        assertEquals(DEFAULT_SIZE_EMPLOYEE_COLLECTION, employeeService.getEmployee().size());
    }
    // проверяем что поиск возращает работника
    @Test
    @DisplayName("Метод поиска возращает добавленный объект")
    public void whenFindEmployeesShouldReturnEmployee(){
        final Employee expected = employeeWIthCorrectParametr;
        //добавим работника в список работников чтобы проверить метод
        employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        final Employee actual = employeeService.find(DEFAULT_NAME, DEFAULT_LAST_NAME);
        assertEquals(expected, actual);
    }
    //Метод возращающий поток из нескольких аргументов с разными регистрами букв
    // имени и фамилии
    public static Stream<Arguments> employeesSourceWithDifferentUpperLowerCase(){
        return Stream.of(
                Arguments.of("Ivan", "Ivanov", 2, 125_000),
                Arguments.of("Oleg", "Ivanov", 2, 100_000),
                Arguments.of("ivan", "ivanov", 3, 50_000),
                Arguments.of("IVAN", "IVANOV", 2, 90_000)
        );
    }
    // проверяем что метод добавляет имя[0]/фамилия[0] в верхних регистрах
    // вне зависимости от переданных регистров
    @ParameterizedTest
    @MethodSource("employeesSourceWithDifferentUpperLowerCase")
    @DisplayName("Проверка работы метода с параметрами в разных регистрах")
    public void addNameAndLNameWithIfDifferentRegistr(String fName, String lName, int deprtID, int salary){
        // приведем имя/фамилию к корректному значению
        // где они начинаются с большой буквы
        // приватным методом
        String expectedName = capitilizeString(fName);
        String expectedLastName = capitilizeString(lName);

        Employee expected = new Employee(expectedName, expectedLastName, deprtID, salary);
        Employee actual = employeeService.add(fName, lName, deprtID, salary);
        assertEquals(expected, actual);
    }
    //----------------------------END UNITTEST -----------------------------------------------------//

    //----------------------------START ERROR TEST------------------------------------------------------//
    // Метод возращающий поток из нескольки аргументов где отсутвует один/несколько параметров
    // или переданы не корректные значения
    public static Stream<Arguments> employeeSourceWithIncorrectNameOrLastName(){
        return Stream.of(
                Arguments.of("Ivan4", "Ivanov", 1, 100_000),
                Arguments.of("Ivan ", "Ivanov", 1, 100_000),
                Arguments.of("", "Ivanov", 1, 100_000),
                Arguments.of("Ivan", "", 1, 100_000),
                Arguments.of("Ivan", "I-vanov", 1, 100_000),
                Arguments.of("i_van", "Ivanov", 1, 100_000),
                Arguments.of(null, "Ivanov", 1, 100_000),
                Arguments.of("Ivan", null, 1, 100_000),
                Arguments.of(null, null, 1, 100_000)
        );
    }
    //Проверяем работу метода на исключение если параметр fName/lName некорректный или null
    @ParameterizedTest
    @MethodSource("employeeSourceWithIncorrectNameOrLastName")
    @DisplayName("Метод добавления выбрасывает исключения при передаче некорректных аргументов")
    public void throwExceptionWhenTryAddEmployeeWithWithIllegalArgs(String fName, String lName, int departID, int salary){
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> employeeService.add(fName, lName,departID, salary));
        assertEquals(ERROR_MESSAGE_ILLEGALARGUMENT_EXCEPTION, thrown.getMessage());
    }
    //
    @Test
    @DisplayName("Исключение лимит сотрудников")
    public void throwExceptionWhenLimitOfCollectionAchive(){
        employeeService.setMaxEmployeesInCompany(DEFAULT_SIZE_EMPLOYEE_COLLECTION);
        EmployeeStorageIsFullException thrown = assertThrows(EmployeeStorageIsFullException.class,
                ()-> employeeService.add("Jonh", "Sinovich", 4, 140_000));
        assertEquals(ERROR_MESSAGE_LIMIT_EMPLOYEE, thrown.getMessage());
    }
    // Тестируем выброс исключения если такой сотрудник уже есть
    @Test
    @DisplayName("Исключение если сотрудник уже есть в списке")
    public void throwExceptionIfEmployeeIsExsistInMap(){
        employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        EmployeeAlreadyAddedException thrown = assertThrows(EmployeeAlreadyAddedException.class,
                () -> employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY));
        assertEquals(ERROR_MESSAGE_EMPLOYEE_ALREADY_EXSIST, thrown.getMessage());
    }
    //Тестируем выброс исключение если не найден такой работник
    @Test
    @DisplayName("Иключение если не найден сотрудник")
    public void throwExceptionWhenTryFindEmployeeThatNotExsist(){
        employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        EmployeeNotFoundException thrown = assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.find("Alex", "Alekseev"));
        assertEquals(ERROR_MESSAGE_EMPLOYEE_NOT_FOUND, thrown.getMessage());
    }
    //Тестируем выброс исключение если не такого работника чтобы удалить
    @Test
    @DisplayName("исключение если нет сотрудника для удаления")
    public void throwExceptionWhenTryRemoveEmployeeThatNotExsist(){
        employeeService.add(DEFAULT_NAME, DEFAULT_LAST_NAME, DEFAULT_DEPARTMENT, DEFAULT_SALARY);
        EmployeeNotFoundException thrown = assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.remove("Alex", "Alekseev")
        );
        assertEquals(ERROR_MESSAGE_EMPLOYEE_NOT_FOUND, thrown.getMessage());
    }
    //----------------------------END ERROR TEST------------------------------------------------------//

    //----------------------------START HELPER UTILS------------------------------------------------------//
    // вспомогательный метод преобразования строки к формату: Sssss
    private String capitilizeString(String str) {
        // приводим строку к нижнему регистру
        String lowerStr = str.toLowerCase();
        // получаем первый символ и приводим к верхнему регистру
        char firstStrUpper = str.substring(0, 1).toUpperCase().charAt(0);
        //меняем первый символ
        return lowerStr.replace(lowerStr.charAt(0), firstStrUpper);
    }
    //----------------------------END HELPER UTILS------------------------------------------------------//
}
