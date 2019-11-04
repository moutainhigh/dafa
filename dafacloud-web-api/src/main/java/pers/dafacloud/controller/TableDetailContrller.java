package pers.dafacloud.controller;

import lombok.Data;
import lombok.ToString;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.mapper.apiContent.ApiTest;
import pers.dafacloud.mapper.tableDetail.TableDetailMapper;
import pers.dafacloud.pojo.ApiContent;
import pers.dafacloud.pojo.TableDetail;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.List;

@RestController
public class TableDetailContrller {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    TableDetailMapper tableDetailMapper = sqlSession.getMapper(TableDetailMapper.class);

    @Autowired
    ApiTest apiTest;

    @GetMapping("/queryTableDetail")
    public Response query(@RequestParam(value = "tableNameEn", required = false) String tableNameEn,
                          @RequestParam(value = "tableDescription", required = false) String tableDescription,
                          @RequestParam(value = "tableColumn", required = false) String tableColumn,
                          @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                          @RequestParam(value = "moduleServer", required = false) String moduleServer,
                          @RequestParam(value = "tableOwner", required = false) String tableOwner,
                          @RequestParam(value = "groupsTable", required = false) String groupsTable,

                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") int pageSize

    ) {
        TableDetail tableDetail = new TableDetail();

        tableDetail.setTableNameEn(tableNameEn);
        tableDetail.setTableDescription(tableDescription);
        tableDetail.setTableColumn(tableColumn);
        tableDetail.setProject(project);
        tableDetail.setModuleServer(moduleServer);
        tableDetail.setTableOwner(tableOwner);
        tableDetail.setGroupsTable(groupsTable);
        tableDetail.setPageNum((pageNum - 1) * pageSize);
        tableDetail.setPageSize(pageSize);
        List<TableDetail> list = tableDetailMapper.queryTableDetail(tableDetail);
        int count = tableDetailMapper.queryTableDetailCount(tableDetail);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return fillResponse(jsonObject);
    }

    //配置
    @PostMapping("/addTableDetail")
    public String add(@RequestParam(value = "tableName", required = false) String tableNameEn,
                      @RequestParam(value = "tableDescription", required = false) String tableDescription,
                      @RequestParam(value = "tableColumn", required = false) String tableColumn,
                      @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                      @RequestParam(value = "moduleServer", required = false) String moduleServer,
                      @RequestParam(value = "tableOwner", required = false) String tableOwner,
                      @RequestParam(value = "groupsTable", required = false) String groupsTable
    ) {
        TableDetail tableDetail = new TableDetail();
        tableDetail.setTableNameEn(tableNameEn);
        tableDetail.setTableDescription(tableDescription);
        tableDetail.setTableColumn(tableColumn);
        tableDetail.setProject(project);
        tableDetail.setModuleServer(moduleServer);
        tableDetail.setTableOwner(tableOwner);
        tableDetail.setGroupsTable(groupsTable);

        int i = tableDetailMapper.addTableDetail(tableDetail);
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

    @PostMapping("/updateTableDetail")
    public String update(@RequestParam(value = "tableNameEn", required = false) String tableNameEn,
                         @RequestParam(value = "tableDescription", required = false) String tableDescription,
                         @RequestParam(value = "tableColumn", required = false) String tableColumn,
                         @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                         @RequestParam(value = "moduleServer", required = false) String moduleServer,
                         @RequestParam(value = "tableOwner", required = false) String tableOwner,
                         @RequestParam(value = "groupsTable", required = false) String groupsTable,
                         int id) {
        TableDetail tableDetail = new TableDetail();

        tableDetail.setId(id);
        tableDetail.setTableNameEn(tableNameEn);
        tableDetail.setTableDescription(tableDescription);
        tableDetail.setTableColumn(tableColumn);
        tableDetail.setProject(project);
        tableDetail.setModuleServer(moduleServer);
        tableDetail.setTableOwner(tableOwner);
        tableDetail.setGroupsTable(groupsTable);

        int i = tableDetailMapper.updateTableDetail(tableDetail);
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


    //@PostMapping("/delete")
    //public String delete(@RequestParam("id") int id) {
    //    //int delete = apiContentMapper.deleteApi(id);
    //    if (delete == 0)
    //        return "删除失败";
    //    else
    //        return "删除成功";
    //}


    public final static Response fillResponse(Object data) {
        Response response = new Response();
        response.code = 1;
        response.data = data;
        return response;
    }


    public static void main(String[] args) {

    }

    @Data
    @ToString
    public static class Response {
        private int code;
        private Object data;
    }


}
