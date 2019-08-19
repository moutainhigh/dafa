package pers.dafacloud.testHttpClientUtil;

import pers.dafacloud.utils.httpclientUtil.HttpClientUtil;
import pers.dafacloud.utils.httpclientUtil.builder.HCB;
import pers.dafacloud.utils.httpclientUtil.common.HttpConfig;
import pers.dafacloud.utils.httpclientUtil.common.SSLs.SSLProtocolVersion;

public class Demo {


    public static void main(String[] args) throws Exception{
        String url = "https://github.com/Arronlong/httpclientutil";
//        String url = "http://www.baidu.com";
        String html = HttpClientUtil
                .get(HttpConfig.custom().url(url).client(HCB.custom().sslpv(SSLProtocolVersion.TLSv1_2).ssl().build()));
        System.out.println(html);



    }


}
