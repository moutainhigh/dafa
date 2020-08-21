package pers.dafacloud.entity;

import lombok.Data;

@Data
public class UserParameters {

    String id;
    String parameterName;
    String parameterValue;
    int parameterType;
    String username;
    String remark;
    int state;
}
