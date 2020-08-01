package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.kibana.IPLimit;
import pers.dafacloud.utils.Response;

@RestController
@RequestMapping("/v1")
public class IPLimitController {

    @GetMapping("/queryLimitIPCms")
    public Response login(String queryUrl, String timeType, String queryType) {
        if (StringUtils.isEmpty(queryUrl)) {
            return Response.fail("查询域名不能为空");
        }

        if (StringUtils.isEmpty(timeType)) {
            return Response.fail("查询参数不能为空");
        }
        JSONObject returnJson;
        returnJson = IPLimit.queryLimitIPCms(queryUrl, timeType, queryType);
        return Response.returnData("获取成功", 1, returnJson);
    }

}
