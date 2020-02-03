package pers.dafacloud.Dao.model;

/**
 * 报表服务 InRecord,充值记录
 */
public class InRecord {

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InRecord{" +
                "id='" + id + '\'' +
                '}';
    }
}
