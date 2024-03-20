package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.model.Employee;

public interface EmployeeService {
    public Employee add(String f, String l, int d, int s);
    public Employee remove(String f, String l);
    public Employee find(String f, String l);

}
