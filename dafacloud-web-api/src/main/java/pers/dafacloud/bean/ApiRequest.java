package pers.dafacloud.bean;

import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.entity.ApiManage;

import java.util.ArrayList;
import java.util.List;


public class ApiRequest {

    public static void main(String[] args) throws Exception{
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiManage(new ApiManage());
        List<DependApiManage> list = new ArrayList<>();
        apiRequest.setHost("");
        apiRequest.setCookie("");
        apiRequest.setDepend(list);

    }

    private static Logger logger = LoggerFactory.getLogger(ApiRequest.class);

    private HttpConfigHandle httpConfigHandle = HttpConfigHandle.custom();

    public void setHost(String host) {
        httpConfigHandle.setHttpHost(host);
    }

    public void setCookie(String cookie) {
        httpConfigHandle.setCookie(cookie);
        httpConfigHandle.setXToken(cookie);
    }

    @Setter
    private ApiManage apiManage;

    public void setDepend(List<DependApiManage> depApiManages) throws Exception {
        if (depApiManages == null || depApiManages.isEmpty()) {
            return;
        }
        List<String> deValueList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        //调用依赖接口
        String deValue = "";
        String deResult = "";
        for (DependApiManage dependApiManage : depApiManages) {
            ApiManage deApiManage = dependApiManage.getApiManage();
            String ruleString = dependApiManage.getRule();

            deResult = httpConfigHandle.setRequestPath(deApiManage.getPath())
                    .setRequestMethod(deApiManage.getMethod())
                    .setRequestParameters(deApiManage.getRequestParameters())
                    .doRequest();

            if (JSONObject.fromObject(deResult).getInt("code") != 1)
                throw new Exception("依赖接口异常");

            if (StringUtils.isNotEmpty(ruleString)) {//有提取规则
                //提取依赖数据
                // GET  : listName#stateName1,stateValue1;stateName2,stateValue2#id,name
                // GET  : orderList#id,name
                // POST : 1 getName
                //{"msg":"数据正确","code":1,"data":{"token":"xxxx"}}
                //{"msg":"获取成功","code":1,"data":[{"id":"1403"}]}
                //{"msg":"数据正确","code":1,"data":{"rows":[{"a":"1"},{"a":"1"}]}}
                String[] getRule = ruleString.split("#");

                JSONObject deResultJo = JSONObject.fromObject(deResult);
                Object data = deResultJo.get("data");
                if (getRule.length == 3) {
                    JSONArray deResultJa = getJSONArray(data, getRule[0]);
                    String[] rule = getRule[1].split(";"); //stateName1,stateValue1;stateName2,stateValue2
                    for (int j = 0; j < deResultJa.size(); j++) {//遍历请求结果
                        boolean isNeed = true;
                        JSONObject listJo = deResultJa.getJSONObject(j);
                        for (String s : rule) {
                            String[] rule0 = s.split(",");//stateName1,stateValue1
                            if (!rule0[1].equals(listJo.getString(rule0[0]))) {
                                isNeed = false;
                            }
                        }
                        if (isNeed) {
                            String[] need = getRule[2].split(",");//id,name
                            for (String s0 : need) {
                                deValue = listJo.getString(s0);
                                if (StringUtils.isEmpty(deValue)) {
                                    logger.info("deValue is null " + deValue + ",提取字段" + s0);
                                    continue;
                                }
                                deValueList.add(deValue);
                            }
                        }
                    }
                } else if (getRule.length == 2) {
                    JSONArray deResultoJa = getJSONArray(data, getRule[0]);
                    String[] need0 = getRule[1].split(",");
                    JSONObject jsonObject0 = deResultoJa.getJSONObject(0);
                    for (String s : need0) {
                        deValue = jsonObject0.getString(s);//orderList#id
                        if (StringUtils.isEmpty(deValue)) {
                            logger.info("deValue is null " + deValue);
                            continue;
                        }
                        deValueList.add(deValue);
                    }
                } else if (getRule.length == 1) {
                    deValue = deResultJo.getJSONObject("data").getString(getRule[0]);
                    deValueList.add(deValue);
                }

                sb.append(returnResultHandle(deValueList.isEmpty() ? deResult : deValueList.toString(), httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters()))
                        .append("\n");
                //替换参数
                if (!deValueList.isEmpty()) {
                    for (int j = 0; j < deValueList.size(); j++) {
                        apiManage.setRequestParameters(apiManage.getRequestParameters().replace(String.format("{data%s}", j + 1), deValueList.get(j)));
                    }
                } else {
                    throw new Exception("依赖提取结果空");//有提取规则却提取空
                }
            }//没有读取规则
        }
        //this.depApiManages = depApiManages;
    }

    /**
     *
     * */
    private JSONArray getJSONArray(Object data, String rule) throws Exception {
        JSONArray deResultJa;
        if (data instanceof JSONArray) {
            //{"code":1,"msg":"获取成功","data":[{"id":"1403"}]}
            deResultJa = JSONArray.fromObject(data);
        } else {
            deResultJa = JSONObject.fromObject(data).getJSONArray(rule);
        }
        if (deResultJa.size() == 0) {
            throw new Exception("依赖接口List空");//有提取规则却提取空
        }
        return deResultJa;
    }

    /**
     * 请求返回数据封装 返回结果+请求路径+请求参数
     *
     * @param result              测试结果
     * @param path                请求路径
     * @param reqParametersString 请求参数
     */
    private String returnResultHandle(String result, String path, String reqParametersString) {
        return String.format("【%s】\n【%s】\n【%s】", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString);
    }

}
