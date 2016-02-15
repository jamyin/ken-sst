/**
 * 
 */
package com.tianfang.common.model;

/**		
 * <p>Title: MessageResp </p>
 * <p>Description: 类描述</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author Administrator	
 * @date 2015年9月1日 上午10:27:22	
 * @version 1.0
 * <p>修改人：Administrator</p>
 * <p>修改时间：2015年9月1日 上午10:27:22</p>
 * <p>修改备注：</p>
 */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MessageResp implements Serializable{
	private static final long serialVersionUID = -1537553646979554285L;
	private static final String SUCCESS = "success";
    private static final String MSG = "msg";

    public MessageResp() {
    }

    public static Map<String, Object> getError(Object msg) {
        return getMessage(false, msg);
    }

    public static Map<String, Object> getMessage(boolean success, Object msg) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(SUCCESS, Boolean.valueOf(success));
        result.put(MSG, msg);
        return result;
    }

    public static Map<String, Object> getSuccess(Object msg) {
        return getMessage(true, msg);
    }
}
