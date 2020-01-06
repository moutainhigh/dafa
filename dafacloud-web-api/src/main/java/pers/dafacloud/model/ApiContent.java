package pers.dafacloud.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiContent {
    private int id; //id自增主键
    private String apiName; //接口名称
    private String path;//接口路径
    private String method;// 请求方法
    private String reqParametersArray;//post参数
    private String headerArray;// 请求头
    private String module;// 所属模块
    private int cmsFront;//所属页面
    private int project;// 所属项目
    private String description;//接口描述
    private String owner;//所属
    private String responseBody;//返回信息示例
    private int pageNum;
    private int pageSize;

    private String groupsApi; //分组
    private String sort = "-1"; //排序

    /**
     * 依赖接口1
     */
    private String dePath;
    private String deMethod;
    private String deReqParametersArray;
    private String deReturnValue;
    /**
     * 依赖接口2
     */
    private String dePath2;
    private String deMethod2;
    private String deReqParametersArray2;
    private String deReturnValue2;

    private String reqParametersString;
    private String deReqParametersString;
    private String deReqParametersString2;

    private String cookie;// 全局cookie
    private String host; //全局host
    private boolean isLoginReq;
}
