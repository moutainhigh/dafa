package pers.dafacloud.utils.persAnnontation;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class TestMyAnnontation {


    public static void main(String[] args) {
        //Reflections reflections = new Reflections(new ConfigurationBuilder()
        //        .forPackages("pers.dafacloud.utils.persAnnontation")
        //        .addScanners(new SubTypesScanner()) // 添加子类扫描工具
        //        .addScanners(new FieldAnnotationsScanner()) // 添加 属性注解扫描工具
        //        .addScanners(new MethodAnnotationsScanner() ) // 添加 方法注解扫描工具
        //        .addScanners(new MethodParameterScanner() ) // 添加方法参数扫描工具
        //);
        Reflections reflections = new Reflections("pers.dafacloud.TestCase");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(MyTestClass.class);
        typesAnnotatedWith = typesAnnotatedWith
                .stream()
                .sorted((o1, o2) -> Integer.compare(o1.getName().compareTo(o2.getName()), 0))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        //typesAnnotatedWith.forEach(System.out::println);
        for (Class clazz : typesAnnotatedWith) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //判断带自定义注解MyAnnontation的method
                if (method.isAnnotationPresent(MyTestCase.class)) {
                    MyTestCase annotation = method.getAnnotation(MyTestCase.class);
                    //根据入参WayCode比较method注解上的WayCode,两者值相同才执行该method
                    if (!annotation.skip()) {
                        try {
                            //执行method
                            method.invoke(clazz.newInstance());
                        } catch (Exception e) {
                            System.out.println("--------------执行自定义注解方法异常--------------");
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
    }


}
