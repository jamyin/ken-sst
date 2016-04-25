package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Description: 类描述:分享基本数据封装 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 13:58
 */
public class ShareDto implements Serializable {

    @Getter
    @Setter
    private String id;

    /**
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 缩略图
     */
    @Getter
    @Setter
    private String pic;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String content;

    /**
     * 概述(简介)
     */
    @Getter
    @Setter
    private String summary;

    /**
     * 活动独有数据封装
     */
    @Getter
    @Setter
    private ActiveShareDto active;
}
