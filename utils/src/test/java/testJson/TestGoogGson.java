package testJson;

public class TestGoogGson {

    public static void main(String[] args) {
        //RequestData requestData = JsonOfObject.jsonToObj(RequestData.class,data);
        //System.out.println(requestData);
        //return DafaRequest.get(requestData.getUrl(),requestData.getCookie());
    }

    public class RequestData {
        private String url;
        private String body;
        private String cookie;


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCookie() {
            return cookie;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }

        @Override
        public String toString() {
            return "RequestData{" +
                    "url='" + url + '\'' +
                    ", body='" + body + '\'' +
                    ", cookie='" + cookie + '\'' +
                    '}';
        }
    }

}
