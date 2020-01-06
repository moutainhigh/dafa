package pers.dafacloud.dao;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;
import pers.utils.fileUtils.FileUtil;

import java.io.*;

@Service
public class SqlSessionFactoryUtils {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSessionFactory bettingSessionFactory;
    private static SqlSessionFactory transactionSessionFactory;
    private static SqlSessionFactory reportSessionFactory;
    private static SqlSessionFactory preSessionFactory;
    private static final Class CLASS_LOCK = SqlSessionFactoryUtils.class;

    /**
     * 私有化构造
     */
    private SqlSessionFactoryUtils() {
    }

    /*
     * 单实例对象
     */
    private static SqlSessionFactory initSqlSessionFactory(String name) {

        //String resource = "/Users/duke/Documents/github/dafa/dafacloud-lottery-spring/src/main/resources/SqlMapConfig.xml";
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = null;
        try {
            //inputStream = SqlSessionFactory.class.getResourceAsStream(resource);
            //File file = new File(resource);
            //inputStream = new FileInputStream(file);
            inputStream = Resources.getResourceAsStream(resource);
        } catch (Exception e) {
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
                case "pre":
                    if (preSessionFactory == null) {
                        preSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, name);
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
            case "pre":
                if (preSessionFactory == null) {
                    initSqlSessionFactory(name);
                }
                return preSessionFactory.openSession(true);
            default:
                if (sqlSessionFactory == null) {
                    initSqlSessionFactory("dev");
                }
                return sqlSessionFactory.openSession(true);
        }
    }

    //public static SqlSession openSqlSession() {
    //    if (sqlSessionFactory == null) {
    //        initSqlSessionFactory("dev");
    //    }
    //    return sqlSessionFactory.openSession();
    //}

}

