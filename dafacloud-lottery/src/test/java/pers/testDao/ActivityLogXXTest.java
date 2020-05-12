package pers.testDao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.mapper.activityLogXX.ActivityLogXXMapper;
import pers.dafacloud.mapper.in_first.InFirstMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityLogXXTest {

    private static volatile int Guid = 100;

    static SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("dev");
    static ActivityLogXXMapper activityLogXXMapper = sqlSessionTransaction.getMapper(ActivityLogXXMapper.class);

    //
    public static void main(String[] args) {
        List<Map> mapList = new ArrayList<>();
        //List<String> InFirstUsers = FileUtil.readFile(ActivityLogXXTest.class.getResourceAsStream("/txt/InFirstUser.txt"));
        for (int i = 210000; i < 220000; i++) {
            String hd = String.format("HD260673441549%06d", i);
            System.out.println(hd);
            Map map = new HashMap();
            map.put("tenant_code", "dafa");
            map.put("user_id", "59202154");
            map.put("user_name", "testluck2");
            map.put("record_code", hd);
            map.put("amount", "6.6");
            map.put("activity_type", "3");
            map.put("recharge_type", "人工存款");
            map.put("is_receive", 1);
            map.put("source_id", "0");
            map.put("is_test", 0);
            map.put("created_date", "2020-04-29");

            map.put("gmt_created", "2020-04-29 16:55:00.000");
            map.put("gmt_modified", "2020-04-29 16:55:00.000");
            mapList.add(map);
        }
        int i = activityLogXXMapper.addActivityLogXX(mapList);
        System.out.println("写入：" + i);

        //System.out.println(getGuid());

    }

    //public static String getGuid() {
    //
    //    ActivityLogXXTest.Guid+=1;
    //    System.out.println(ActivityLogXXTest.Guid);
    //
    //    long now = System.currentTimeMillis();
    //    //获取4位年份数字
    //    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
    //    //获取时间戳
    //    String time=dateFormat.format(now);
    //    String info=now+"";
    //    //获取三位随机数
    //    //int ran=(int) ((Math.random()*9+1)*100);
    //    //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
    //    int ran=0;
    //    if(ActivityLogXXTest.Guid>999){
    //        ActivityLogXXTest.Guid=100;
    //    }
    //    ran=ActivityLogXXTest.Guid;
    //
    //    return time+info.substring(2, info.length())+ran;
    //}
}
