package com.skyhomework.courseworkonspring.controller;

import com.skyhomework.courseworkonspring.model.Employee;
import com.skyhomework.courseworkonspring.service.EmployeeServiceImpl;
import com.skyhomework.courseworkonspring.Exception.EmployeeAlreadyAddedException;
import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.Exception.EmployeeStorageIsFullException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("employee/")
public class EmployeeController {
    public final EmployeeServiceImpl emplService;

    public EmployeeController(EmployeeServiceImpl emplService) {
        this.emplService = emplService;
    }

    @GetMapping(path="/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee add(@RequestParam("firstName") String f,
                        @RequestParam("lastName") String l,
                        @RequestParam("dpt") int d,
                        @RequestParam("slr") int s
    ){
        return emplService.add(f, l, d, s);
    }
    @GetMapping(path="/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee remove(@RequestParam("firstName") String f,
                           @RequestParam("lastName") String l){
        return emplService.remove(f, l);
    }
    @GetMapping(path="/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee find(@RequestParam("firstName") String f,
                         @RequestParam("lastName") String l){
        Employee empl;
        try {
            empl = emplService.find(f, l);
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        return empl;
    }

    @GetMapping(path="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Employee> all(){
        return emplService.getEmployee();
    }

}
