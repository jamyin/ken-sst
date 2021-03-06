package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 移动端数据封装
 *
 * @Author wangxiang
 * @Date 2016/4/7 13:31
 */
public class AppUser implements Serializable {

    /**
     * 球员表主键id
     */
    @Getter
    @Setter
    private String id;

    /**
     * 用户id
     */
    @Getter
    @Setter
    private String userId;

    /**
     * 用户头像
     */
    @Getter
    @Setter
    private String pic;

    /**
     * 球队id
     */
    @Getter
    @Setter
    private String teamId;

    /**
     * 性别(1.男  2.女)
     */
    @Getter
    @Setter
    private Integer gender;

    /**
     * 手机号码
     */
    @Getter
    @Setter
    private String mobile;

    /**
     * 用户类型(1，普通会员；2，教练；3，队长)
     */
    @Getter
    @Setter
    private Integer utype;

    /**
     * 创建时间
     */
    @Getter
    @Setter
    private Date createTime;

    /**
     * 中文名
     */
    @Setter
    @Getter
    private String name;

    /**
     * 在团队中的位置，比如左边锋
     */
    @Setter
    @Getter
    private String position;
}
