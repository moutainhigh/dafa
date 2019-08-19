package pers.utils.httpclientUtils;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import pers.utils.httpclientUtils.exception.HttpProcessException;
import pers.utils.httpclientUtils.SSLClientCustom.SSLProtocolVersion;


/**
 * httpclient创建者
 *
 * @author arron
 * @version 1.0
 */
public class HttpClientCustom extends HttpClientBuilder {


    private SSLClientCustom sslClientCustom = SSLClientCustom.getInstance();
    private SSLProtocolVersion sslpv= SSLProtocolVersion.SSLv3;//ssl 协议版本
    private HttpClientCustom(){}
    public static HttpClientCustom custom(){
        return new HttpClientCustom();
    }

    public HttpClientCustom ssl() throws Exception {
        return (HttpClientCustom) this.setSSLSocketFactory(sslClientCustom.getSSLCONNSF(sslpv));
    }



    /**
     * 设置连接池（默认开启https）
     *
     * @param maxTotal					最大连接数
     * @param defaultMaxPerRoute	每个路由默认连接数
     * @return	返回当前对象
     * @throws HttpProcessException	http处理异常
     */
    /*public HCB pool(int maxTotal, int defaultMaxPerRoute) throws HttpProcessException {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslClientCustom.getSSLCONNSF(sslpv)).build();
        //设置连接池大小
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connManager.setMaxTotal(maxTotal);// Increase max total connection to $maxTotal
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);// Increase default max connection per route to $defaultMaxPerRoute
        //connManager.setMaxPerRoute(route, max);// Increase max connections for $route(eg：localhost:80) to 50
        //isSetPool=true;
        return (HCB) this.setConnectionManager(connManager);
    }*/

    /**
     * 设置ssl版本<br>
     * 如果您想要设置ssl版本，必须<b>先调用此方法，再调用ssl方法<br>
     * 仅支持 SSLv3，TSLv1，TSLv1.1，TSLv1.2</b>
     * @param sslpv	版本号
     * @return	返回当前对象
     */
    public HttpClientCustom sslpv(SSLProtocolVersion sslpv){
        this.sslpv = sslpv;
        return this;
    }

}
