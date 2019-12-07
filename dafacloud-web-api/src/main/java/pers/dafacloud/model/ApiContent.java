package pers.dafacloud.model;


public class ApiContent {

    private int id; //id自增主键
    private String apiName; //接口名称
    private String path;//接口路径
    private String method;// 请求方法
    private String reqParametersArray;//post参数
    private String headerArray;// 请求头
    private String dependApiName;// 依赖接口
    private String module;// 所属模块
    private String cmsFront;//所属页面
    private int project;// 所属项目
    private String description;//接口简介
    private String owner;//所属用户
    private int pageNum;
    private int pageSize;
    private String responseBody;

    private String dePath;
    private String deMethod;
    private String deReqParametersArray;
    private String deReturnValue;

    private String dePath2;
    private String deMethod2;
    private String deReqParametersArray2;
    private String deReturnValue2;

    private String groupsApi; //分组

    private String sort; //排序

    private String reqParametersString;
    private String deReqParametersString;
    private String deReqParametersString2;

    private boolean isLoginReq;

    public boolean isLoginReq() {
        return isLoginReq;
    }

    public void setIsLoginReq(boolean loginReq) {
        isLoginReq = loginReq;
    }

    public String getReqParametersString() {
        return reqParametersString;
    }

    public void setReqParametersString(String reqParametersString) {
        this.reqParametersString = reqParametersString;
    }

    public String getDeReqParametersString() {
        return deReqParametersString;
    }

    public void setDeReqParametersString(String deReqParametersString) {
        this.deReqParametersString = deReqParametersString;
    }

    public String getDeReqParametersString2() {
        return deReqParametersString2;
    }

    public void setDeReqParametersString2(String deReqParametersString2) {
        this.deReqParametersString2 = deReqParametersString2;
    }

    public String getDePath2() {
        return dePath2;
    }

    public void setDePath2(String dePath2) {
        this.dePath2 = dePath2;
    }

    public String getDeMethod2() {
        return deMethod2;
    }

    public void setDeMethod2(String deMethod2) {
        this.deMethod2 = deMethod2;
    }

    public String getDeReqParametersArray2() {
        return deReqParametersArray2;
    }

    public void setDeReqParametersArray2(String deReqParametersArray2) {
        this.deReqParametersArray2 = deReqParametersArray2;
    }

    public String getDeReturnValue2() {
        return deReturnValue2;
    }

    public void setDeReturnValue2(String deReturnValue2) {
        this.deReturnValue2 = deReturnValue2;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String cookie;
    private String host;


    public String getGroupsApi() {
        return groupsApi;
    }

    public void setGroupsApi(String groupsApi) {
        this.groupsApi = groupsApi;
    }

    public String getDePath() {
        return dePath;
    }

    public void setDePath(String dePath) {
        this.dePath = dePath;
    }

    public String getDeMethod() {
        return deMethod;
    }

    public void setDeMethod(String deMethod) {
        this.deMethod = deMethod;
    }

    public String getDeReqParametersArray() {
        return deReqParametersArray;
    }

    public void setDeReqParametersArray(String deReqParametersArray) {
        this.deReqParametersArray = deReqParametersArray;
    }

    public String getDeReturnValue() {
        return deReturnValue;
    }

    public void setDeReturnValue(String deReturnValue) {
        this.deReturnValue = deReturnValue;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReqParametersArray() {
        return reqParametersArray;
    }

    public void setReqParametersArray(String reqParametersArray) {
        this.reqParametersArray = reqParametersArray;
    }

    public String getHeaderArray() {
        return headerArray;
    }

    public void setHeaderArray(String headerArray) {
        this.headerArray = headerArray;
    }

    public String getDependApiName() {
        return dependApiName;
    }

    public void setDependApiName(String dependApiName) {
        this.dependApiName = dependApiName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCmsFront() {
        return cmsFront;
    }

    public void setCmsFront(String cmsFront) {
        this.cmsFront = cmsFront;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", apiName='" + apiName + '\'' +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", reqParametersArray='" + reqParametersArray + '\'' +
                ", headerArray='" + headerArray + '\'' +
                ", dependApiName='" + dependApiName + '\'' +
                ", module='" + module + '\'' +
                ", cmsFront='" + cmsFront + '\'' +
                ", project=" + project +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", responseBody='" + responseBody + '\'' +
                ", dePath='" + dePath + '\'' +
                ", deMethod='" + deMethod + '\'' +
                ", deReqParametersArray='" + deReqParametersArray + '\'' +
                ", deReturnValue='" + deReturnValue + '\'' +
                ", dePath2='" + dePath2 + '\'' +
                ", deMethod2='" + deMethod2 + '\'' +
                ", deReqParametersArray2='" + deReqParametersArray2 + '\'' +
                ", deReturnValue2='" + deReturnValue2 + '\'' +
                ", groupsApi='" + groupsApi + '\'' +
                ", sort='" + sort + '\'' +
                ", reqParametersString='" + reqParametersString + '\'' +
                ", deReqParametersString='" + deReqParametersString + '\'' +
                ", deReqParametersString2='" + deReqParametersString2 + '\'' +
                ", isLoginReq=" + isLoginReq +
                ", cookie='" + cookie + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
