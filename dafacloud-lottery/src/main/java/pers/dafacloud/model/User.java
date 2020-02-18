package pers.dafacloud.model;

public class User {

    private String username;
    private String userid;
    private String tenantCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", tenantCode='" + tenantCode + '\'' +
                '}';
    }
}
