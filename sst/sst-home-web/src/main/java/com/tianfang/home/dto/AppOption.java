package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 移动端选项对象
 *
 * @Author wangxiang
 * @Date 2016/3/24 13:47
 */
public class AppOption implements Serializable{

    /**
     * 选项内容
     */
    @Setter
    @Getter
    private String optionText;

    /**
     * 接收base64位图片数据
     */
    @Setter
    @Getter
    private String base64Img;
}
