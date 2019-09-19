package pers.dafacloud.Dao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.mapper.tenantOpenMessage.TenantOpenMessageMapper;

import java.util.List;
import java.util.Map;

public class GetOpenNumber {

    /**
     * 1418 站長快3
     * 1419 站長5分快3
     * <p>
     * 1018 站長时时彩
     * 1019 站長5分时时彩
     */
    public static void main(String[] args) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
        TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);
        List<Map> list = tenantOpenMessageMapper.getOpenNumber(1018);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }


}
