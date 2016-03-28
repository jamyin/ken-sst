package com.tianfang.message.service;

import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;

import java.util.List;
public interface INoticeUsersService {

	/**
	 * 发布公告
	 * @author YIn
	 * @time:2016年2月3日 下午5:10:59
	 * @param noticeUsersDto
	 * @return
	 */
	int addNotice(NoticeUsersDto noticeUsersDto);

	int releaseNotice(List<NoticeUsersDto> list);

	/**
	 * 根据用户id获取最新一条公告信息
	 * @param userId
	 * @return
     */
	NoticeDto getLast(String userId);

	//发送人数
	int findMount(String id);
	
	//已读
	int findRead(String id);
}