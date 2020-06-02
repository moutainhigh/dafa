package pers.dafacloud.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiManage {
    private int id;
    private String apiName; //接口名称

    private String description;//接口描述

    private String path;//接口路径
    private int method;// 请求方法

    private String requestParameters;

    private String requestHeader;

    private int cmsFront;//所属页面
    private int project;// 所属项目

    private String projectModule;// 所属模块

    private String owner;//所属
    private String assertResponse; //响应断言
    private String responseComments;//返回信息示例

    private String apiGroups; //分组
    private String sort = "-1"; //排序

    private String dependentApi;//依赖接口

    private String cookie;// 全局cookie
    private String host; //全局host
    private boolean isLoginReq = false;

    private String assertType;
    private String assertContent;

    private int pageNum;
    private int pageSize;

}
