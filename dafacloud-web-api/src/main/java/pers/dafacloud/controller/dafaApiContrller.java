package pers.dafacloud.controller;


import lombok.Data;
import lombok.ToString;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.dao.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.dao.pojo.ApiContent;
import pers.utils.dafaRequest.DafaRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
public class dafaApiContrller {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    ApiContentMapper apiContentMapper = sqlSession.getMapper(ApiContentMapper.class);

    @RequestMapping(value = "queryDafaApi", method = RequestMethod.GET)
    public Response query(@RequestParam(value = "apiName", required = false) String apiName,
                          @RequestParam(value = "dependApiName", required = false) String dependApiName,
                          @RequestParam(value = "module", required = false) String module,
                          @RequestParam(value = "cmsFront", required = false) String cmsFront,
                          @RequestParam(value = "project", required = false) String project,
                          @RequestParam(value = "owner", required = false) String owner,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") int pageSize

                          //,@RequestParam("description") String description
    ) {
        ApiContent apiContent = new ApiContent();
        apiContent.setApiName(apiName);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setOwner(owner);
        apiContent.setPageNum((pageNum - 1) * pageSize);
        apiContent.setPageSize(pageSize);
        List<ApiContent> list = apiContentMapper.queryApi(apiContent);
        int count = apiContentMapper.queryApiCount(apiContent);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return fillResponse(jsonObject);
    }

    //配置
    @RequestMapping(value = "addDafaApi", method = RequestMethod.POST)
    public String add(@RequestParam(value = "apiName", required = false) String apiName,
                      @RequestParam(value = "path", required = false) String path,
                      @RequestParam(value = "mothod", required = false) String mothod,
                      @RequestParam(value = "reqParametersArray", required = false) String reqParametersArray,
                      @RequestParam(value = "headerArray", required = false) String headerArray,
                      @RequestParam(value = "dependApiName", required = false) String dependApiName,
                      @RequestParam(value = "module", required = false) String module,
                      @RequestParam(value = "cmsFront", required = false) String cmsFront,
                      @RequestParam(value = "project", required = false) String project,
                      @RequestParam(value = "description", required = false) String description,
                      @RequestParam(value = "owner", required = false) String owner) {
        System.out.println("headerArray:" + headerArray);
        ApiContent apiContent = new ApiContent();
        apiContent.setApiName(apiName);
        apiContent.setPath(path);
        apiContent.setMethod(mothod);
        apiContent.setReqParametersArray(reqParametersArray);
        apiContent.setHeaderArray(headerArray);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setDescription(description);
        apiContent.setOwner(owner);
        int i = apiContentMapper.addApi(apiContent);
        JSONObject jsonObject0 = new JSONObject();
        if (i == 1) {
            jsonObject0.put("code", 1);
            jsonObject0.put("data", "新增成功");
        } else {
            jsonObject0.put("code", -1);
            jsonObject0.put("data", "新增失败");
        }
        return jsonObject0.toString();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        int delete = apiContentMapper.deleteApi(id);
        if (delete == 0)
            return "删除失败";
        else
            return "删除成功";
    }


    @PostMapping("/updateDafaApi")
    public String update(@RequestParam(value = "apiName", required = false) String apiName,
                         @RequestParam(value = "path", required = false) String path,
                         @RequestParam(value = "mothod", required = false) String mothod,
                         @RequestParam(value = "reqParametersArray", required = false) String reqParametersArray,
                         @RequestParam(value = "headerArray", required = false) String headerArray,
                         @RequestParam(value = "dependApiName", required = false) String dependApiName,
                         @RequestParam(value = "module", required = false) String module,
                         @RequestParam(value = "cmsFront", required = false) String cmsFront,
                         @RequestParam(value = "project", required = false) String project,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "owner", required = false) String owner,
                         int id) {
        ApiContent apiContent = new ApiContent();
        apiContent.setId(id);
        apiContent.setApiName(apiName);
        apiContent.setPath(path);
        apiContent.setMethod(mothod);
        apiContent.setReqParametersArray(reqParametersArray);
        apiContent.setHeaderArray(headerArray);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setDescription(description);
        apiContent.setOwner(owner);
        int i = apiContentMapper.updateApi(apiContent);
        JSONObject jsonObject0 = new JSONObject();
        if (i == 1) {
            jsonObject0.put("code", 1);
            jsonObject0.put("data", "修改成功");
        } else {
            jsonObject0.put("code", -1);
            jsonObject0.put("data", "修改失败");
        }
        return jsonObject0.toString();
    }

    //请求
    @RequestMapping(value = "request", method = RequestMethod.POST)
    public String request(@RequestParam(value = "url", required = false) String url,
                          @RequestParam(value = "cookie", required = false) String cookie,
                          @RequestParam(value = "body", required = false) String body,
                          @RequestParam(value = "method", required = false) String method) {
        System.out.println("url:" + url);//请求地址
        System.out.println("body:" + body);//请求内容，get请求cookie可以是空
        System.out.println("method:" + method);//请求方法
        System.out.println("cookie:" + cookie); //请求cookie
        if (method.equals("get"))
            return DafaRequest.post(url, body, cookie);
        else if (method.equals("post"))
            return DafaRequest.get(url, cookie);
        else
            return "请求方法错误";
    }


    public final static Response fillResponse(Object data) {
        Response response = new Response();
        response.code = 1;
        response.data = data;
        return response;

    }


    public static void main(String[] args) {
        List<JSONObject> list = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("aa", "bb");

        list.add(json);

        Response res = fillResponse(list);
        System.out.println(res);
    }

    @Data
    @ToString
    public static class Response {
        private int code;
        private Object data;
    }
}
