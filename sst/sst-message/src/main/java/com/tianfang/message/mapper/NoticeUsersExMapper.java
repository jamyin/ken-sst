package com.tianfang.message.mapper;

import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;

import java.util.List;

public interface NoticeUsersExMapper {

	int releaseNotice(List<NoticeUsersDto> list);

	NoticeDto getLast(String userId);
}