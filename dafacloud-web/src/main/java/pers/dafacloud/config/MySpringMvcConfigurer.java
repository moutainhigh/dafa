package pers.dafacloud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//一种

/*@Configuration
public class MySpringMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //发送 /mengxuegu 请求来到 success.html
        registry.addViewController("/mxg").setViewName("success");
    }
}*/

//二种
/*@Configuration
public class MySpringMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/mxg").setViewName("/success");
                registry.addViewController("/index").setViewName("index");
            }
        };
    }


}*/


