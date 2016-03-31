package com.tianfang.train.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 比赛球员基本数据
 *
 * @Author wangxiang
 * @Date 2016/3/22 13:24
 */
public class MatchPlayerBaseDatasTempDto implements Serializable{

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String matchId;     // 比赛id

    @Getter
    @Setter
    private String teamId;      // 球队id

    @Getter
    @Setter
    private String playerId;    // 球员id

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

    @Getter
    @Setter
    private String playerName;  // 球员名称

    @Getter
    @Setter
    private Integer num;        // 球员号码

    @Getter
    @Setter
    private Integer position;   // 球员位置

    @Getter
    @Setter
    private String positionStr; // 球员位置中文描述

}