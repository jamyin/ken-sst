package com.tianfang.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dao.NotificationsDao;
import com.tianfang.user.dto.NotificationsDto;
import com.tianfang.user.pojo.Notifications;

@Service
public class NotificationsServiceImpl implements INotificationsService{
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Override
	public void save(NotificationsDto dto) {
		chechObjIsNullException(dto);
		Notifications obj = BeanUtils.createBeanByTarget(dto, Notifications.class);
		notificationsDao.insertSelective(obj);
	}

	@Override
	public void update(NotificationsDto dto) {
		chechObjIsNullException(dto);
		if (StringUtils.isBlank(dto.getId())){
			throw new RuntimeException("对不起,对象主键id为空");	
		}
		Notifications obj = BeanUtils.createBeanByTarget(dto, Notifications.class);
		notificationsDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public NotificationsDto getNotificationsByUserId(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,用户id为空");
		}
		NotificationsDto dto = new NotificationsDto();
		dto.setOwnerId(userId);
		List<NotificationsDto> datas = notificationsDao.findNotificationsByParam(dto);
		if (null != datas && datas.size() > 0){
			return datas.get(0);
		}
		return null;
	}
	
	private void chechObjIsNullException(NotificationsDto dto) {
		if (null == dto){
			throw new RuntimeException("对不起,对象为空");
		}
	}
}