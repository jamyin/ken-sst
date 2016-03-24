package com.tianfang.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 封装比赛球员页面接收数据
 *
 * @Author wangxiang
 * @Date 2016/3/23 11:17
 */
public class PlayerDataDto implements Serializable {

    @Getter
    @Setter
    private String matchId;     // 比赛id

    @Getter
    @Setter
    private String teamId;      // 球队id

    @Getter
    @Setter
    private String[] id;

    @Getter
    @Setter
    private String[] playerId;    // 球员id

    @Getter
    @Setter
    private Integer[] minute;     // 上场比赛时长

    @Getter
    @Setter
    private Integer[] goal;       // 进球

    @Getter
    @Setter
    private Integer[] assist;     // 助攻

    @Getter
    @Setter
    private Integer[] shot;       // 射门

    @Getter
    @Setter
    private Integer[] shotRight;  // 射正

    @Getter
    @Setter
    private Integer[] pass;       // 传球

    @Getter
    @Setter
    private Integer[] foul;       // 犯规

    @Getter
    @Setter
    private Integer[] tackle;     // 抢球

    @Getter
    @Setter
    private Integer[] clearanceKick;  // 解围

    @Getter
    @Setter
    private Integer[] save;       // 扑救

    @Getter
    @Setter
    private Integer[] yellow;     // 黄牌

    @Getter
    @Setter
    private Integer[] red;        // 红牌

    @Getter
    @Setter
    private Integer[] reserve;    // 是否替补(0-否,1-是)
}
