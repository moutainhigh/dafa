package pers.dafacloud.entity;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String username;
    private String password;
    private String roleId;
    private String isTest;
    private String remark;
}
