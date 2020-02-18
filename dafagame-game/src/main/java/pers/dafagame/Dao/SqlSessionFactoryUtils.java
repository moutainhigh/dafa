package pers.dafagame.Dao;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {

    private static SqlSession devSession;
    private static SqlSession proTransactionSession;
    private static SqlSession proReportSession;
    private static SqlSession proGameSession;
    private static SqlSession proGameHistorySession;
    private static final Class CLASS_LOCK = SqlSessionFactoryUtils.class;

    /**
     * 私有化构造
     */
    private SqlSessionFactoryUtils() {
    }

    /**
     * 单例 ，多数据源
     */
    public static SqlSession openSqlSession(String name) {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (CLASS_LOCK) {
            switch (name) {
                case "proTransaction":
                    if (proTransactionSession == null) {
                        proTransactionSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return proTransactionSession;
                case "proReport":
                    if (proReportSession == null) {
                        proReportSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return proReportSession;
                case "proGame":
                    if (proGameSession == null) {
                        proGameSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return proGameSession;
                case "proGameHistory":
                    if (proGameHistorySession == null) {
                        proGameHistorySession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return proGameHistorySession;
                case "dev":
                    if (devSession == null) {
                        devSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return devSession;
                default:
                    if (devSession == null) {
                        devSession = new SqlSessionFactoryBuilder().build(inputStream, "dev").openSession(true);
                    }
                    return devSession;
            }
        }
    }

}

