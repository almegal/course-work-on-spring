package com.skyhomework.courseworkonspring.controller;

import com.skyhomework.courseworkonspring.service.DepartmentServiceImp;
import com.skyhomework.courseworkonspring.model.Employee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("department/")
public class DepartmentController {
    public final DepartmentServiceImp departmentServiceImp;

    public DepartmentController(DepartmentServiceImp departmentServiceImp) {
        this.departmentServiceImp = departmentServiceImp;
    }
    // возвращает сотрудников, сгруппированых по отделам
    @GetMapping(path="/employees")
    public Map<Integer, List<Employee>> getAllEmployessFilterByDepartment(){
        return departmentServiceImp.getAllEmployeeSplitDepartment();
    }
    //возвращает список сотрудников по департаменту.
    @GetMapping(path="/{id}/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployeesInDepartment(@PathVariable("id") int id){
        return departmentServiceImp.getEmployeeInDepartment(id);
    }
    //возвращает максимальную зарплату по департаменту.
    @GetMapping(path = "/{id}/salary/max", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getMaxSalaryInDepartment(@PathVariable("id") int id) {
        return departmentServiceImp.getMaxSalaryInDepartment(id);
    }
    //возвращает минимальную зарплату по департаменту.
    @GetMapping(path = "/{id}/salary/min", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getMinSalaryInDepartment(@PathVariable("id") int id) {
        return departmentServiceImp.getMinSalaryInDepartment(id);
    }
    //возвращает сумму зарплат по департаменту.
    @GetMapping(path = "/{id}/salary/sum", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getSumSalaryByDeaprtment(@PathVariable("id") int id){
        return departmentServiceImp.getSalarySumByDepartment(id);
    }
}
