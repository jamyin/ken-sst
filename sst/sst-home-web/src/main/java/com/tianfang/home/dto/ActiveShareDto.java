package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Description: 类描述:活动分享数据封装 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 14:04
 */
public class ActiveShareDto implements Serializable {

    /**
     * 活动地点
     */
    @Getter
    @Setter
    private String address;

    /**
     * 活动日期(yyyy-mm-dd)
     */
    @Getter
    @Setter
    private String activityDate;

    /**
     * 活动开始时间(hh:mm)
     */
    @Getter
    @Setter
    private String startTime;

    /**
     * 活动结束时间(hh:mm)
     */
    @Getter
    @Setter
    private String endTime;
}
