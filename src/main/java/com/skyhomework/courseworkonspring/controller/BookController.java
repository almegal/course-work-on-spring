package com.skyhomework.courseworkonspring.controller;

import com.skyhomework.courseworkonspring.service.BookServiceImp;
import com.skyhomework.courseworkonspring.model.Employee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("departments/")
public class BookController {
    public final BookServiceImp bookServiceImp;

    public BookController(BookServiceImp bookServiceImp) {
        this.bookServiceImp = bookServiceImp;
    }

    @GetMapping(path="/all")
    public Map<Integer, List<Employee>> getAll(){
        return bookServiceImp.getAllEmployeeSplitDepartment();
    }
    @GetMapping(path="/all-by-id", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> all(@RequestParam("department") int d){
        return bookServiceImp.getEmployeeInDepartment(d);
    }
    @GetMapping(path = "/max-salary", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getMaxSalaryInDepartment(@RequestParam("department") int d) {
        return bookServiceImp.getMaxSalaryInDepartment(d);
    }
    @GetMapping(path = "/min-salary", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getMinSalaryInDepartment(@RequestParam("department") int d) {
        return bookServiceImp.getMinSalaryInDepartment(d);
    }
}
