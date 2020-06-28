package pers.dafacloud.testDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExportBetContentServerTest {
    @Autowired
    pers.dafacloud.server.ExportBetContentServer exportBetContentServer;


    @Test
    public void test01() {
        List<Map> list = exportBetContentServer.getBetContent("dafa", "2020-06-27", "0");
        System.out.println(list.size());
    }
}
