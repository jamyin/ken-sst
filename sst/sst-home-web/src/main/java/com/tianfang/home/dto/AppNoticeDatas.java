package com.tianfang.home.dto;

import lombok.Getter;
import lombok.Setter;

import com.tianfang.message.dto.NoticeDto;

/**
 * @author YIn
 * @time:2016年3月24日 下午3:37:17
 * @ClassName: AppNoticeDatas
 * @Description: 用于接收移动端发布公告数据
 * @
 */
public class AppNoticeDatas{
	
    @Getter
    @Setter
    private NoticeDto noticeDto;

    @Getter
    @Setter
    private String[] userIds;
    
}
