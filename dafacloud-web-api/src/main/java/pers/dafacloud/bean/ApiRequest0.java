package pers.dafacloud.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.dafacloud.entity.ApiManage;
import pers.dafacloud.server.ApiManageServer;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ApiRequest0 {

    private HttpConfig httpConfig = HttpConfig.custom();

    private ApiManage apiManage;
    private List<ApiManage> depApiManages;

    private String host;

    private ApiRequest0 apiRequest;

    @Autowired
    ApiManageServer apiManageServer;


    public ApiRequest0() {
    }

    @PostConstruct
    public void init() {
        apiRequest = this;
    }

    public void setHost(String host) {

    }

    public void setDepend(List<ApiManage> depend) {

    }

    public void setPara(String para) {
        if (depApiManages != null) {
            for (int i = 0; i < depApiManages.size(); i++) {
                
            }
        }

        httpConfig.body(para);
    }

    public void setCookie(String cookie) {
        httpConfig.context(HttpCookies.custom().setBasicClientCookie(host, "JSS", cookie).getContext());
    }


}
