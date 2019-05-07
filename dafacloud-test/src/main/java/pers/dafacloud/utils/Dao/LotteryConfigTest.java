package pers.dafacloud.utils.Dao;


import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.utils.Dao.pojo.LotteryConfigPojo;
import pers.dafacloud.utils.Dao.mapper.LotteryConfigMapper;

import java.util.List;

public class LotteryConfigTest {

    public static void getLotteryConfig(){
    }

    public static void main(String[] args) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        LotteryConfigMapper lotteryConfigMapper = sqlSession.getMapper(LotteryConfigMapper.class);
        List<LotteryConfigPojo> list = lotteryConfigMapper.getLotteryConfig();
        System.out.println(list.size());
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            LotteryConfigPojo lotteryConfigPojo = list.get(i);
            JSONObject jsonObject2 = new JSONObject();
//            System.out.println(lotteryConfigPojo.getLotteryClassName());
            jsonObject2.element("lotteryClassName",lotteryConfigPojo.getLotteryClassName());//快3
            jsonObject2.element("lotteryType",lotteryConfigPojo.getLotteryType());//k3
            jsonObject2.element("lotteryName",lotteryConfigPojo.getLottreyName());//UU快3
            switch (lotteryConfigPojo.getLotteryType()){
                case "SSC":
                    jsonObject2.element("lotteryTypeNum","0");//0-8
                    break;
                case "K3":
                    jsonObject2.element("lotteryTypeNum","1");
                    break;
                case "SYX5":
                    jsonObject2.element("lotteryTypeNum","2");
                    break;
                case "FC3D":
                    jsonObject2.element("lotteryTypeNum","3");
                    break;
                case "PL35":
                    jsonObject2.element("lotteryTypeNum","4");
                    break;
                case "KL8":
                    jsonObject2.element("lotteryTypeNum","5");
                    break;
                case "PK10":
                    jsonObject2.element("lotteryTypeNum","6");
                    break;
                case "LHC":
                    jsonObject2.element("lotteryTypeNum","7");
                    break;
            }
            jsonObject.element(lotteryConfigPojo.getLotteryCode(),jsonObject2);
        }
        //生成json
        System.out.println(jsonObject);
        // 关闭资源
        sqlSession.close();
    }
}
