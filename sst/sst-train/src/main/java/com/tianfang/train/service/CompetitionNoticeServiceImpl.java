package com.tianfang.train.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.CompetitionNoticeDao;
import com.tianfang.train.dto.CompetitionNoticeDto;
import com.tianfang.train.pojo.CompetitionNotice;


@Service
public class CompetitionNoticeServiceImpl implements ICompetitionNoticeService {

	@Autowired
	private CompetitionNoticeDao competitionNoticeDao;

	/**
	 * @author YIn
	 * @time:2016年1月15日 上午11:20:18
	 */
	@Override
	public int addCompetitionNotice(CompetitionNoticeDto competitionNoticeDto) {
		CompetitionNotice competitionNotice = BeanUtils.createBeanByTarget(competitionNoticeDto, CompetitionNotice.class);
		return competitionNoticeDao.insertSelective(competitionNotice);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:38
	 */
	@Override
	public int updateCompetitionNotice(CompetitionNoticeDto competitionNoticeDto) {
		CompetitionNotice competitionNotice = BeanUtils.createBeanByTarget(competitionNoticeDto, CompetitionNotice.class);
		return competitionNoticeDao.updateByPrimaryKeySelective(competitionNotice);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:34
	 */
	@Override
	public int delCompetitionNotice(CompetitionNoticeDto competitionNoticeDto) {
		CompetitionNotice competitionNotice = BeanUtils.createBeanByTarget(competitionNoticeDto, CompetitionNotice.class);
		return competitionNoticeDao.deleteByPrimaryKey(competitionNotice.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	CompetitionNotice competitionNotice = competitionNoticeDao.selectByPrimaryKey(id);
	            if (null == competitionNotice) {
	                return 0;//无此条记录
	            }
	            competitionNotice.setStat(DataStatus.DISABLED);
	            competitionNoticeDao.updateByPrimaryKeySelective(competitionNotice);
	        }
	        return 1;
	}
	
	@Override
	public CompetitionNoticeDto getNoticeById(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事公告主键ID为空!");
		}
		CompetitionNotice notice = competitionNoticeDao.selectByPrimaryKey(id);
		if (null != notice && notice.getStat() == DataStatus.ENABLED){
			return BeanUtils.createBeanByTarget(notice, CompetitionNoticeDto.class);
		}
		return null;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月15日 下午1:17:12
	 */
	@Override
	public List<CompetitionNoticeDto> findCompetitionNotice(CompetitionNoticeDto competitionNoticeDto) {
		CompetitionNotice competitionNotice = BeanUtils.createBeanByTarget(competitionNoticeDto, CompetitionNotice.class);
		List<CompetitionNotice> list = competitionNoticeDao.selectByParameter(competitionNotice);
		List<CompetitionNoticeDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionNoticeDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午2:18:10
	 */
	@Override
	public PageResult<CompetitionNoticeDto> findCompetitionNoticeViewByPage(CompetitionNoticeDto competitionNoticeDto , PageQuery page) {
		CompetitionNotice competitionNotice = BeanUtils.createBeanByTarget(competitionNoticeDto, CompetitionNotice.class);
		List<CompetitionNotice> list = competitionNoticeDao.findCompetitionNoticeViewByPage(competitionNotice,page);
		int total = competitionNoticeDao.selectAccount(competitionNotice);
		page.setTotal(total);
		List<CompetitionNoticeDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionNoticeDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionNoticeDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<CompetitionNoticeDto>(page, dtoList);
	}

	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:35:13
	 */
	@Override
	public PageResult<CompetitionNoticeDto> findCompNoticeViewByPage(
			CompetitionNoticeDto competitionNoticeDto, PageQuery page) {
		List<CompetitionNoticeDto> list = competitionNoticeDao.findCompNoticeViewByPage(competitionNoticeDto,page);
		int total = competitionNoticeDao.selectCompAccount(competitionNoticeDto);
		page.setTotal(total);
		List<CompetitionNoticeDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionNoticeDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionNoticeDto dto : dtoList){
			if(dto.getCreateTime() != null){
				dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
				dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<CompetitionNoticeDto>(page, dtoList);
	}

}