package pers.dafacloud.mapper.apiContent;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.model.ApiContent;

import java.util.List;

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
     * 查询api接口数量
     */
    int queryApiCount(ApiContent apiContent);


}
