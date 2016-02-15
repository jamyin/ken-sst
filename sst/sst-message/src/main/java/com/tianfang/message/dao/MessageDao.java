package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.mapper.MessageMapper;
import com.tianfang.message.pojo.Message;
import com.tianfang.message.pojo.MessageExample;
import com.tianfang.message.pojo.MessageExample.Criteria;

@Repository
public class MessageDao extends MyBatisBaseDao<Message> {

	@Getter
	@Autowired
	private MessageMapper mapper;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月14日 下午1:24:21
	 */
	public List<Message> selectByParameter(Message message) {
		MessageExample example = new MessageExample();
		MessageExample.Criteria criteria = example.createCriteria();
        assemblyParams(message, criteria);   //组装参数
        List<Message> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月14日 下午1:27:43
	 */
	private void assemblyParams(Message message, Criteria criteria) {
		if (StringUtils.isNotBlank(message.getId())){
    		criteria.andIdEqualTo(message.getId());
    	}
    	if (StringUtils.isNotBlank(message.getContent())){
    		criteria.andContentLike("%" +message.getContent()+"%");
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询公告
	 * @author YIn
	 * @time:2016年1月14日 下午2:43:29
	 */
	public List<Message> findmessageViewByPage(Message message, PageQuery page) {
		MessageExample example = new MessageExample();
		MessageExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(message, criteria);   //组装参数
        List<Message> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月14日 下午2:43:59
	 */
	public int selectAccount(Message message) {
		MessageExample example = new MessageExample();
		MessageExample.Criteria criteria = example.createCriteria();
        assemblyParams(message, criteria);   //组装参数
        return mapper.countByExample(example);
	}

	
	
}