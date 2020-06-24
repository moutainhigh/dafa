package pers.utils.dafaRequest;

import pers.utils.httpclientUtils.HttpConfig;

public class RequestHandle {

    HttpConfig httpConfig;

    String url;
    String Meth;

    public RequestHandle() {
        httpConfig = HttpConfig.custom();
    }

    public void setUrl(String url) {
        httpConfig.url(url);
    }

}
