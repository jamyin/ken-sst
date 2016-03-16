package com.tianfang.user.service;

import com.tianfang.user.dto.NotificationsDto;

/**		
 * <p>Title: INotificationsService </p>
 * <p>Description: 类描述:获取用户安全提醒方式</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月16日上午11:08:55
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface INotificationsService {
	
	void save(NotificationsDto dto);
	
	void update(NotificationsDto dto);
	
	NotificationsDto getNotificationsByUserId(String userId);
}