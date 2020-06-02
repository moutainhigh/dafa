package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.entity.TestApiResult;
import pers.dafacloud.server.TestApiResultServer;
import pers.dafacloud.utils.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TestApiResultContrller {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestApiResultServer testApiResultServer;

    @GetMapping("/searchTestApiResult")
    public Response query(String apiName,
                          String apiPath,
                          String isPass,
                          @RequestParam(value = "cmsFront", required = false, defaultValue = "-1") int cmsFront,
                          String testExecutor,
                          String testBatch,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") int pageSize
    ) {
        TestApiResult testApiResult = new TestApiResult();
        testApiResult.setIsPassList(new ArrayList<>(Arrays.asList(isPass.split(","))));
        testApiResult.setIsPass(isPass);
        testApiResult.setApiName(apiName);
        testApiResult.setApiPath(apiPath);
        testApiResult.setCmsFront(cmsFront);
        testApiResult.setTestExecutor(testExecutor);
        testApiResult.setTestBatch(testBatch);

        testApiResult.setPageSize(pageSize);
        testApiResult.setPageNum((pageNum - 1) * pageSize);

        logger.info(testApiResult.toString());

        List<TestApiResult> list = testApiResultServer.searchTestApiResult(testApiResult);
        int count = testApiResultServer.searchTestApiResultCount(testApiResult);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return Response.success(jsonObject);
    }
}
