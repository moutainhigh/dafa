package pers.dafacloud;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *@SpringBootApplication 引导类，是spring boot项目
 * @SpringBootConfiguration
 *  @Configuration 是pring中的一个注解，定义配置类，等价于配置文件
 *   @Component 添加到spring容器，表示是一个组件
 * @EnableAutoConfiguration
 *  @AutoConfigurationPackage 将引导类所在包以及其包下面的所有组件添加到spring容器中
 *  @Import({AutoConfigurationImportSelector.class})
 *      1.将所有组件已全类名的方式返回，并且添加到spring容器中
 *      2.会给容器导入非常多的自动配置类，配置类（autoConfiguration），就是导入并配置好很多当前项目需要的组件，省去我们手动编写配置然后注入到组件中
 * @ComponentScan 被标识的类会被spring容器管理
 *
 *
 * */

@SpringBootApplication
//@MapperScan(value = {"pers.dafacloud.dao.mapper"}) //不用每个mapper添加注解
public class DafacloudWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DafacloudWebApiApplication.class, args);
    }

}
