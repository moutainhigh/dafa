package pers.dafacloud.utils.excelUtil;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

//import javax.persistence.*;
import java.util.Date;

//@Entity
//@Table(name = "seckill")
@Data
public class ExcelPojo {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "id", orderNum = "0")
    private Long seckillId;

    //@Column(name = "name")
    @Excel(name = "姓名", orderNum = "1")
    private String name;

    //@Column(name = "number")
    @Excel(name = "数量", orderNum = "2")
    private int number;

    //@Column(name = "start_time")
    @Excel(name = "开始日期", orderNum = "3", importFormat = "yyyy-MM-dd HH:mm:ss")//exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //@Column(name = "end_time")
    @Excel(name = "结束日期", orderNum = "4", importFormat = "yyyy-MM-dd HH:mm:ss")//exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //@Column(name = "create_time")
    @Excel(name = "创建日期", orderNum = "5", importFormat = "yyyy-MM-dd HH:mm:ss")//exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
