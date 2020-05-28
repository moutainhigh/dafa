package pers.dafacloud.bean;

import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

public class HttpConfigHandle {
    private HttpConfig httpConfig;
    @Setter
    private String xTkoen;
    @Getter
    private String httpHost;
    private String cookie;

    private boolean isGet;
    private int requestMethod;
    @Getter
    private String requestPath;
    @Getter
    private String requestParameters;

    public static HttpConfigHandle custom() {
        return new HttpConfigHandle();
    }

    public HttpConfigHandle setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    public HttpConfigHandle setHttpHost(String httpHost) {
        this.httpHost = httpHost;
        return this;
    }

    public HttpConfigHandle setRequestMethod(int method) {
        this.requestMethod = method;
        this.isGet = method == 1;
        return this;
    }

    public HttpConfigHandle setRequestPath(String requestPath) {
        this.requestPath = requestPath;
        return this;
    }


    public HttpConfigHandle setCookie(String cookie) {
        this.cookie = cookie;
        HttpCookies httpCookies = HttpCookies.custom();
        if (StringUtils.isNotEmpty(cookie)) {
            httpCookies.setBasicClientCookie(httpHost, "JSESSIONID", cookie);
        }
        httpConfig.context(httpCookies.getContext());
        return this;
    }


    public HttpConfigHandle setHeader(String requestHeader) throws Exception {
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Session-Id", cookie) //cocos使用
                .other("X-Token",xTkoen);
        if (StringUtils.isNotEmpty(requestHeader)) { //header不为空则添加header
            JSONArray headersArray;
            try {
                headersArray = JSONArray.fromObject(requestHeader);
            } catch (Exception e) {
                throw new Exception("请求头非json格式：" + requestHeader);
            }
            for (int i = 0; i < headersArray.size(); i++) {
                JSONArray headersArray0 = headersArray.getJSONArray(i);
                httpHeader.other(headersArray0.getString(0), headersArray0.getString(1));
            }
        }
        httpConfig.headers(httpHeader.build());
        return this;
    }

    public HttpConfigHandle setRequestParameters(String requestParameters) throws Exception {
        if (StringUtils.isEmpty(requestParameters)) {
            if (isGet)
                httpConfig.url(httpHost + requestPath);
            return this;
        }
        JSONArray requestParametersJa;
        try {
            requestParametersJa = JSONArray.fromObject(requestParameters);
        } catch (Exception e) {
            throw new Exception("请求参数非json格式:" + requestParameters);
        }
        UrlBuilder urlBuilder = UrlBuilder.custom();
        for (int i = 0; i < requestParametersJa.size(); i++) {
            JSONArray requestParametersArray0 = requestParametersJa.getJSONArray(i);
            urlBuilder.addBuilder(requestParametersArray0.getString(0), requestParametersArray0.getString(1));
        }
        String urlBuilder0 = isGet ? urlBuilder.url(httpHost + requestPath).fullUrl() : urlBuilder.fullBody();
        this.requestParameters = urlBuilder0;
        if (isGet)
            httpConfig.url(urlBuilder0);
        else
            httpConfig.body(urlBuilder0);
        return this;
    }

    public String doRequest() {
        return requestMethod == 1 ? DafaRequest.get(httpConfig) : DafaRequest.post(httpConfig.url(httpHost + requestPath));
    }
}
