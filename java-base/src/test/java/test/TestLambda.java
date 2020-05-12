package test;

import entity.Employee;
import impl.MyFunction;
import impl.MyPredicate;
import org.testng.annotations.Test;
import utils.FilterEmployeeBySalary;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TestLambda {

    List<Employee> employees = Arrays.asList(
            new Employee("张山", 29, 5000),
            new Employee("哈哈", 18, 1000),
            new Employee("百度", 60, 60000),
            new Employee("腾讯", 30, 3000),
            new Employee("阿里", 10, 7000),
            new Employee("网易", 50, 9000)
    );

    @Test(description = "测试")
    public void test01() {
        List<Employee> list = filterEmployees(employees);
        for (Employee employee : list) {
            System.out.println(employee);
        }
        System.out.println("------------------------------");
        //策略设计模式，只需要修改类方法就可以，按需要的策略过滤数据
        List<Employee> list2 = filterEmployee(employees, new FilterEmployeeBySalary());
        for (Employee employee : list2) {
            System.out.println(employee);
        }

    }

    //
    public List<Employee> filterEmployees(List<Employee> employees) {
        List<Employee> emps = new ArrayList<Employee>();
        for (Employee emp : employees) {
            if (emp.getAge() >= 30) {
                emps.add(emp);
            }
        }
        return emps;
    }

    //优化1
    public List<Employee> filterEmployee(List<Employee> employees, MyPredicate<Employee> mp) {
        List<Employee> emps = new ArrayList<Employee>();
        for (Employee emp : employees) {
            if (mp.test(emp)) {
                emps.add(emp);
            }
        }
        return emps;
    }

    //优化2，匿名内部类
    @Test(description = "匿名内部类")
    public void test02() {
        List<Employee> emps = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee t) {
                return t.getSalary() > 7000;
            }
        });
        for (Employee employee : emps) {
            System.out.println(employee);
        }
    }

    //优化3,lambda表达式
    @Test(description = "测试")
    public void test01a() {
        List<Employee> emps = filterEmployee(employees, t -> t.getSalary() > 7000);
        emps.forEach(System.out::println);
    }

    //优化4
    @Test(description = "测试")
    public void test01b() {
        employees.stream().filter(e -> e.getSalary() > 100)
                .limit(2)
                .forEach(System.out::println);

        employees.stream().filter(e -> e.getSalary() > 100)
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    @Test(description = "测试")
    public static void test01c() {
        int num = 0; //jdk 1.7 以前必须是final
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("zc" + num);
            }
        };
        r.run();
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("cz");
    }

    @Test(description = "测试")
    public void test01aa() {

        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
    }

    @Test(description = "联系")
    public void test02a() {
        String s0 = strHander("czxczxcx", new MyFunction() {
            @Override
            public String getValue(String str) {
                return str.trim();
            }
        });
        //简写1
        String s = strHander("czxczxcx", str -> str.trim());
        //简写2
        String s1 = strHander(" czxczxcx ", String::trim);
        System.out.println(s1);
    }


    public String strHander(String str, MyFunction my) {
        return my.getValue(str);
    }

    @Test(description = "联系")
    void testHappy() {
        happy(10000, m -> System.out.println("zxcxzcz" + m));
    }


    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }


    @Test(description = "测试")
    public static void testFunc() {
        Function<Integer, Employee> function = Employee::new;
        Employee employee = function.apply(18);
        System.out.println(employee);

        Function<Integer, String[]> function0 = x -> new String[x];
        Function<Integer, String[]> function1 = String[]::new;
        String[] ss = function1.apply(10);
    }
}
