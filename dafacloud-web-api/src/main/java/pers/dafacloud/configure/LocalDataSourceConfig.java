//package pers.dafacloud.configure;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
///**
// * mysql从库配置类
// * @日期： 2018年7月5日 下午10:05:25
// * @作者： Chendb
// */
//@Configuration
//@MapperScan(basePackages = "pers.dafacloud.dao.mapper",sqlSessionTemplateRef = "localSqlSessionTemplate")
//public class LocalDataSourceConfig {
//
//    /**
//     * 创建数据源
//     *@return DataSource
//     */
//    @Bean(name = "localDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.local")
//    @Primary
//    public DataSource localDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    /**
//     * 创建工厂
//     *@param dataSource
//     *@throws Exception
//     *@return SqlSessionFactory
//     */
//    @Bean(name = "localSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory localSqlSessionFactory(@Qualifier("localDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/local/*.xml"));
//        return bean.getObject();
//    }
//
//
//    /**
//     * 创建事务
//     *@param dataSource
//     *@return DataSourceTransactionManager
//     */
//    @Bean(name = "localTransactionManager")
//    @Primary
//    public DataSourceTransactionManager localDataSourceTransactionManager(@Qualifier("localDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 创建模板
//     *@param sqlSessionFactory
//     *@return SqlSessionTemplate
//     */
//    @Bean(name = "localSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate localSqlSessionTemplate(@Qualifier("localSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//}
