package com.tianfang.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;

public interface NoticeUsersExMapper {

	int releaseNotice(List<NoticeUsersDto> list);

	NoticeDto getLast(String userId);

	List<NoticeDto> findNoticeViewByPage(@Param("dto")NoticeDto noticeDto,
			@Param("page")PageQuery page);

	int selectAccount(@Param("dto")NoticeDto noticeDto);
}