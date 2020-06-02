package pers.dafacloud.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TestApiResult {

    private int id;

    private String host;

    private String apiName;

    private String apiPath;

    private int apiMethod;

    private String testResult;

    private String dependentResult1;

    private String isPass;

    private List<String> isPassList;

    private int cmsFront;

    private String dependentResult2;

    private String testExecutor;

    private String testBatch;

    private String assertContent;

    private int pageNum;
    private int pageSize;

    private String insertTime;
}
