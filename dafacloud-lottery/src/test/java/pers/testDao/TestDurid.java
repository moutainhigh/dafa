package pers.testDao;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestDurid {


    public static void main(String[] args) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl("jdbc:mysql://3.0.170.87:8305");
        datasource.setUsername("us31jw9");
        datasource.setPassword("KzrdFtgXILlE");
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");








    }

}
