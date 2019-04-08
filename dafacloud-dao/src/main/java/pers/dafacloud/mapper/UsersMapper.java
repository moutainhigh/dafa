package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper {

    //void insertHongHei(HongHei hongHei);

//    @Select("select * from provider where pid=#{pid}")
//    HongHei getHongHeiZj(Integer pid);

    //HongHei getHongHei(Integer pid);

    int updateUserPassword(Integer pid, String password);

}
