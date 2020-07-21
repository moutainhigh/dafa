package pers.dafacloud.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Car {
    public static String name = "test";

    public static void main(String[] args) {
        //ApplicationContext applicationContext =  new FileSystemXmlApplicationContext("D:applicationContenxt.xml");
        //Car car = (Car) applicationContext.getBean("beanName");
        Car car =new Car();
        Car.name = "zczcz";
        System.out.println(Car.name);

        Car cara =new Car();
        System.out.println(Car.name);

    }



}
