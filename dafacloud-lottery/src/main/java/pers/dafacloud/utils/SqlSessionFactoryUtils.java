package pers.dafacloud.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionFactoryUtils {

    private static SqlSession devSession;
    private static SqlSession bettingSession;
    private static SqlSession bettingHistorySession;
    private static SqlSession lotterySession;
    private static SqlSession transactionSession;
    private static SqlSession reportSession;
    private static SqlSession lotteryGameSession;
    private static SqlSession aliSession;

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
                case "betting":
                    if (bettingSession == null) {
                        bettingSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return bettingSession;
                case "bettingHistory":
                    if (bettingHistorySession == null) {
                        bettingHistorySession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return bettingHistorySession;
                case "lottery":
                    if (lotterySession == null) {
                        lotterySession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return lotterySession;
                case "transaction":
                    if (transactionSession == null) {
                        transactionSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return transactionSession;
                case "report":
                    if (reportSession == null) {
                        reportSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return reportSession;
                case "lotteryGame":
                    if (lotteryGameSession == null) {
                        lotteryGameSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return lotteryGameSession;
                case "dev":
                    if (devSession == null) {
                        devSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return devSession;
                case "ali":
                    if (aliSession == null) {
                        aliSession = new SqlSessionFactoryBuilder().build(inputStream, name).openSession(true);
                    }
                    return aliSession;
                default:
                    if (devSession == null) {
                        devSession = new SqlSessionFactoryBuilder().build(inputStream, "dev").openSession(true);
                    }
                    return devSession;
            }
        }
    }
}

