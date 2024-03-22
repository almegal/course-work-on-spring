package com.skyhomework.courseworkonspring.Service;

import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.model.Employee;
import com.skyhomework.courseworkonspring.service.DepartmentServiceImp;
import com.skyhomework.courseworkonspring.service.EmployeeServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImpTest {
    @Mock
    private EmployeeServiceImpl employeeServiceImplMock;
    @InjectMocks
    private DepartmentServiceImp out;
    private Map<String, Employee> mapEmployee;
    // константы
    private static final String ERROR_MESSAGE_DEPARTMENT_NOT_EXSIST = "такого отдела не существует";
    @BeforeEach
    public void set_up(){
        //перед каждым запуском теста заполняем хранилище сотрудников
        mapEmployee = Map.of(
                "Ivan Ivanov", new Employee("Ivan","Ivanov", 1, 125_000),
                "Ivan Kolodov", new Employee("Ivan", "Kolodov", 3, 55_000),
                "Semen Semenov", new Employee("Semen", "Semenov", 1, 125_000),
                "Andrey Berezin", new Employee("Andrey", "Berezin", 2, 215_000),
                "Dima Petrov", new Employee("Dima", "Petrov", 1, 165_000),
                "Vasilyi Fedorov", new Employee("Vasilyi", "Fedorov", 2, 195_000),
                "Kirill Kuznecov", new Employee("Kirill", "Kuznecov", 2, 155_000),
                "Maksim Maksimov", new Employee("Maksim", "Maksimov", 3, 125_000),
                "Aleksei Alekseevich", new Employee("Aleksei", "Alekseevich", 3, 125_000),
                "Aleksandr Ovechkin", new Employee("Aleksandr", "Ovechkin", 4, 500_000)
        );
    }
    //----------------------------START UNITTEST -----------------------------------------------------//
    // Возращает список всех работников групиррованных по отделу
    @Test
    @DisplayName("Должен вернуть сотрудников групированных по отделу")
    public void shouldReturnEmployessGroupingByDeaprtment(){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);

        Map<Integer, List<Employee>> expected = new HashMap<>(Map.of(
                1, List.of(
                        new Employee("Ivan","Ivanov", 1, 125_000),
                        new Employee("Semen", "Semenov", 1, 125_000),
                        new Employee("Dima", "Petrov", 1, 165_00)),
                2,List.of(
                        new Employee("Andrey", "Berezin", 2, 215_000),
                        new Employee("Vasilyi", "Fedorov", 2, 195_000),
                        new Employee("Kirill", "Kuznecov", 2, 155_000)),
                3, List.of(
                        new Employee("Ivan", "Kolodov", 3, 55_000),
                        new Employee("Maksim", "Maksimov", 3, 125_000),
                        new Employee("Aleksei", "Alekseevich", 3, 125_000)),
                4, List.of(
                      new Employee("Aleksandr", "Ovechkin", 4, 500_000))
                ));
        Map<Integer, List<Employee>> actual = out.getAllEmployeeSplitDepartment();
        Assertions.assertTrue(mapAreEquals(expected, actual));
        verify(employeeServiceImplMock, only()).getEmployee();
    }
    //поток возращающий номер отдела и махксимальную сумму зп в отделе
    public static Stream<Arguments> argsProviderForMaxSalaryInDepartment() {
        return Stream.of(
                Arguments.of(1, 165_000),
                Arguments.of(2, 215_000),
                Arguments.of(3, 125_000)
        );
    }
    // Возращает максимальную зарплату по отделу
    @ParameterizedTest
    @MethodSource("argsProviderForMaxSalaryInDepartment")
    @DisplayName("Должен вернуть максимальную зп в отделе")
    public void getMaxSalaryInDepartment(int id, int expectedSalary){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);
        Employee actual = out.getMaxSalaryInDepartment(id);
        assertEquals(expectedSalary, actual.getSalary());
        verify(employeeServiceImplMock, only()).getEmployee();
    }
    // метод getMaxSalaryInDepartment должен выбрасить исключение если отдела не существует
    @Test
    @DisplayName("Должен выбросить исключение при не существующем отделе")
    public void shouldReturnExceptionIfDepartmentNotExsistInMaxSalaryMethod(){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);
        EmployeeNotFoundException actual = assertThrows(EmployeeNotFoundException.class, () -> {
            out.getMaxSalaryInDepartment(111);
        });
        assertEquals(ERROR_MESSAGE_DEPARTMENT_NOT_EXSIST, actual.getMessage());
    }
    //поток возращающий номер отдела и минимальную сумму зп в отделе
    public static Stream<Arguments> argsProviderForMinSalaryInDepartment() {
        return Stream.of(
                Arguments.of(1, 125_000),
                Arguments.of(2, 155_000),
                Arguments.of(3, 55_000)
        );
    }
    // Возращают минимальную зарплату по отделу
    @ParameterizedTest
    @MethodSource("argsProviderForMinSalaryInDepartment")
    public void getMinSalaryInDepartment(int id, int expectedSalary){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);
        Employee actual = out.getMinSalaryInDepartment(id);
        assertEquals(expectedSalary, actual.getSalary());
        verify(employeeServiceImplMock, only()).getEmployee();
    }
    // метод getMinSalaryInDepartment должен выбрасить исключение если отдела не существует
    @Test
    @DisplayName("Должен выбросить исключение при не существующем отделе")
    public void shouldReturnExceptionIfDepartmentNotExsistInMinSalaryMethod(){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);
        EmployeeNotFoundException actual = assertThrows(EmployeeNotFoundException.class, () -> {
            out.getMinSalaryInDepartment(111);
        });
        assertEquals(ERROR_MESSAGE_DEPARTMENT_NOT_EXSIST, actual.getMessage());
    }
    // поток возращающий аргументы номер отдела и сумму всех запрлат из mapEmployee
    public static Stream<Arguments> argsProviderForSalarySumByDepartment() {
        return Stream.of(
                Arguments.of(1, 415_000),
                Arguments.of(2, 565_000),
                Arguments.of(3, 305_000 )
        );
    }
    // Возращает сумму зарплат по отделу
    @ParameterizedTest
    @MethodSource("argsProviderForSalarySumByDepartment")
    @DisplayName("Получаем сумму зарплат в отделе")
    public void shouldReturnSalarySumByDepartment(int id, int expected ){
        when(employeeServiceImplMock.getEmployee()).thenReturn(mapEmployee);
        Integer actual = out.getSalarySumByDepartment(id);
        assertEquals(expected, actual);
        verify(employeeServiceImplMock, only()).getEmployee();
    }
    //----------------------------END UNITTEST ------------------------------------------------------//
    //----------------------------START HELPER UTILS------------------------------------------------------//
    // вспомогательный метод для сравнения двух коллекций внезависимости от порядка
    private boolean mapAreEquals(Map<Integer, List<Employee>> first,
                                 Map<Integer, List<Employee>> second) {
        // получаем set из пару ключ : значение
            return first.entrySet()
                    //создаем стрим
                    .stream()
                    // проверяем что предикат возращает тру для всех значение
                    .allMatch(entry -> {
                        final List<Employee> a = entry.getValue();
                        final List<Employee> b = second.get(entry.getKey());
                        return CollectionUtils.isEqualCollection(a, b);
                    });
    }
    //----------------------------END HELPER UTILS------------------------------------------------------//
}
