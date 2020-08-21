package pers.dafacloud.entity;

import lombok.Data;

@Data
public class UsersEnvironmentConfig {
    String id;
    String evName;
    String evHost;
    int evType;
    String evCookie;
    int evState;
    String username;
    String remark;
}
