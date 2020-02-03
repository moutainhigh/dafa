package pers.dafacloud.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    /*--------------------dev1-----------------------*/
    @Value("${spring.datasource.dev1.jdbc-url}")
    private String datasourceDev1Url;

    @Value("${spring.datasource.dev1.username}")
    private String datasourceDev1Username;

    @Value("${spring.datasource.dev1.password}")
    private String datasourceDev1Password;

    @Value("${spring.datasource.dev1.driver-class-name}")
    private String datasourceDev1DriverClassName;

    /*--------------------proBetting-----------------------*/
    @Value("${spring.datasource.proBetting.jdbc-url}")
    private String datasourceProBettingUrl;

    @Value("${spring.datasource.proBetting.username}")
    private String datasourceProBettingUsername;

    @Value("${spring.datasource.proBetting.password}")
    private String datasourceProBettingPassword;

    @Value("${spring.datasource.proBetting.driver-class-name}")
    private String datasourceProBettingDriverClassName;

    /*--------------------local-----------------------*/

    @Value("${spring.datasource.local.jdbc-url}")
    private String datasourceLocalUrl;

    @Value("${spring.datasource.local.username}")
    private String datasourceLocalUsername;

    @Value("${spring.datasource.local.password}")
    private String datasourceLocalPassword;

    @Value("${spring.datasource.local.driver-class-name}")
    private String datasourceLocalDriverClassName;

    /*--------------------druid 连接池-----------------------*/
    @Value("${spring.datasource.initial-size}")
    private int datasourceInitialSize;

    @Value("${spring.datasource.minIdle}")
    private int datasourceMinIdle;

    @Value("${spring.datasource.maxActive}")
    private int datasourceMaxActive;

    @Value("${spring.datasource.maxWait}")
    private int datasourceMaxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int datasourceTimeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int datasourceMinEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String datasourceValidationQuery;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean datasourcePoolPreparedStatements;


    @Bean(name = "dev1")
    //@ConfigurationProperties(prefix = "datasource.dev1")
    public DataSource readDataSource() {
        //System.out.println("=============datasourceDev1Url：" + datasourceDev1Url);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(datasourceDev1Url);
        dataSource.setUsername(datasourceDev1Username);
        dataSource.setPassword(datasourceDev1Password);
        dataSource.setDriverClassName(datasourceDev1DriverClassName);
        dataSource.setInitialSize(datasourceInitialSize);
        dataSource.setMinIdle(datasourceMinIdle);
        dataSource.setMaxActive(datasourceMaxActive);
        dataSource.setMaxWait(datasourceMaxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceTimeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(datasourceMinEvictableIdleTimeMillis);
        dataSource.setValidationQuery(datasourceValidationQuery);
        dataSource.setPoolPreparedStatements(datasourcePoolPreparedStatements);
        return dataSource;
    }

    @Bean(name = "proBetting")
    public DataSource proBetting() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(datasourceProBettingUrl);
        dataSource.setUsername(datasourceProBettingUsername);
        dataSource.setPassword(datasourceProBettingPassword);
        dataSource.setDriverClassName(datasourceProBettingDriverClassName);
        dataSource.setInitialSize(datasourceInitialSize);
        dataSource.setMinIdle(datasourceMinIdle);
        dataSource.setMaxActive(datasourceMaxActive);
        dataSource.setMaxWait(datasourceMaxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceTimeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(datasourceMinEvictableIdleTimeMillis);
        dataSource.setValidationQuery(datasourceValidationQuery);
        dataSource.setPoolPreparedStatements(datasourcePoolPreparedStatements);
        return dataSource;
    }

    @Bean(name = "local")
    public DataSource writeDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(datasourceLocalUrl);
        dataSource.setUsername(datasourceLocalUsername);
        dataSource.setPassword(datasourceLocalPassword);
        dataSource.setDriverClassName(datasourceLocalDriverClassName);
        dataSource.setInitialSize(datasourceInitialSize);
        dataSource.setMinIdle(datasourceMinIdle);
        dataSource.setMaxActive(datasourceMaxActive);
        dataSource.setMaxWait(datasourceMaxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceTimeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(datasourceMinEvictableIdleTimeMillis);
        dataSource.setValidationQuery(datasourceValidationQuery);
        dataSource.setPoolPreparedStatements(datasourcePoolPreparedStatements);
        return dataSource;
    }

    //@Bean(name = "writeOrReadTransactionManager")
    @Bean
    public DataSourceTransactionManager transactionManager(RoutingDataSouceImpl roundRobinDataSouceProxy) {
        //Spring 的jdbc事务管理器
        System.out.println("--------------------Spring 的jdbc事务管理器-----------------------");
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(roundRobinDataSouceProxy);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory clusterSqlSessionFactory(RoutingDataSouceImpl roundRobinDataSouceProxy)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(roundRobinDataSouceProxy);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 实体类对应的位置
        bean.setTypeAliasesPackage("pers.dafacloud.model");
        System.out.println("**** clusterSqlSessionFactory：加载mapper配置路径");
        // mybatis的XML的配置
        bean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        return bean.getObject();
    }

}
