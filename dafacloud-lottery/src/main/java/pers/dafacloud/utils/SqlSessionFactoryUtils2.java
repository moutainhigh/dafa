package pers.dafacloud.utils;


import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtils2 {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSessionFactory bettingSessionFactory;
    private static SqlSessionFactory lotterySessionFactory;
    private static SqlSessionFactory transactionSessionFactory;
    private static SqlSessionFactory reportSessionFactory;
    private static final Class CLASS_LOCK = SqlSessionFactoryUtils2.class;
    //static InputStream inputStream;
    //
    //static {
    //    try {
    //        inputStream = inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}

    /**
     * 私有化构造
     */
    private SqlSessionFactoryUtils2() {
    }

    /**
     * 单实例对象
     */
    public static void initSqlSessionFactory(String name) {

        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
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
                case "lottery":
                    if (lotterySessionFactory == null) {
                        lotterySessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
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
                //new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                return bettingSessionFactory.openSession(true);
            case "lottery":
                if (lotterySessionFactory == null) {
                    initSqlSessionFactory(name);
                }
                return lotterySessionFactory.openSession(true);
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
        }
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

