package pers.dafacloud.mapper.apiContent;

import pers.dafacloud.pojo.ApiContent;

import java.util.List;

public interface ApiContentMapper {

    /**
     * 新增api接口
     */
    int addApi(ApiContent apiContent);

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
