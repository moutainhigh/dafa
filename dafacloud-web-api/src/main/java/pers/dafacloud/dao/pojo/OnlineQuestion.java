package pers.dafacloud.dao.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class OnlineQuestion {
    /**
     * id , -- id 自增
     * question_name questionName, -- '简介'
     * description , -- '描述'
     * type , -- bug,需求,优化,运维
     * page , -- '所属页面'
     * module, -- '所属模块'
     * tester_reply testerReply,-- '测试回复',
     * is_solve isSolve,-- '是否解决',
     * develop ,
     * on_duty tester,
     * DATE_FORMAT(question_date,'%Y-%m-%d') questionDate
     */
    //@Excel(name = "id")
    private int id;

    //序号
    @Excel(name = "序号", orderNum = "1")
    private int num;

    //exportFormat = "yyyy-MM-dd HH:mm:ss") importFormat = "YYYY-MM-dd"
    @Excel(name = "日期", orderNum = "2", exportFormat = "YYYY-MM-dd",width = 16)
    private Date questionDate;

    @Excel(name = "值班人", orderNum = "3")
    private String tester;

    //@Excel(name = "名称", orderNum = "")
    private String questionName;

    @Excel(name = "申报问题", orderNum = "4", width = 60)
    private String description;

    //@Excel(name = "类型", orderNum = "")
    private int type;

    @Excel(name = "是否解决", orderNum = "5", replace = {"是_1", "否_2"})
    //replace = { "是_1", "否_2" }replace = { "1_是", "2_否" } , suffix = ""
    private int isSolve;

    @Excel(name = "定位原因", orderNum = "6", width = 60)
    private String testerReply;

    //@Excel(name = "开发", orderNum = "6")
    //private String develop;
}
