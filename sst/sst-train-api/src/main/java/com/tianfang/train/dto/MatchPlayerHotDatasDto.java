package com.tianfang.train.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 比赛重要时刻数据
 *
 * @Author wangxiang
 * @Date 2016/3/22 13:24
 */
public class MatchPlayerHotDatasDto implements Serializable{

    /**
     * 比赛球员基本数据类型枚举
     */
    public enum Type{
        UP(0, "上场"),DOWN(1, "下场"),GOAL(2, "进球"),RED(3, "红牌"),YELLOW(4, "黄牌");
        private int status;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        Type(int status, String value) {
            this.status = status;
            this.value = value;
        }
    }

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
    private Integer type;       // 类型(0-上场,1-下场,2-进球,3-红牌,4-黄牌)

    @Getter
    @Setter
    private Integer minute;     // 分钟

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;
}