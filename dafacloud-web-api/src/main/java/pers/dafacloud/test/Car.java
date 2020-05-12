package pers.dafacloud.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Car {

    String name = "";

    public static void main(String[] args) {
        ApplicationContext applicationContext =  new FileSystemXmlApplicationContext("D:applicationContenxt.xml");
        Car car = (Car) applicationContext.getBean("beanName");
    }
}
