package pers.dafacloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.dafacloud.interceptor.LoginHanderInterceptor;

/**
 * @author
 * @create 2018-09-11 21:35
 * @desc 拦截器配置
 **/
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHanderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/login","/","/login");
    }
}
