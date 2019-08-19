package pers.utils.httpclientUtils;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import pers.utils.httpclientUtils.exception.HttpProcessException;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SSLClientCustom {

    private static SSLConnectionSocketFactory sslConnectionSocketFactory ;
    private static final SSLHandler simpleVerifier = new SSLHandler();
    private SSLContext sc;
    private static SSLClientCustom ssl = new SSLClientCustom();


    public static SSLClientCustom getInstance(){
        return ssl;
    }

    // 重写X509TrustManager类的三个方法,信任服务器证书
    private static class SSLHandler implements X509TrustManager, HostnameVerifier {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
            //return null;
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public boolean verify(String paramString, SSLSession paramSSLSession) {
            return true;
        }
    }

    public synchronized SSLConnectionSocketFactory getSSLCONNSF(SSLProtocolVersion sslpv) throws HttpProcessException {
        if (sslConnectionSocketFactory != null)
            return sslConnectionSocketFactory;
        try {
            SSLContext sc = getSSLContext(sslpv);
//	    	sc.init(null, new TrustManager[] { simpleVerifier }, null);
            sc.init(null, new TrustManager[] { simpleVerifier }, new java.security.SecureRandom());
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(sc, simpleVerifier);
        } catch (KeyManagementException e) {
            throw new HttpProcessException(e);
        }
        return sslConnectionSocketFactory;
    }

    public SSLContext getSSLContext(SSLProtocolVersion sslpv) throws HttpProcessException{
        try {
            if(sc==null){
                sc = SSLContext.getInstance(sslpv.getName());
            }
            return sc;
        } catch (NoSuchAlgorithmException e) {
            throw new HttpProcessException(e);
        }
    }

    /**
     * The SSL protocol version (SSLv3, TLSv1, TLSv1.1, TLSv1.2)
     *
     * @author arron
     * @version 1.0
     */

    public static enum SSLProtocolVersion{
        SSL("SSL"),
        SSLv3("SSLv3"),
        TLSv1("TLSv1"),
        TLSv1_1("TLSv1.1"),
        TLSv1_2("TLSv1.2"),
        ;
        private String name;
        private SSLProtocolVersion(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public static SSLProtocolVersion find(String name){
            for (SSLProtocolVersion pv : SSLProtocolVersion.values()) {
                if(pv.getName().toUpperCase().equals(name.toUpperCase())){
                    return pv;
                }
            }
            throw new RuntimeException("未支持当前ssl版本号："+name);
        }

    }

}
