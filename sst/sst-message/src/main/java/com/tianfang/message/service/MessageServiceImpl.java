package com.tianfang.message.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.message.dao.MessageDao;
import com.tianfang.message.dto.MessageDto;
import com.tianfang.message.pojo.Message;
import com.tianfang.message.service.IMessageService;


@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private MessageDao messageDao;

	/**
	 * @author YIn
	 * @time:2016年1月14日 上午11:20:18
	 */
	@Override
	public int addMessage(MessageDto messageDto) {
		Message message = BeanUtils.createBeanByTarget(messageDto, Message.class);
		return messageDao.insertSelective(message);
	}
	
	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:38
	 */
	@Override
	public int updateMessage(MessageDto messageDto) {
		Message message = BeanUtils.createBeanByTarget(messageDto, Message.class);
		return messageDao.updateByPrimaryKeySelective(message);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月14日 上午11:51:34
	 */
	@Override
	public int delMessage(MessageDto messageDto) {
		Message message = BeanUtils.createBeanByTarget(messageDto, Message.class);
		return messageDao.deleteByPrimaryKey(message.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月14日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	Message message = messageDao.selectByPrimaryKey(id);
	            if (null == message) {
	                return 0;//无此条记录
	            }
	            message.setStat(DataStatus.DISABLED);
	            messageDao.updateByPrimaryKeySelective(message);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月14日 下午1:17:12
	 */
	@Override
	public List<MessageDto> findMessage(MessageDto messageDto) {
		Message message = BeanUtils.createBeanByTarget(messageDto, Message.class);
		List<Message> list = messageDao.selectByParameter(message);
		List<MessageDto> dtoList = BeanUtils.createBeanListByTarget(list, MessageDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月14日 下午2:18:10
	 */
	@Override
	public PageResult<MessageDto> findMessageViewByPage(MessageDto messageDto , PageQuery page) {
		Message message = BeanUtils.createBeanByTarget(messageDto, Message.class);
		List<Message> list = messageDao.findmessageViewByPage(message,page);
		int total = messageDao.selectAccount(message);
		page.setTotal(total);
		List<MessageDto> dtoList = BeanUtils.createBeanListByTarget(list, MessageDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(MessageDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<MessageDto>(page, dtoList);
	}

}