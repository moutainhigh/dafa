package pers.dafacloud.model;

public class TestApiResult {

    private int id;

    private  String host ;

    private String apiName;

    private String apiPath;

    private String apiMethod;

    private String testResult;

    private String dependentResult1;

    private int isPass;

    private String cmsFront;

    private String dependentResult2;

    private String testExecutor;

    private String testBatch;

    private int pageNum;
    private int pageSize;

    private String insertTime;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
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

    public String getCmsFront() {
        return cmsFront;
    }

    public void setCmsFront(String cmsFront) {
        this.cmsFront = cmsFront;
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

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getDependentResult1() {
        return dependentResult1;
    }

    public void setDependentResult1(String dependentResult1) {
        this.dependentResult1 = dependentResult1;
    }


    public String getDependentResult2() {
        return dependentResult2;
    }

    public void setDependentResult2(String dependentResult2) {
        this.dependentResult2 = dependentResult2;
    }

    public String getTestExecutor() {
        return testExecutor;
    }

    public void setTestExecutor(String testExecutor) {
        this.testExecutor = testExecutor;
    }

    public String getTestBatch() {
        return testBatch;
    }

    public void setTestBatch(String testBatch) {
        this.testBatch = testBatch;
    }
}
