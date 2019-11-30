package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.model.TableDetail;
import pers.dafacloud.server.TableDetailServer;
import pers.dafacloud.utils.Response;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TableDetailContrller {

    //SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    //TableDetailMapper tableDetailMapper = sqlSession.getMapper(TableDetailMapper.class);

    @Autowired
    TableDetailServer tableDetailServer;

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
        List<TableDetail> list = tableDetailServer.tableDetailList(tableDetail);//tableDetailMapper.queryTableDetail(tableDetail);
        int count = tableDetailServer.tableDetailCount(tableDetail);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);

        return Response.success(jsonObject);
    }

    //配置
    @PostMapping("/addTableDetail")
    public Response add(@RequestParam(value = "tableNameEn", required = false) String tableNameEn,
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

        int i = tableDetailServer.addTableDetail(tableDetail);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/cloneTableDetail")
    public Response cloneTableDetail(int id) {
        int i = tableDetailServer.cloneTableDetail(id);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/updateTableDetail")
    public Response update(@RequestParam(value = "tableNameEn", required = false) String tableNameEn,
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

        int i = tableDetailServer.updateTableDetail(tableDetail);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }


}
