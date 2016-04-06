package com.tianfang.home.dto;

import com.tianfang.user.dto.UserDto;
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
    private List<UserDto> list;

    public AppTeamPlayer() {
    }

    public AppTeamPlayer(String title, List<UserDto> list) {
        this.title = title;
        this.list = list;
    }
}
