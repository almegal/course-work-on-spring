package com.skyhomework.courseworkonspring;
import java.util.Objects;

public class Employee {

    final private String firstName;
    final private String lastName;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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