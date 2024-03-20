package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.model.Employee;

import java.util.List;
import java.util.Map;

public interface Book {
    public Employee getMinSalaryInDepartment(int dpt);
    public Employee getMaxSalaryInDepartment(int dpt);
    public List<Employee> getEmployeeInDepartment(int dpt);
    public Map<Integer, List<Employee>> getAllEmployeeSplitDepartment();
}
