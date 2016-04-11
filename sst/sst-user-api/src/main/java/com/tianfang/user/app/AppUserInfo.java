package com.tianfang.user.app;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: 移动端用户申请球队展示信息 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @time 16/4/9 下午2:34
 */
public class AppUserInfo implements Serializable {

    @Getter
    @Setter
    private String id;          // 申请记录id

    @Getter
    @Setter
    private String userId;      // 用户id

    @Getter
    @Setter
    private String nickName;    // 用户昵称

    @Getter
    @Setter
    private String name;        // 用户真实姓名

    @Getter
    @Setter
    private Integer age;        // 年龄

    @Getter
    @Setter
    private String mobile;      // 参赛信息中手机号码

    @Getter
    @Setter
    private String cardNo;      // 身份证号

    @Getter
    @Setter
    private String studentNo;   // 学籍号码

    @Getter
    @Setter
    private String school;      // 学校

    @Getter
    @Setter
    private String teamId;      // 球队id

    @Getter
    @Setter
    private String teamName;    // 球队名称

    @Getter
    @Setter
    private String reason;      // 申请理由(JSON组装)

    @Getter
    @Setter
    private String photo;       // 参赛信息照片

    @Getter
    @Setter
    private Integer utype;      // 用户类型(1-普通用户,2-教练,3-队长)

    @Getter
    @Setter
    private String pic;         // 用户头像

    @Getter
    @Setter
    private Integer status;      // 申请状态(0-未审核,1-通过,2-拒绝)

    @Getter
    @Setter
    private Integer gender;      // 性别(1-男,2-女)

    @Getter
    @Setter
    private Date createTime;    // 申请时间
}