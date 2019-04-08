package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import pers.dafacloud.pojo.HongHei;

@Mapper
@Repository
public interface HongHeiMapper {

    //void insertHongHei(HongHei hongHei);

//    @Select("select * from provider where pid=#{pid}")
//    HongHei getHongHeiZj(Integer pid);

    //HongHei getHongHei(Integer pid);

    HongHei getHongHeiByid(Integer pid);

}
