package com.tianfang.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 积分榜数据封装
 *
 * @Author wangxiang
 * @Date 2016/3/21 14:10
 */
public class ScoreboardData implements Serializable {

    @Getter
    @Setter
    private int num;

    @Getter
    @Setter
    private String teamId;

    @Getter
    @Setter
    private String teamName;

    @Getter
    @Setter
    private String teamIcon;

    @Getter
    @Setter
    private int win;

    @Getter
    @Setter
    private int lose;

    @Getter
    @Setter
    private int draw;

    @Getter
    @Setter
    private int score;


    public ScoreboardData(int num, String teamId, String teamName, String teamIcon, int win, int lose, int draw) {
        this.num = num;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamIcon = teamIcon;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.score = win * 3 + draw;
    }
}