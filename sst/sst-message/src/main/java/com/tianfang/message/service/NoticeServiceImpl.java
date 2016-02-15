package com.tianfang.message.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.message.dao.NoticeDao;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.pojo.Notice;
import com.tianfang.message.service.INoticeService;


@Service
public class NoticeServiceImpl implements INoticeService {

	@Autowired
	private NoticeDao noticeDao;

	/**
	 * @author YIn
	 * @time:2016年1月13日 上午11:20:18
	 */
	@Override
	public int addNotice(NoticeDto noticeDto) {
		Notice notice = BeanUtils.createBeanByTarget(noticeDto, Notice.class);
		return noticeDao.insertSelective(notice);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月13日 上午11:28:38
	 */
	@Override
	public int updateNotice(NoticeDto noticeDto) {
		Notice notice = BeanUtils.createBeanByTarget(noticeDto, Notice.class);
		return noticeDao.updateByPrimaryKeySelective(notice);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月13日 上午11:51:34
	 */
	@Override
	public int delNotice(NoticeDto noticeDto) {
		Notice notice = BeanUtils.createBeanByTarget(noticeDto, Notice.class);
		return noticeDao.deleteByPrimaryKey(notice.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月13日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	Notice notice = noticeDao.selectByPrimaryKey(id);
	            if (null == notice) {
	                return 0;//无此条记录
	            }
	            notice.setStat(DataStatus.DISABLED);
	            noticeDao.updateByPrimaryKeySelective(notice);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月13日 下午1:17:12
	 */
	@Override
	public List<NoticeDto> findNotice(NoticeDto noticeDto) {
		Notice notice = BeanUtils.createBeanByTarget(noticeDto, Notice.class);
		List<Notice> list = noticeDao.selectByParameter(notice);
		List<NoticeDto> dtoList = BeanUtils.createBeanListByTarget(list, NoticeDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月13日 下午2:18:10
	 */
	@Override
	public PageResult<NoticeDto> findNoticeViewByPage(NoticeDto noticeDto , PageQuery page) {
		Notice notice = BeanUtils.createBeanByTarget(noticeDto, Notice.class);
		List<Notice> list = noticeDao.findNoticeViewByPage(notice,page);
		int total = noticeDao.selectAccount(notice);
		page.setTotal(total);
		List<NoticeDto> dtoList = BeanUtils.createBeanListByTarget(list, NoticeDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(NoticeDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<NoticeDto>(page, dtoList);
	}

}