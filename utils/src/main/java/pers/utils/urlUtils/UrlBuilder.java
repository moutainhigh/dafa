package pers.utils.urlUtils;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UrlBuilder {

    public static UrlBuilder custom() {
        return new UrlBuilder();
    }


    private String url;
    private List<Params> params = new ArrayList<>();
    private List<Params> jsonParams = new ArrayList<>();

//    public UrlBuilder(String url) {
//        this.url = url;
//    }

    public UrlBuilder url(String url) {
        this.url = url;
        return this;
    }

    public UrlBuilder addBuilder(String key, String value) {
        this.params.add(new Params(key, value));
        return this;
    }

    public UrlBuilder addBuilder(String key, int value) {
        this.params.add(new Params(key, String.valueOf(value)));
        return this;
    }

    public UrlBuilder addBuilder(String key) {
        this.params.add(new Params(key, ""));
        return this;
    }

    public UrlBuilder addBuilder(Params params) {
        this.params.add(params);
        return this;
    }

    public UrlBuilder addJsonBuilder(String key, String value) {
        this.jsonParams.add(new Params(key, value));
        return this;
    }

    public UrlBuilder addJsonBuilder(Params params) {
        this.jsonParams.add(params);
        return this;
    }

    public String fullUrl() {
        if (!url.contains("?")) {
            StringBuilder builder = new StringBuilder(this.url);
            if (!this.params.isEmpty()) {
                builder.append("?");
                int counter = 0;
                for (Params param : params) {
                    if (counter++ != 0) {
                        builder.append("&");
                    }
                    builder.append(param.key).append("=").append(param.value);
                }
            }
            return builder.toString();
        } else {
            return this.url;
        }
    }

    /**
     * Content-Type: application/x-www-form-urlencoded;charset=UTF-8 的请求头时，构建body
     */
    public String fullBody() {
        StringBuilder builder = new StringBuilder();
        if (!this.params.isEmpty()) {
            int counter = 0;
            for (Params param : params) {
                if (counter++ != 0) {
                    builder.append("&");
                }
                builder.append(param.key).append("=").append(param.value);
            }
        }
        return builder.toString();

    }


    public String fullJson() {
        JSONObject object = new JSONObject();
        for (Params params : this.jsonParams) {
            object.put(params.key, params.value);
        }
        return object.toString();
    }
}
