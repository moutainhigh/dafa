package dafagame.testCase.cms.tenant.transactionManage.renGongCunKuan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.dafagame.utils.httpUtils.Request;

/**
 *人工存款保存接口（后台POST）
 * */
public class SaveBatchManualRecord {

    private final static Logger Log = LoggerFactory.getLogger(SaveBatchManualRecord.class);
    static Path path = Path.saveBatchManualRecord;

    @Test(description = "人工存款")
    public void test001(){
        String body = "userName=99581194,21712495,55015778&amount=10000&remark=duke人工存款&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "用户不存在")
    public void test002(){
        String body = "userName=88467689duke&amount=10&remark=&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    /*@Test(description = "其他站账号")
    public void test003(){
        String body = "userName=88467689duke&amount=10&remark=&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }*/

    /*@Test(description = "冻结账号")
    public void test004(){
        String body = "userName=88467689duke&amount=10&remark=&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }*/

    //ok
    @Test(description = "多个账号存款")
    public void test005(){
        String body = "userName=88467689,48955010,35998070&amount=10&remark=duke测试导入&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "多个账号存款,部分正确")
    public void test006(){
        String body = "userName=88467689,48955010,35998070,35998070duke&amount=10&remark=duke测试导入&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "多个账号存款,重复账号")
    public void test007(){
        String body = "userName=88467689,48955010,35998070,35998070&amount=10&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    /*@Test(description = "多个账号存款,账号等于100")
    public void test008(){
        String body = "userName=88467689,48955010,35998070,35998070&amount=10&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }*/

    //ok
    @Test(description = "amount非法")
    public void test009(){
        String body = "userName=88467689&amount=duke&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "小于人工存款设置金额")
    public void test010(){
        String body = "userName=88467689&amount=0.9&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "等于人工存款设置最小金额")
    public void test011(){
        String body = "userName=88467689&amount=1&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "大于人工存款设置最大金额")
    public void test012(){
        String body = "userName=88467689&amount=1&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "大于人工存款设置最大金额")
    public void test013(){
        String body = "userName=88467689&amount=100001&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "等于人工存款设置最大金额")
    public void test014(){
        String body = "userName=88467689&amount=100000&remark=duke测试导入01&dictionId=401";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "提款大于金额")
    public void test015(){
        String body = "userName=88467689&amount=100233&remark=duke测试导入01&dictionId=405";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "提款等于金额")
    public void test016(){
        String body = "userName=88467689&amount=100232&remark=duke测试导入01提款等于金额&dictionId=405";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "部分小于余额，部分大于余额")
    public void test017(){
        String body = "userName=88467689,35998070,15805709,75099274" +
                "&amount=1000&remark=duke测试导入部分小于余额，部分大于余额&dictionId=405";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "dictionId错误")
    public void test018(){
        String body = "userName=88467689&amount=100232&remark=duke测试导入01提款等于金额&dictionId=409";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    //ok
    @Test(description = "402人工存入")
    public void test019(){
        String body = "userName=88467689&amount=10&remark=duketest402人工存入&dictionId=402";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }


    @Test(description = "403其他优惠")
    public void test020(){
        String body = "userName=88467689&amount=100&remark=duke测试：其他优惠&dictionId=403";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "404误存提出")
    public void test021(){
        String body = "userName=88467689&amount=100&remark=duke测试：误存提出&dictionId=404";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "405行政提出")
    public void test022(){
        String body = "userName=88467689&amount=100&remark=duke测试：行政提出&dictionId=405";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }

    @Test(description = "405行政提出")
    public void test023(){
        String body = "userName=88467689&amount=100&remark=duke测试：行政提出&dictionId=405";
        String result = Request.doPost(path.value,body);
        System.out.println(result);
        Reporter.log(result);
        Log.info(result);
        //Assert.assertEquals(true,s.contains("获取成功"),"");
    }








}
