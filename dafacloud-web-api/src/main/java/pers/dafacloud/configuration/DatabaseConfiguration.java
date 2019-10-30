package pers.dafacloud.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author jason
 * @date 2018/04/19
 * @project app-service
 */
@Configuration
public class DatabaseConfiguration {
    public static final String MAIN_DATASOURCE = "Local";
    public static final String SLAVE_DATASOURCE = "Dev";

    @Bean(name = MAIN_DATASOURCE,destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.local")
    @Primary
    public DataSource dataSourceMain(){
        return new HikariDataSource();
    }

    @Bean(name = SLAVE_DATASOURCE,destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.dev")
    public DataSource dataSourceSlave(){
        return new HikariDataSource();
    }

}
