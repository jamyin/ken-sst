package com.tianfang.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class AdminLogDto implements Serializable{

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String adminId;

    @Setter
    @Getter
    private String adminAccount;

    @Setter
    @Getter
    private String serviceName;

    @Setter
    @Getter
    private String methodName;

    @Setter
    @Getter
    private String args;

    @Setter
    @Getter
    private String result;

    @Setter
    @Getter
    private Date createTime;

    @Setter
    @Getter
    private Date lastUpdateTime;

    @Setter
    @Getter
    private Integer stat;
}