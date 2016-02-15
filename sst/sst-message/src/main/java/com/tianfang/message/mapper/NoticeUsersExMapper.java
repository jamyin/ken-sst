package com.tianfang.message.mapper;

import java.util.List;

import com.tianfang.message.dto.NoticeUsersDto;

public interface NoticeUsersExMapper {

	int releaseNotice(List<NoticeUsersDto> list);
}