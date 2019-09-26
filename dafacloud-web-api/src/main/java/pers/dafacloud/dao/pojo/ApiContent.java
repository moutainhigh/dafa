package pers.dafacloud.dao.pojo;


public class ApiContent {



    private int id; //id自增主键
    private String name; //接口名称
    private String path;//接口路径
    private String method;// 请求方法
    private String reqParameters;//post参数
    private String headerData;// 请求头
    private String dependApiName;// 依赖接口
    private String module;// 所属模块
    private String cmsFront;//所属页面
    private String project;// 所属项目
    private String description;//接口简介
    private String owner;//所属用户
    private int pageNum;
    private int pageSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getReqParameters() {
        return reqParameters;
    }

    public void setReqParameters(String reqParameters) {
        this.reqParameters = reqParameters;
    }

    public String getHeaderData() {
        return headerData;
    }

    public void setHeaderData(String headerData) {
        this.headerData = headerData;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    //@Override
    //public String toString() {
    //    return "ApiContent{" +
    //            "id=" + id +
    //            ", name='" + name + '\'' +
    //            ", path='" + path + '\'' +
    //            ", method='" + method + '\'' +
    //            ", body='" + body + '\'' +
    //            ", header='" + header + '\'' +
    //            ", dependApiName='" + dependApiName + '\'' +
    //            ", module='" + module + '\'' +
    //            ", page='" + cmsFront + '\'' +
    //            ", project='" + project + '\'' +
    //            ", description='" + description + '\'' +
    //            ", owner='" + owner + '\'' +
    //            '}';
    //}
}
