package com.tianfang.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * TODO
 *
 * @Author wangxiang
 * @Date 2016/3/22 17:44
 */
public class TeamDataDto implements Serializable{

    @Getter
    @Setter
    private String[] id;

    @Getter
    @Setter
    private String matchId;     // 比赛id

    @Getter
    @Setter
    private String[] teamId;      // 球队id

    @Getter
    @Setter
    private Integer[] goal;       // 进球

    @Getter
    @Setter
    private Integer[] goalOut;    // 失球

    @Getter
    @Setter
    private Integer[] shot;       // 射门

    @Getter
    @Setter
    private Integer[] shotRight;  // 射正

    @Getter
    @Setter
    private Integer[] shotPost;   // 击中门柱

    @Getter
    @Setter
    private Integer[] pass;       // 传球

    @Getter
    @Setter
    private Integer[] passCross;      // 传中

    @Getter
    @Setter
    private Integer[] corner;     // 角球

    @Getter
    @Setter
    private Integer[] freeKick;   // 任意球

    @Getter
    @Setter
    private Integer[] offside;    // 越位

    @Getter
    @Setter
    private Integer[] tackle;     // 抢断

    @Getter
    @Setter
    private Integer[] clearanceKick;  // 解围

    @Getter
    @Setter
    private Integer[] yellow;         // 黄牌

    @Getter
    @Setter
    private Integer[] red;        // 红牌

    @Getter
    @Setter
    private Double[] trapRate;    // 控球率
}
