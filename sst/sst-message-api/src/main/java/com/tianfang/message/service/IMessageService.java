package com.tianfang.message.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.message.dto.MessageDto;
public interface IMessageService {

	/**
	 * 增加消息
	 * @author YIn
	 * @time:2016年1月14日 上午10:50:44
	 * @param messageDto
	 * @return
	 */
	int addMessage(MessageDto messageDto);

	/**
	 * 编辑消息(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:26
	 * @param messageDto
	 * @return
	 */
	int updateMessage(MessageDto messageDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月14日 上午11:51:24
	 * @param messageDto
	 * @return
	 */
	int delMessage(MessageDto messageDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月14日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 查询消息-不分页
	 * @author YIn
	 * @time:2016年1月14日 下午1:25:20
	 * @param messageDto
	 * @return
	 */
	List<MessageDto> findMessage(MessageDto messageDto);

	/**
	 * 后台消息显示页面-分页
	 * @author YIn
	 * @time:2016年1月14日 下午2:17:54
	 * @param messageDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<MessageDto> findMessageViewByPage(MessageDto messageDto, PageQuery page);

}