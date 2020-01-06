package pers.dafacloud.mapper.apiContent;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dafacloud.model.ApiContent;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiContentMapper {

    /**
     * 新增api接口
     */

    int addApi(ApiContent apiContent);

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
    int updateApi(ApiContent apiContent);

    /**
     * 查询api接口
     */

    List<ApiContent> queryApi(ApiContent apiContent);

    /**
     * 批量测试
     * */
    List<ApiContent> queryApiBatchTest(@Param("groupsApi") String groupsApi,@Param("owner") String owner);

    /**
     * 查询api接口数量
     */
    int queryApiCount(ApiContent apiContent);


}
