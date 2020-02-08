package pers.utils.httpclientUtils;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpCookies {

    /**
     * 使用httpcontext，用于设置和携带Cookie
     */
    private HttpClientContext context;

    /**
     * 储存Cookie
     */
    private CookieStore cookieStore;

    public static HttpCookies custom() {
        return new HttpCookies();
    }

    public HttpCookies setBasicClientCookie(String host, String cookieName, String cookieValue) {
        BasicClientCookie basicClientCookie = new BasicClientCookie(cookieName, cookieValue);
        try {
            basicClientCookie.setDomain(new URL(host).getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        basicClientCookie.setPath("/");
        this.cookieStore.addCookie(basicClientCookie);
        return this;
    }

    private HttpCookies() {
        this.context = new HttpClientContext();
        this.cookieStore = new BasicCookieStore();
        this.context.setCookieStore(cookieStore);
    }

    public HttpClientContext getContext() {
        return context;
    }

    public HttpCookies setContext(HttpClientContext context) {
        this.context = context;
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HttpCookies setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

}
