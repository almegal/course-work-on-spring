package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.model.Employee;
import com.skyhomework.courseworkonspring.Exception.EmployeeAlreadyAddedException;
import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.Exception.EmployeeStorageIsFullException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    //Допустимое количество сотрудников в компании по умолчанию
    private int maxEmployeesInCompany = 15;
    private final Map<String, Employee> employeeBook = new HashMap<>();
    // метод добавления
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee add(String f, String l, int d, int s) {
        //проверяем что переданные значения валидные
        validateParams(f, l);
        // делаем строки строки с Заглавной буквы
        String validName = changeStringToCapitalize(f);
        String validLastName = changeStringToCapitalize(l);
        // создаем экземпляр работника
        Employee empl = new Employee(validName, validLastName, d, s);
        // если размен книги больше допустимого значения вернуть ошибку
        if(employeeBook.size() >= maxEmployeesInCompany) throw new EmployeeStorageIsFullException("Нельзя больше добавить сотрудников, превышен лимит работников");
        // если значение уже есть в списке вернуть ошибку
        if(employeeBook.containsValue(empl)) throw new EmployeeAlreadyAddedException("Такой сотрудник уже есть в списке");
        // добавляем работника в словарь
        employeeBook.put(empl.getFullName(), empl);
        return empl;
    }
    // метод удаления
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee remove(String f, String l) {
        //проверяем что переданные значения валидные
        validateParams(f, l);
        // делаем строки строки с Заглавной буквы
        String validName = changeStringToCapitalize(f);
        String validLastName = changeStringToCapitalize(l);
        // создаем ключ для поиска в словаре
        String key = validName.concat(" ").concat(validLastName);
        // если такого работника нет в словаре по ключу - вернуть ошибку
        if(!employeeBook.containsKey(key)) throw new EmployeeNotFoundException("Такого работника не существует");
        return employeeBook.remove(key);
    }
    // метод поиска
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee find(String f, String l){
        //проверяем что переданные значения валидные
        validateParams(f, l);
        // делаем строки строки с Заглавной буквы
        String validName = changeStringToCapitalize(f);
        String validLastName = changeStringToCapitalize(l);
        // создаем ключ для поиска в словаре
        String key = validName.concat(" ").concat(validLastName);
        // если такого работника нет в словаре по ключу - вернуть ошибку
        if(!employeeBook.containsKey(key)) throw new EmployeeNotFoundException("Такого работника не существует");
        return employeeBook.get(key);
    }
    // вспомогательный метод для приведения строки к формату Sssss
    private String changeStringToCapitalize(String str) {
        // привеодим значение к нижнему регистру
        String lowetStr = str.toLowerCase();
        // используем метод библиотеки string.utils чтобы вернуть верную строку
        return StringUtils.capitalize(lowetStr);
    }
    // вспомогателньый метод для проверки корректности параметров
    private void validateParams(String f, String l) {
        // метод isAlpha срабатывает если в строке есть цифры
        // а также на ' ',  '', null,
        boolean lNameIsAlpha = StringUtils.isAlpha(l);
        boolean fNameIsAlpha = StringUtils.isAlpha(f);
        // если содержит такие символы - вернуть ошибку
        if(!fNameIsAlpha || !lNameIsAlpha) {
            throw new IllegalArgumentException("Переданы некорректные аргументы");
        }
    }
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Employee> getEmployee() {
        return employeeBook;
    }
    public void setMaxEmployeesInCompany(int maxEmployeesInCompany) {
        this.maxEmployeesInCompany = maxEmployeesInCompany;
    }
}
