package com.skyhomework.courseworkonspring.service;

import com.skyhomework.courseworkonspring.Exception.ValidateException;
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
    private final int maxEmployeesInCompany = 10;
    private final Map<String, Employee> employeeBook = new HashMap<>();

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee add(String f, String l, int d, int s) {
        validateData(f, l);
        Employee empl = new Employee(f, l, d, s);

        if(employeeBook.size() >= maxEmployeesInCompany) throw new EmployeeStorageIsFullException("max limited employees in company achive (" + maxEmployeesInCompany +")");
        if(employeeBook.containsValue(empl)) throw new EmployeeAlreadyAddedException("Employee is exsist/added in storage");

        employeeBook.put(empl.getFullName(), empl);
        return empl;
    }
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee remove(String f, String l) {
        validateData(f, l);
        String key = f + " " + l;
        if(!employeeBook.containsKey(key)) throw new EmployeeNotFoundException("Employee not found in collection");

        return employeeBook.get(key);
    }
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public Employee find(String f, String l){
        validateData(f, l);
        String key = f + " " + l;
        if(!employeeBook.containsKey(key)) throw new EmployeeNotFoundException("Employee not found in collection");
        return employeeBook.get(key);
    }

    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Employee> getEmployee() {
        return employeeBook;
    }

    private void validateData(String f, String l) {
        String capitalizeFirstName = StringUtils.capitalize(f);
        String capitalizeLastName = StringUtils.capitalize(l);
        boolean isFirstNameAlpha = StringUtils.isAlpha(f);
        boolean isLastNameAlpha = StringUtils.isAlpha(l);

        if(
                !f.equals(capitalizeFirstName) ||
                !l.equals(capitalizeLastName)
        ) {
            throw new ValidateException("Имя или Фамилия не с большой буквы");
        }

        if(
                !isFirstNameAlpha ||
                !isLastNameAlpha
        ){
            throw new ValidateException("Имя или Фамилия содержит запрещенные символы");
        }

    }
}
