package pers.dafacloud.utils.dataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.configuration.DataSourceType;

public class DataSourceContextHolder {

    private static Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

    //线程本地环境
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static void setDev1() {
        local.set(DataSourceType.dev1);
        //log.info("数据库切换到dev1库...");
    }
    public static void setLocal() {
        local.set(DataSourceType.local);
        //log.info("数据库切换到local库...");
    }

    public static void setProBetting() {
        local.set(DataSourceType.proBetting);
    }



    public static String getDataSourceKey() {
        return local.get();
    }
}
