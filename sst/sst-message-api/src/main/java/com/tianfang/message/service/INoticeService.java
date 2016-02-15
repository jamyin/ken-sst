package com.tianfang.message.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.message.dto.NoticeDto;
public interface INoticeService {

	/**
	 * 增加公告
	 * @author YIn
	 * @time:2016年1月13日 上午10:50:44
	 * @param noticeDto
	 * @return
	 */
	int addNotice(NoticeDto noticeDto);

	/**
	 * 编辑公告(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:26
	 * @param noticeDto
	 * @return
	 */
	int updateNotice(NoticeDto noticeDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月13日 上午11:51:24
	 * @param noticeDto
	 * @return
	 */
	int delNotice(NoticeDto noticeDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月13日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 查询公告-不分页
	 * @author YIn
	 * @time:2016年1月13日 下午1:25:20
	 * @param noticeDto
	 * @return
	 */
	List<NoticeDto> findNotice(NoticeDto noticeDto);

	/**
	 * 后台公告显示页面-分页
	 * @author YIn
	 * @time:2016年1月13日 下午2:17:54
	 * @param noticeDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<NoticeDto> findNoticeViewByPage(NoticeDto noticeDto, PageQuery page);

}