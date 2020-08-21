package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.entity.UserParameters;

import java.util.List;

@Mapper
public interface UserParametersMapper {

    List<UserParameters> getUserParametersList(String username);
    List<UserParameters> getUserParametersAllList();


    int getUserParametersCount(String username);

    int addUserParameters(UserParameters userParameters);

    int updateUserParameters(UserParameters userParameters);

    int deleteUserParameters(String id);

    UserParameters getUserParametersById(String id);

}
