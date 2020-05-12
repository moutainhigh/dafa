package entity;

import lombok.Data;

@Data
public class Employee {
    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(int age) {
        this.age = age;
    }
}
