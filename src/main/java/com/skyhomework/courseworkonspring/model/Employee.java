package com.skyhomework.courseworkonspring.model;
import java.util.Objects;

public class Employee {

    final private String firstName;
    final private String lastName;
    private int department;
    private int salary;

    public Employee(String firstName, String lastName, int dpt, int slr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = dpt;
        this.salary = slr;
    }

    // GETTERS
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDepartment() {
        return department;
    }

    // SETTERS

    public void setDepartment(int department) {
        this.department = department;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString(){
        return "Сотрудник " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object other){
        if (this.getClass() != other.getClass()) return false;
        Employee employee = (Employee) other;
        return this.getFullName().equals(employee.getFullName());
    };

    @Override
    public int hashCode(){
        return Objects.hash(firstName, lastName);
    }
}