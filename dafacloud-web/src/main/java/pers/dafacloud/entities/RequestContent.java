package pers.dafacloud.entities;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


//@PropertySource(value = {"classpath:emp.properties"})
@Component      //不加这个注解的话, 使用@Autowired 就不能注入进去了
@ConfigurationProperties(prefix = "request")  // 配置文件中的前缀
public class RequestContent {


    private String url;
    private String domain;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "RequestContent{" +
                "url='" + url + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
