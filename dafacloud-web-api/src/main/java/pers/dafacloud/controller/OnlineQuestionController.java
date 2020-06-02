package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.server.OnlineQuestionServer;
import pers.dafacloud.entity.OnlineQuestion;
import pers.dafacloud.utils.excelUtil.ExcelUtiles;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class OnlineQuestionController {

    //SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    //OnlineQuestionMapper onlineQuestionMapper = sqlSession.getMapper(OnlineQuestionMapper.class);

    @Autowired
    OnlineQuestionServer onlineQuestionServer;

    @GetMapping(value = "/getQuestion")
    public String query(String tester, @RequestParam(defaultValue = "-1") int isSolve,
                        @RequestParam(defaultValue = "-1") int type,@RequestParam(defaultValue = "-1")  int project,
                        String questionName, String startQuestionDate, String endQuestionDate, int pageNum, int pageSize) {
        Map map = new HashMap();
        map.put("tester", tester);
        map.put("isSolve", isSolve);
        map.put("startQuestionDate", startQuestionDate);
        map.put("endQuestionDate", endQuestionDate);
        map.put("type", type);
        map.put("questionName", questionName);
        map.put("project", project);
        map.put("pageNum", (pageNum - 1) * pageSize);
        map.put("pageSize", pageSize);
        List<Map> list = onlineQuestionServer.questionList(map);
        JSONObject jsonObject0 = new JSONObject();
        jsonObject0.put("code", 1);
        JSONObject jsonObject = new JSONObject();
        int count = onlineQuestionServer.questionCount(map);
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        jsonObject0.put("data", jsonObject);
        return jsonObject0.toString();
    }

    @GetMapping(value = "/exportQuestion")
    public void exportQuestion(String tester, @RequestParam(defaultValue = "-1") int isSolve,
                               @RequestParam(defaultValue = "-1") int type,@RequestParam(defaultValue = "-1")  int project,
                               String questionName, String startQuestionDate, String endQuestionDate,
                               HttpServletResponse response) {
        Map map = new HashMap();
        map.put("tester", tester);
        map.put("isSolve", isSolve);
        map.put("startQuestionDate", startQuestionDate);
        map.put("endQuestionDate", endQuestionDate);
        map.put("type", type);
        map.put("project", project);
        map.put("questionName", questionName);
        //map.put("pageNum", (pageNum-1)*pageSize);
        //map.put("pageSize", pageSize);
        List<OnlineQuestion> list = onlineQuestionServer.exportQuestion(map);
        /*HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("故障记录");
        String fileName = "question" + ".xls";
        int rowNum = 1;
        String[] headers = {"学号", "姓名", "身份类型", "登录密码"};
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        for (Map question : list) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(question.get("questionName").toString());
            row1.createCell(1).setCellValue(question.get("isSolve").toString());
            row1.createCell(2).setCellValue(question.get("questionName").toString());
            row1.createCell(3).setCellValue(question.get("description").toString());
            rowNum++;
        }*/
        ExcelUtiles.exportExcel(list, "title", "sheet01", OnlineQuestion.class, "question.xlsx", response);
        //return "";
    }


    @PostMapping(value = "/addQuestion")
    public String addQuestion(String questionName, String description, String type,
                              String page, String module, String testerReply,int project,
                              String isSolve, String develop, String tester, String questionDate) {
        Map map = new HashMap();
        map.put("questionName", questionName);
        map.put("description", description);
        map.put("type", type);
        map.put("page", page);
        map.put("module", module);
        map.put("project", project);
        map.put("testerReply", testerReply);
        map.put("isSolve", isSolve);
        map.put("develop", develop);
        map.put("tester", tester);
        map.put("questionDate", questionDate);

        int count = onlineQuestionServer.addQuestion(map);
        JSONObject jsonObject = new JSONObject();
        if (count == 1) {
            jsonObject.put("code", 1);
            jsonObject.put("data", "新增成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("data", "新增失败");
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/deleteQuestion")
    public String deleteQuestion(int id) {
        int count = onlineQuestionServer.deleteQuestion(id);
        JSONObject jsonObject = new JSONObject();
        if (count == 1) {
            jsonObject.put("code", 1);
            jsonObject.put("data", "删除成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("data", "删除失败");
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/updateQuestion")
    public String updateQuestion(String id, String questionName, String description, String type,
                                 String page, String module, String testerReply,int project,
                                 String isSolve, String develop, String tester, String questionDate) {
        Map map = new HashMap();
        map.put("id", id);
        map.put("questionName", questionName);
        map.put("description", description);
        map.put("type", type);
        map.put("page", page);
        map.put("module", module);
        map.put("project", project);
        map.put("testerReply", testerReply);
        map.put("isSolve", isSolve);
        map.put("develop", develop);
        map.put("tester", tester);
        map.put("questionDate", questionDate);

        int count = onlineQuestionServer.updateQuestion(map);
        JSONObject jsonObject = new JSONObject();
        if (count == 1) {
            jsonObject.put("code", 1);
            jsonObject.put("data", "修改成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("data", "修改失败");
        }
        return jsonObject.toString();
    }

}
