package com.tianfang.train.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 比赛重要时刻数据
 *
 * @Author wangxiang
 * @Date 2016/3/22 13:24
 */
public class MatchPlayerHotDatasTempDto implements Serializable{

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

    @Getter
    @Setter
    private Integer type;       // 类型(0-上场,1-下场,2-进球,3-红牌,4-黄牌)

    @Getter
    @Setter
    private String style;       // 类型对应样式

    @Getter
    @Setter
    private Integer minute;     // 分钟

    /**
     * 用于上超官网.样式枚举
     */
    public enum HotStyleEnum{
        UP(0,"up"),DOWN(1,"down"),BALL(2,"ball"),RED(3,"red"),YELLOW(4,"yellow");
        private int status;
        private String style;

        public int getStatus() {
            return status;
        }

        public String getStyle() {
            return style;
        }

        public static String getStyle(int status) {
            for (HotStyleEnum c : HotStyleEnum.values()) {
                if (c.getStatus() == status) {
                    return c.getStyle();
                }
            }
            return null;
        }

        HotStyleEnum(int status, String style) {
            this.status = status;
            this.style = style;
        }
    }
}