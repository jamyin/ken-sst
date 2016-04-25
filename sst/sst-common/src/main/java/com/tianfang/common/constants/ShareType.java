package com.tianfang.common.constants;

/**
 * <p>Description: 类描述:分享的类型枚举定义 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 13:50
 */
public enum ShareType {

    NEWS(1),ACTIVE(2);// 1-新闻,2-活动

    ShareType(int index){
        this.index = index;
    }
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}