package pers.dafacloud.utils.httpUtils;


import org.apache.http.client.CookieStore;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Https忽略证书
 */
public class SSLClientCustom {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslConnectionSocketFactory = null;
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;//连接池管理类
    private static SSLContextBuilder sslContextBuilder = null;//管理Https连接的上下文类

    //private static CookieStore cookieStore = null;
    static {
        try {
            sslContextBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                    信任所有站点 直接返回true
                    return true;
                }
            });
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(),
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"},
                    null,
                    NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslConnectionSocketFactory)
                    .build();
            poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registryBuilder);
            poolingHttpClientConnectionManager.setMaxTotal(200);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
//    public static CloseableHttpClient getHttpClinet() {
//        CookieStore cookieStore = null;
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslConnectionSocketFactory)
//                .setConnectionManager(poolingHttpClientConnectionManager)
//                .setConnectionManagerShared(true)
//                .build();
//        return httpClient;
//    }

    /**
     * 获取连接
     * params : cookie
     *
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient getHttpClinet(CookieStore cookieStore) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setSSLSocketFactory(sslConnectionSocketFactory)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
        return httpClient;
    }
}
