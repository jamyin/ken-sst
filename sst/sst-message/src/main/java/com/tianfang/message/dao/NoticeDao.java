package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.mapper.NoticeMapper;
import com.tianfang.message.mapper.NoticeUsersExMapper;
import com.tianfang.message.pojo.Notice;
import com.tianfang.message.pojo.NoticeExample;
import com.tianfang.message.pojo.NoticeExample.Criteria;

@Repository
public class NoticeDao extends MyBatisBaseDao<Notice> {

	@Getter
	@Autowired
	private NoticeMapper mapper;
	
	@Getter
	@Autowired
	private NoticeUsersExMapper mappers;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月13日 下午1:24:21
	 */
	public List<Notice> selectByParameter(Notice notice) {
		NoticeExample example = new NoticeExample();
		NoticeExample.Criteria criteria = example.createCriteria();
        assemblyParams(notice, criteria);   //组装参数
        List<Notice> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月13日 下午1:27:43
	 */
	private void assemblyParams(Notice notice, Criteria criteria) {
		if (StringUtils.isNotBlank(notice.getId())){
    		criteria.andIdEqualTo(notice.getId());
    	}
		if (StringUtils.isNotBlank(notice.getTitle())){
    		criteria.andTitleLike("%" +notice.getTitle()+"%");
    	}
    	if (notice.getReadStat() != null){
    		criteria.andReadStatEqualTo(notice.getReadStat());    //根据状态查询
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * @author YIn
	 * @time:2016年3月28日 下午3:51:46
	 */
	public List<NoticeDto> findNoticeViewByPage(NoticeDto noticeDto,
			PageQuery page) {

		return mappers.findNoticeViewByPage(noticeDto, page);
	}

	/**
	 * @author YIn
	 * @time:2016年3月28日 下午3:56:36
	 */
	public int selectAccount(NoticeDto noticeDto) {

		return mappers.selectAccount(noticeDto);
	}

	/**
	 * 分页查询公告
	 * @author YIn
	 * @time:2016年1月13日 下午2:43:29
	 */
/*	public List<Notice> findNoticeViewByPage(Notice notice, PageQuery page) {
		NoticeExample example = new NoticeExample();
		NoticeExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(notice, criteria);   //组装参数
        List<Notice> result = mapper.selectByExample(example);  
        return result;
	}*/

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月13日 下午2:43:59
	 */
/*	public int selectAccount(Notice notice) {
		NoticeExample example = new NoticeExample();
		NoticeExample.Criteria criteria = example.createCriteria();
        assemblyParams(notice, criteria);   //组装参数
        return mapper.countByExample(example);
	}*/

	
	
}