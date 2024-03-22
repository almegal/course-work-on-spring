package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp implements DepartmentService {
    private final EmployeeServiceImpl employeeServiceImpl;
    public DepartmentServiceImp(EmployeeServiceImpl employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }
    @Override
    public Employee getMinSalaryInDepartment(int dpt) {
        return employeeServiceImpl.getEmployee()
        // получаем коллекцию значений из employeeService
                .values()
        // создаем стрим
                .stream()
        // фильтруем каждый элемент по принципу равен dpt
                .filter(e -> e.getDepartment() == dpt )
        // получаем минимальное значение через компаратор который сравнивает
        // каждые значение поля Salary у каждого Employee
                .min(Comparator.comparingInt(Employee::getSalary))
        // если нет результата выбрасить исключение
                .orElseThrow(() -> new EmployeeNotFoundException("такого отдела не существует"));
    }
    @Override
    public Employee getMaxSalaryInDepartment(int dpt) {
        return employeeServiceImpl.getEmployee()
                // получаем коллекцию значений из employeeService
                .values()
                // создаем стрим
                .stream()
                // фильтруем каждый элемент по принципу равен dpt
                .filter(e -> e.getDepartment() == dpt )
                // получаем максимальное значение через компаратор который сравнивает
                //каждые значение поля Salary у каждого Employee
                .max(Comparator.comparingInt(Employee::getSalary))
                // если нет результата выбрасить исключение
                .orElseThrow(() -> new EmployeeNotFoundException("такого отдела не существует"));
    }
    @Override
    public List<Employee> getEmployeeInDepartment(int dpt) {
        return employeeServiceImpl.getEmployee()
                // получаем коллекцию значений из employeeService
                .values()
                // создаем стрим
                .stream()
                // фильтруем каждый элемент по принципу равен dpt
                .filter(employee -> employee.getDepartment() == dpt)
                //собираем полученный результат в список
                .toList();
    }
    @Override
    public  Map<Integer, List<Employee>> getAllEmployeeSplitDepartment() {
        return employeeServiceImpl.getEmployee()
                // получаем коллекцию значений из employeeService
                .values()
                // создаем стрим
                .stream()
                // собираем результат в Мар
                // указываем коллектору, что нужно группировать
                // и показываем на основание какого значение получаем ключ и список
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
    @Override
    public Integer getSalarySumByDepartment(int id) {
        return employeeServiceImpl.getEmployee()
                .values()
                .stream()
                .filter(employee -> employee.getDepartment() == id)
                //конвертируем результат в список integer
                .mapToInt(Employee::getSalary)
                .sum();
    }
}
