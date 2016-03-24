package com.tianfang.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户后台页面展示
 *
 * @Author wangxiang
 * @Date 2016/3/23 13:55
 */
public class PlayerDto implements Serializable{

    @Setter
    @Getter
    private String playerId;

    @Getter
    @Setter
    private String name;

    @Setter
    @Getter
    private Integer num;

    @Getter
    @Setter
    private Integer disabled = 0;  // 是否被禁用

    @Setter
    @Getter
    private String position;     // 根据枚举,转为文字

    @Getter
    @Setter
    private String id;          // 数据id

    @Getter
    @Setter
    private Integer minute;     // 上场比赛时长

    @Getter
    @Setter
    private Integer goal;       // 进球

    @Getter
    @Setter
    private Integer assist;     // 助攻

    @Getter
    @Setter
    private Integer shot;       // 射门

    @Getter
    @Setter
    private Integer shotRight;  // 射正

    @Getter
    @Setter
    private Integer pass;       // 传球

    @Getter
    @Setter
    private Integer foul;       // 犯规

    @Getter
    @Setter
    private Integer tackle;     // 抢球

    @Getter
    @Setter
    private Integer clearanceKick;  // 解围

    @Getter
    @Setter
    private Integer save;       // 扑救

    @Getter
    @Setter
    private Integer yellow;     // 黄牌

    @Getter
    @Setter
    private Integer red;        // 红牌

    @Getter
    @Setter
    private Integer reserve;    // 是否替补(0-否,1-是)
}