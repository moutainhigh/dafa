package pers.dafacloud.dao.pojo;


public class ApiContent {



    private int id; //id自增主键
    private String name; //接口名称
    private String path;//接口路径
    private String method;// 请求方法
    private String body;//post参数
    private String header;// 请求头
    private String dependApiName;// 依赖接口
    private String module;// 所属模块
    private String page;//所属页面
    private String project;// 所属项目
    private String description;//接口简介
    private String owner;//所属用户


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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ApiContent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", body='" + body + '\'' +
                ", header='" + header + '\'' +
                ", dependApiName='" + dependApiName + '\'' +
                ", module='" + module + '\'' +
                ", page='" + page + '\'' +
                ", project='" + project + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
