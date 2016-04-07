package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: ${TODO} </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @time 16/4/6 下午5:59
 */
public class AppTeamPlayer implements Serializable{

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private List<AppUser> list;

    public AppTeamPlayer() {
    }

    public AppTeamPlayer(String title, List<AppUser> list) {
        this.title = title;
        this.list = list;
    }
}
