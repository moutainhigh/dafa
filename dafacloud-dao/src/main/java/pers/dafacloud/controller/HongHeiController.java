package pers.dafacloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pers.dafacloud.mapper.HongHeiMapper;
import pers.dafacloud.pojo.HongHei;

import java.util.List;
import java.util.Map;


//@Controller
//@ResponseBody
@RestController
public class HongHeiController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    HongHeiMapper hongHeiMapper ;

    @GetMapping("/honghei")
    public Map list(){
        List<Map<String,Object>>  maps = jdbcTemplate.queryForList("select  * from honghei");
        Map<String,Object> map = maps.get(0);
        return map;
    }


    @GetMapping("/honghei2/{bid}")
    public HongHei list2(@PathVariable("bid") Integer bid){

        HongHei hh = hongHeiMapper.getHongHeiByid(bid);
        return hh;
    }

}
