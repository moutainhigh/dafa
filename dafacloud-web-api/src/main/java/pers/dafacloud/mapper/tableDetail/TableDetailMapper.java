package pers.dafacloud.mapper.tableDetail;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.entity.TableDetail;

import java.util.List;

@Mapper
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

    int cloneTableDetail(int id);

    /**
     * 修改api接口
     */
    int updateTableDetail(TableDetail tableDetail);

    /**
     * 删除api接口
     */
    //int deleteApi(int id);


}
