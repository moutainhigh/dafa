package utils;

import entity.Employee;
import impl.MyPredicate;

public class FilterEmployeeBySalary implements MyPredicate<Employee> {

    public boolean test(Employee t) {
        return t.getSalary() >= 6000;
    }
}
