package pers.dafacloud.mapper.tableDetail;

import pers.dafacloud.pojo.TableDetail;

import java.util.List;

public interface TableDetailMapper {

    /**
     * 查询api接口
     */

    List<TableDetail> queryTableDetail(TableDetail tableDetail);

    /**
     * 查询api接口数量
     */
    int queryTableDetailCount(TableDetail tableDetail);

    /**
     * 新增api接口
     */
    int addTableDetail(TableDetail tableDetail);

    /**
     * 修改api接口
     */
    int updateTableDetail(TableDetail tableDetail);

    /**
     * 删除api接口
     */
    //int deleteApi(int id);


}
