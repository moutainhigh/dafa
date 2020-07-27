package pers.dafacloud.bean;

import cn.binarywang.tools.generator.BankCardNumberGenerator;
import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import pers.dafacloud.dafaLottery.ChaseBettingData;
import pers.dafacloud.enums.EVapiManage;
import pers.utils.dafaCloud.DafaCloudLogin;
import pers.utils.dafaCloud.LotteryIssuePrivate;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.logUtils.Log;
import pers.utils.urlUtils.UrlBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpConfigHandle {
    @Getter
    private HttpConfig httpConfig;
    @Setter
    private String xToken;

    @Setter
    @Getter
    String frontUsername = "";
    private String registerUsername;

    @Getter
    private String httpHost;
    private String cookie;

    private boolean isGet;
    private int requestMethod;
    @Getter
    private String requestPath;
    @Getter
    private String requestParameters;

    private boolean isFront;
    private boolean isLogin;

    private String rebate;

    @Getter
    private boolean isStop = false;


    public static HttpConfigHandle custom() {
        return new HttpConfigHandle();
    }

    public HttpConfigHandle setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    public HttpConfigHandle setHttpHost(String httpHost) {
        this.httpHost = httpHost;
        EVapiManage ev = EVapiManage.getEVapiManage(httpHost);
        if (ev != null)
            isFront = ev.isFront;
        else
            isFront = false;
        return this;
    }

    public HttpConfigHandle setRequestMethod(int method) {
        this.requestMethod = method;
        this.isGet = method == 1;
        return this;
    }

    public HttpConfigHandle setRequestPath(String requestPath) {
        if (requestPath.endsWith("login")) {
            isLogin = true;
        } else {
            isLogin = false;
        }
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
                .other("X-Token", xToken);
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
            this.requestParameters = "";
            if (isGet)
                httpConfig.url(httpHost + requestPath);
            return this;
        }
        JSONArray requestParametersJa;//二维数组
        try {
            requestParametersJa = JSONArray.fromObject(requestParameters
                    .replace("{today}", LocalDate.now().toString())
                    .replace("{today-1}", LocalDate.now().plusDays(-1).toString())
                    .replace("{today+1}", LocalDate.now().plusDays(1).toString())
                    .replace("{todayM}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")))
                    .replace("{todayM+1}", LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")))
                    .replace("{todayM-1}", LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")))
                    .replace("{bankCard}", BankCardNumberGenerator.getInstance().generate())
                    .replace("{issue}", LotteryIssuePrivate.getCurrentIssue(1))
                    .replace("{userName}", frontUsername)
                    //.replace("{safetyPassword}", DigestUtils.md5Hex(frontUsername + DigestUtils.md5Hex("100200")))
                    .replace(" ", "%20"));
        } catch (Exception e) {
            throw new Exception("请求参数非json格式:" + requestParameters);
        }
        UrlBuilder urlBuilder = UrlBuilder.custom();
        for (int i = 0; i < requestParametersJa.size(); i++) {
            JSONArray requestParametersArray0 = requestParametersJa.getJSONArray(i);//一维数组
            String paraName = requestParametersArray0.getString(0);
            String paraValue = requestParametersArray0.getString(1);
            if (isFront) {
                //注册
                if (requestPath.endsWith("users/register")) {
                    if (requestParametersJa.size() == 1) {
                        registerUsername = String.format("auto%05d", (int) (Math.random() * 100000));
                        String password = DigestUtils.md5Hex(registerUsername + DigestUtils.md5Hex("123qwe"));
                        urlBuilder.addBuilder("inviteCode", "75053265")
                                .addBuilder("userName", registerUsername)
                                .addBuilder("password", password);
                        break;
                    } else {
                        if ("userName".equals(paraName)) {
                            registerUsername = String.format("auto%05d", (int) (Math.random() * 100000));
                            if (StringUtils.isEmpty(paraValue)) {
                                urlBuilder.addBuilder("userName", registerUsername);
                            } else {
                                registerUsername = paraValue;
                                urlBuilder.addBuilder("userName", paraValue);
                            }
                            continue;
                        }
                        if ("password".equals(paraName)) {
                            if (StringUtils.isEmpty(paraValue)) {
                                urlBuilder.addBuilder("password", DigestUtils.md5Hex(registerUsername + DigestUtils.md5Hex("123qwe")));
                            } else {
                                urlBuilder.addBuilder("password", DigestUtils.md5Hex(registerUsername + DigestUtils.md5Hex(paraValue)));
                            }
                            continue;
                        }

                    }
                }
                if (isLogin) {
                    if (requestParametersJa.size() == 1) {
                        if (StringUtils.isNotEmpty(paraValue)) {
                            frontUsername = paraValue;
                        }
                        //前台登录密码
                        JSONObject passwordJson = DafaCloudLogin.getPassword(frontUsername, "123qwe");
                        urlBuilder.addBuilder("userName", frontUsername)
                                .addBuilder("password", passwordJson.getString("password"))
                                .addBuilder("random", passwordJson.getString("random"));
                        break;
                    }
                }
                //首次设置安全密码
                if (requestPath.contains("setSafetyPassword")) {
                    if (StringUtils.isEmpty(frontUsername)) {
                        frontUsername = JSONObject.fromObject(DafaRequest.get(httpConfig.url(httpHost + "/v1/users/info"))).getJSONObject("data").getString("userName");
                    }
                    urlBuilder.addBuilder("safetyPassword", DigestUtils.md5Hex(frontUsername + DigestUtils.md5Hex("100200")));
                    break;
                }
                //验证安全密码
                if (requestPath.contains("verifySafetyPassword") || requestPath.contains("saveFrontWithdrawRecord")) {
                    if (StringUtils.isEmpty(frontUsername)) {
                        frontUsername = JSONObject.fromObject(DafaRequest.get(httpConfig.url(httpHost + "/v1/users/info"))).getJSONObject("data").getString("userName");
                    }
                    if ("safetyPassword".equals(paraName)) {
                        urlBuilder.addBuilder("safetyPassword", DigestUtils.md5Hex(frontUsername + DigestUtils.md5Hex("100200")));
                        continue;
                    }
                }
                //追号
                if (requestPath.contains("addChaseBetting")) {
                    if (StringUtils.isEmpty(this.rebate)) {
                        this.rebate = JSONObject.fromObject(DafaRequest.get(httpConfig.url(httpHost + "/v1/betting/getBetRebate?lotteryType=SSC")))
                                .getJSONObject("data").getString("rebate");
                    }
                    urlBuilder.addBuilder("chaseBettingData", ChaseBettingData.getChaseBettingData(rebate));
                    break;
                }

            } else {
                //cms登录密码
                if (isLogin) {
                    if ("managerName".equals(paraName)) {
                        urlBuilder.addBuilder("managerName", paraValue)
                                .addBuilder("password", DigestUtils.md5Hex(paraValue + DigestUtils.md5Hex("123456")));
                        break;
                    }
                }
                if(requestPath.contains("updateUserLoginPassword")){//修改密码
                    if ("password".equals(paraName)) {
                        urlBuilder.addBuilder("password", DigestUtils.md5Hex(frontUsername + DigestUtils.md5Hex("123qwe")));
                        continue;
                    }

                }
            }
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
        String result = requestMethod == 1 ? DafaRequest.get(httpConfig) : DafaRequest.post(httpConfig.url(httpHost + requestPath));
        if (isLogin && isFront) {
            try {
                this.xToken = JSONObject.fromObject(result).getJSONObject("data").getString("token");
            } catch (Exception e) {
                this.isStop = true;
                Log.info("登录接口提取xToken失败：" + result);
            }
        }
        if (requestPath.endsWith("users/register")) {
            if (!result.contains("成功")) {
                if (StringUtils.isEmpty(frontUsername))
                    this.isStop = true;
            } else {
                frontUsername = registerUsername;
            }
        }
        if (requestPath.contains("manager/login")
                || requestPath.contains("switchTenant")) {
            if (!result.contains("成功")) {
                this.isStop = true;
            }

        }
        return result;
    }
}
