package com.tianfang.message.service;

import java.util.List;

import com.tianfang.message.dto.NoticeUsersDto;
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

}