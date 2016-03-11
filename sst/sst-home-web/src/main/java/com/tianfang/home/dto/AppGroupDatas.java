package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于接收移动端用户创建群主数据封装
 *
 * Created by Administrator on 2016/3/11.
 */
public class AppGroupDatas{
    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private String[] friendIds;
}
