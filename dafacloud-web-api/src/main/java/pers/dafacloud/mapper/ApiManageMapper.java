package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.dafacloud.model.ApiManage;

import java.util.List;

@Mapper
public interface ApiManageMapper {

    /**
     * 新增api接口
     */

    int addApi(ApiManage apiManage);

    /**
     * 克隆api
     */
    int cloneApi(int id);

    /**
     * 删除api接口
     */
    int deleteApi(int id);

    /**
     * 修改api接口
     */
    int updateApi(ApiManage apiManage);

    /**
     * 查询api接口
     */

    List<ApiManage> getApiList(ApiManage apiManage);

    /**
     * 通过 id 查询api接口
     */
    ApiManage getApiById(int id);

    /**
     * 批量测试
     * */
    List<ApiManage> getBatchTestApiList(@Param("apiGroups") String apiGroups, @Param("owner") String owner);

    /**
     * 查询api接口数量
     */
    int getApiListCount(ApiManage apiManage);


}
