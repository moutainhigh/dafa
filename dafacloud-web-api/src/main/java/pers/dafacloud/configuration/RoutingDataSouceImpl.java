package pers.dafacloud.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import pers.dafacloud.utils.dataSource.DataSourceContextHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 继承路由数据源摘要，
 */
@Component
public class RoutingDataSouceImpl extends AbstractRoutingDataSource {
    private static Logger log = LoggerFactory.getLogger(RoutingDataSouceImpl.class);

    @Autowired
    @Qualifier("dev1")
    private DataSource dev1DataSource;

    @Autowired
    @Qualifier("local")
    private DataSource localDataSource;

    @Override
    public void afterPropertiesSet() {
        log.info("设置数据集和默认的数据源");
        //将默认的datasource注入到targetDataSources中，可以为后续路由用到的key
        this.setDefaultTargetDataSource(dev1DataSource);
        //设置数据源集
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.local, localDataSource);
        targetDataSources.put(DataSourceType.dev1, dev1DataSource);
        this.setTargetDataSources(targetDataSources);
        //即将targetDataSources中的DataSource加载到resolvedDataSources
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        //这里边就是读写分离逻辑，最后返回的是setTargetDataSources保存的Map对应的key
        String typeKey = DataSourceContextHolder.getReadOrWrite();
        //log.info("获取到:" + typeKey + "数据库");
        return typeKey;
    }
}
