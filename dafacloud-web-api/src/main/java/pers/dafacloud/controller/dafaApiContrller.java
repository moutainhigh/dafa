package pers.dafacloud.controller;


import lombok.Data;
import lombok.ToString;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.dao.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.dao.mapper.userMapper.UserMapper;
import pers.dafacloud.dao.pojo.ApiContent;
import pers.utils.dafaRequest.DafaRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
public class dafaApiContrller {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    ApiContentMapper apiContentMapper = sqlSession.getMapper(ApiContentMapper.class);
    //配置
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "path", required = false) String path,
                      @RequestParam(value = "mothod", required = false) String mothod,
                      @RequestParam(value = "body", required = false) String body,
                      @RequestParam(value = "header", required = false) String header,
                      @RequestParam(value = "dependApiName", required = false) String dependApiName,
                      @RequestParam(value = "module", required = false) String module,
                      @RequestParam(value = "page", required = false) String page,
                      @RequestParam(value = "project", required = false) String project,
                      @RequestParam(value = "description", required = false) String description,
                      @RequestParam(value = "owner", required = false) String owner) {
        ApiContent apiContent = new ApiContent();
        apiContent.setName(name);
        apiContent.setPath(path);
        apiContent.setMethod(mothod);
        apiContent.setBody(body);
        apiContent.setHeader(header);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setPage(page);
        apiContent.setProject(project);
        apiContent.setDescription(description);
        apiContent.setOwner(owner);
        int i = apiContentMapper.addApi(apiContent);
        if (i == 1) {
            return "新增成功";
        } else {
            return "新增失败";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        int delete = apiContentMapper.deleteApi(id);
        if (delete == 0)
            return "删除失败";
        else
            return "删除成功";
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public Response query(@RequestParam(value = "name", required = false) String name,
                        @RequestParam(value = "dependApiName", required = false) String dependApiName,
                        @RequestParam(value = "module", required = false) String module,
                        @RequestParam(value = "page", required = false) String page,
                        @RequestParam(value = "project", required = false) String project,
                        @RequestParam(value = "owner", required = false) String owner,
                        @RequestParam(value = "pageNum", required = false,defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false,defaultValue = "1") int pageSize

                          //,@RequestParam("description") String description
    ) {
        ApiContent apiContent = new ApiContent();
        apiContent.setName(name);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setPage(page);
        apiContent.setProject(project);
        apiContent.setOwner(owner);
        apiContent.setPageNum((pageNum-1)*pageSize);
        apiContent.setPageSize(pageSize);
        List<ApiContent> list = apiContentMapper.queryApi(apiContent);
        int count=apiContentMapper.queryApiCount(apiContent);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("total",count);
        jsonObject.put("list",list);
        return fillResponse(jsonObject);
        //格式：{"code":1,"data",}
    }

    @PostMapping("/update")
    public String update() {
        return "修改成功";
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
