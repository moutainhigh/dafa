package pers.dafacloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.server.ExportBetContentServer;
import pers.dafacloud.utils.Response;

@RestController
@RequestMapping("/v1")
public class ExportDataController {

    @Autowired
    ExportBetContentServer exportBetContentServer;

    @GetMapping("/exportData")
    public Response exportData(String host, String cookie) {
        System.out.println(exportBetContentServer.getBetContent("dafa", "2020-06-29", "0"));
        return Response.success("成功");
    }


}
