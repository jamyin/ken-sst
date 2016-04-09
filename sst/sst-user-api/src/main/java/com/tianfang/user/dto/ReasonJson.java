package com.tianfang.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>Description: 申请理由JSON封装格式 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @time 16/4/9 下午2:57
 */
public class ReasonJson implements Serializable {

    @Getter
    @Setter
    private String apply;

    @Getter
    @Setter
    private String refuse;

    @Getter
    @Setter
    private String pass;
}