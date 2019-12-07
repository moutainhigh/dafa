package pers.dafacloud.Dao;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSessionFactory bettingSessionFactory;
    private static SqlSessionFactory transactionSessionFactory;
    private static SqlSessionFactory reportSessionFactory;
    private static final Class CLASS_LOCK = SqlSessionFactoryUtils.class;

    /**
     * 私有化构造
     */
    private SqlSessionFactoryUtils() {
    }

    /**
     * 单实例对象
     */
    public static SqlSessionFactory initSqlSessionFactory(String name) {

        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            //File file = ResourceUtils.getFile(resource);
            //inputStream=new FileInputStream(file);
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (CLASS_LOCK) {
            switch (name) {
                case "betting":
                    if (bettingSessionFactory == null) {
                        bettingSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
                    }
                    break;
                case "transaction":
                    if (transactionSessionFactory == null) {
                        transactionSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
                    }
                    break;
                case "report":
                    if (reportSessionFactory == null) {
                        reportSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
                    }
                    break;
                case "lotteryGame":
                    if (reportSessionFactory == null) {
                        reportSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
                    }
                    break;
                default:
                    if (sqlSessionFactory == null) {
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "dev");
                    }
                    break;
            }

        }
        return sqlSessionFactory;
    }

    /**
     * 多数据源
     */
    public static SqlSession openSqlSession(String name) {
        switch (name) {
            case "betting":
                if (bettingSessionFactory == null) {
                    initSqlSessionFactory(name);
                }
                return bettingSessionFactory.openSession(true);
            case "transaction":
                if (transactionSessionFactory == null) {
                    initSqlSessionFactory(name);

                }
                return transactionSessionFactory.openSession(true);
            case "report":
                if (reportSessionFactory == null) {
                    initSqlSessionFactory(name);
                }
                return reportSessionFactory.openSession(true);
            case "lotteryGame":
                if (reportSessionFactory == null) {
                    initSqlSessionFactory(name);
                }
                return reportSessionFactory.openSession(true);
            default:
                if (sqlSessionFactory == null) {
                    initSqlSessionFactory("dev");

                }
                return sqlSessionFactory.openSession(true);
            //break;
        }
//        if (sqlSessionFactory == null) {
//            initSqlSessionFactory(name);
//        }
//        return sqlSessionFactory.openSession();
    }

    /**
     *单数据源-旧
     */
    //public static SqlSession openSqlSession() {
    //    if (sqlSessionFactory == null) {
    //        initSqlSessionFactory("dev");
    //    }
    //    return sqlSessionFactory.openSession(true);
    //}

}

