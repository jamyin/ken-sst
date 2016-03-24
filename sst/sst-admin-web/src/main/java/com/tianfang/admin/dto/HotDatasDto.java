package com.tianfang.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用于接收后台页面时间轴数据
 *
 * @Author wangxiang
 * @Date 2016/3/23 16:45
 */
public class HotDatasDto implements Serializable {

    @Getter
    @Setter
    private String matchId;

    @Getter
    @Setter
    private String teamId;

    @Getter
    @Setter
    private String[] id;

    @Getter
    @Setter
    private String[] playerId;

    @Getter
    @Setter
    private Integer[] type;

    @Getter
    @Setter
    private Integer[] minute;
}