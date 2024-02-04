package com.skyhomework.courseworkonspring;

import com.skyhomework.courseworkonspring.Exception.EmployeeAlreadyAddedException;
import com.skyhomework.courseworkonspring.Exception.EmployeeNotFoundException;
import com.skyhomework.courseworkonspring.Exception.EmployeeStorageIsFullException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("employee/")
public class EmployeeController {
    public final EmployeeService emplService;

    public EmployeeController(EmployeeService emplService) {
        this.emplService = emplService;
    }

    @GetMapping(path="/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee add(@RequestParam("firstName") String f,
                      @RequestParam("lastName") String l) {
        Employee empl;
        try {
            empl = emplService.add(f, l);
        } catch (EmployeeStorageIsFullException e) {
            throw new RuntimeException(e);
        } catch (EmployeeAlreadyAddedException e) {
            throw new RuntimeException(e);
        }
        return empl;
    }
    @GetMapping(path="/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee remove(@RequestParam("firstName") String f,
                           @RequestParam("lastName") String l){
        Employee empl;
        try {
            empl = emplService.remove(f, l);
        } catch (EmployeeNotFoundException e) {
            throw new RuntimeException(e);
        }
        return empl;
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
    public ArrayList<Employee> all(){
        return emplService.getEmployeeList();
    }

}
