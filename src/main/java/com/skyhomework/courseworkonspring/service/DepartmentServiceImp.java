package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImp implements DepartmentService {
    private final EmployeeServiceImpl emplService;
    public DepartmentServiceImp(EmployeeServiceImpl emplService) {
        this.emplService = emplService;
    }

    @Override
    public Employee getMinSalaryInDepartment(int dpt) {
        return emplService.getEmployee()
                .values()
                .stream()
                .filter(e -> e.getDepartment() == dpt )
                .min(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow();
    }

    @Override
    public Employee getMaxSalaryInDepartment(int dpt) {
        return emplService.getEmployee()
                .values()
                .stream()
                .filter(e -> e.getDepartment() == dpt )
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow();
    }

    @Override
    //arraylist not passed as type
    public List<Employee> getEmployeeInDepartment(int dpt) {
        return emplService.getEmployee()
                .values()
                .stream()
                .filter(employee -> employee.getDepartment() == dpt)
                .toList();
    }

    @Override
    public  Map<Integer, List<Employee>> getAllEmployeeSplitDepartment() {
        // set keys
        // filter
        return emplService.getEmployee()
                .values()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}
