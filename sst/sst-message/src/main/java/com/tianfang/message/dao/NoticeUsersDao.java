package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;
import com.tianfang.message.mapper.NoticeUsersExMapper;
import com.tianfang.message.mapper.NoticeUsersMapper;
import com.tianfang.message.pojo.NoticeUsers;
import com.tianfang.message.pojo.NoticeUsersExample;
import com.tianfang.message.pojo.NoticeUsersExample.Criteria;

@Repository
public class NoticeUsersDao extends MyBatisBaseDao<NoticeUsers> {

	@Getter
	@Autowired
	private NoticeUsersMapper mapper;
	
	@Getter
	@Autowired
	private NoticeUsersExMapper mappers;

	/**
	 * @author YIn
	 * @time:2016年2月3日 下午5:27:04
	 */
	public int releaseNotice(List<NoticeUsersDto> list) {

		return mappers.releaseNotice(list);
	}

	public NoticeDto getLast(String userId){
		return mappers.getLast(userId);
	}

	/**
	 * @author YIn
	 * @time:2016年3月28日 下午6:42:56
	 */
	public int findMount(String id) {

		return mappers.findMount(id);
	}
	
	/**
	 * @author YIn
	 * @time:2016年3月28日 上午11:09:01
	 */
	public int findRead(String id) {
		/*NoticeUsersExample example = new NoticeUsersExample();
		NoticeUsersExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(id)){
    		criteria.andIdEqualTo(id);
    	}
		criteria.andReadstatEqualTo(DataStatus.ENABLED); //已读
		criteria.andStatEqualTo(DataStatus.ENABLED); 
        int reads = mapper.countByExample(example);
        return reads;*/
		return mappers.findRead(id);
	}

	/**
	 * @author YIn
	 * @time:2016年3月29日 上午9:52:18
	 */
	public List<NoticeUsers> selectByParameter(NoticeUsers noticeUsers) {
		NoticeUsersExample example = new NoticeUsersExample();
		NoticeUsersExample.Criteria criteria = example.createCriteria();
        assemblyParams(noticeUsers, criteria);   //组装参数
        List<NoticeUsers> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * @author YIn
	 * @time:2016年3月29日 上午9:53:42
	 */
	private void assemblyParams(NoticeUsers noticeUsers, Criteria criteria) {
		if (StringUtils.isNotBlank(noticeUsers.getUserId())){
    		criteria.andUserIdEqualTo(noticeUsers.getUserId());
    	}
    	if (StringUtils.isNotBlank(noticeUsers.getNoticeId())){
    		criteria.andNoticeIdEqualTo(noticeUsers.getNoticeId());    //根据状态查询
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
		
	}

}