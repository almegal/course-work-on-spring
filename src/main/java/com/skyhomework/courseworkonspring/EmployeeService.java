package com.skyhomework.courseworkonspring;

import com.skyhomework.courseworkonspring.Exception.EmployeeAlreadyAddedException;
import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.Exception.EmployeeStorageIsFullException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
@Service
public class EmployeeService  {
    private final int maxEmployeesInCompany = 3;
    public final ArrayList<Employee> employeeList;

    public EmployeeService() {
        employeeList = new ArrayList<>(maxEmployeesInCompany);
    }

    @ResponseStatus(code = HttpStatus.OK)
    public Employee add(String f, String l)  throws EmployeeAlreadyAddedException, EmployeeStorageIsFullException {
        Employee empl = new Employee(f, l);

        if(employeeList.size() >= maxEmployeesInCompany) throw new EmployeeStorageIsFullException("max limited employees in company achive (" + maxEmployeesInCompany +")");
        if(employeeList.contains(empl)) throw new EmployeeAlreadyAddedException("Employee is exsist/added in storage");

        employeeList.add(empl);
        return empl;
    }
    @ResponseStatus(code = HttpStatus.OK)
    public Employee remove(String f, String l) throws EmployeeNotFoundException {
        Employee empl = new Employee(f, l);
        //метод ArrayList.remove() вовзращает true в случае если елемент успешно удален
        // если не найден такой элемент в списке возращает false и выбрасываем ошибку
        if(!employeeList.remove(empl)) throw new EmployeeNotFoundException("Employee not found in collection");

        return empl;
    }
    @ResponseStatus(code = HttpStatus.OK)
    public Employee find(String f, String l) throws EmployeeNotFoundException {
        Employee empl = new Employee(f, l);
        if(!employeeList.contains(empl)) throw new EmployeeNotFoundException("Employee not found in collection");
        return empl;
    }

    public int getMaxEmployeesInCompany() {
        return maxEmployeesInCompany;
    }

    public ArrayList<Employee> getEmployee() {
        return employeeList;
    }
    @ResponseStatus(code = HttpStatus.OK)
    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }
}
