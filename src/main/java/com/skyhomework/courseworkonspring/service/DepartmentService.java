package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.model.Employee;

import java.util.List;
import java.util.Map;
public interface DepartmentService {
    public Employee getMinSalaryInDepartment(int id);
    public Employee getMaxSalaryInDepartment(int id);
    public List<Employee> getEmployeeInDepartment(int id);
    public Map<Integer, List<Employee>> getAllEmployeeSplitDepartment();
    public Integer getSalarySumByDepartment(int id);
}
