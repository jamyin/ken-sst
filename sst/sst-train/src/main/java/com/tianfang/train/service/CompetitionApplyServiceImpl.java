package com.tianfang.train.service;

import java.text.SimpleDateFormat;
import java.util.List;

import com.tianfang.train.dao.CompetitionDao;
import com.tianfang.train.pojo.Competition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.CompetitionApplyDao;
import com.tianfang.train.dao.CompetitionTeamDao;
import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.enums.AuditType;
import com.tianfang.train.pojo.CompetitionApply;
import com.tianfang.train.pojo.CompetitionTeam;
import com.tianfang.train.service.ICompetitionApplyService;


@Service
public class CompetitionApplyServiceImpl implements ICompetitionApplyService {

	@Autowired
	private CompetitionApplyDao competitionApplyDao;
	@Autowired
	private CompetitionTeamDao compTeamDao;
	@Autowired
	private CompetitionDao compDao;

	/**
	 * @author YIn
	 * @time:2016年1月20日 上午11:20:18
	 */
	@Override
	public int addCompetitionApply(CompetitionApplyDto competitionApplyDto) {
		CompetitionApply competitionApply = BeanUtils.createBeanByTarget(competitionApplyDto, CompetitionApply.class);
		if (StringUtils.isBlank(competitionApply.getCompName()) && StringUtils.isNotBlank(competitionApply.getCompId())){
			Competition competition = compDao.selectByPrimaryKey(competitionApply.getId());
			competitionApply.setCompName(competition.getTitle());
		}
		return competitionApplyDao.insertSelective(competitionApply);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月20日 上午11:28:38
	 */
	@Override
	public int updateCompetitionApply(CompetitionApplyDto competitionApplyDto) {
		CompetitionApply competitionApply = BeanUtils.createBeanByTarget(competitionApplyDto, CompetitionApply.class);
		return competitionApplyDao.updateByPrimaryKeySelective(competitionApply);
	}

	@Override
	public int auditCompetitionApply(String id, Integer status) {
		if (StringUtils.isBlank(id) || null == status || StringUtils.isBlank(AuditType.getByIndexValue(status))){
			return 0;
		}
		CompetitionApply ca = competitionApplyDao.selectByPrimaryKey(id);
		if (null == ca || ca.getStat() == DataStatus.DISABLED || ca.getAuditType() != AuditType.UNAUDIT.getIndex()){
			return 0;
		}
		CompetitionApply apply = new CompetitionApply();
		apply.setId(id);
		apply.setAuditType(status);
		int count = competitionApplyDao.updateByPrimaryKeySelective(apply);
		if (count > 0){
			if (AuditType.PASS.getIndex() == status.intValue()){
				CompetitionTeam ct = new CompetitionTeam();
				ct.setCompId(ca.getCompId());
				ct.setTeamId(ca.getTeamId());
				compTeamDao.insertSelective(ct);
			}
		}
		return count;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月20日 上午11:51:34
	 */
	@Override
	public int delCompetitionApply(CompetitionApplyDto competitionApplyDto) {
		CompetitionApply competitionApply = BeanUtils.createBeanByTarget(competitionApplyDto, CompetitionApply.class);
		return competitionApplyDao.deleteByPrimaryKey(competitionApply.getId());
	}
	
	@Override
	public CompetitionApplyDto getCompetitionApplyById(String id) {
		checkIdIsNull(id);
		CompetitionApplyDto dto = BeanUtils.createBeanByTarget(competitionApplyDao.selectByPrimaryKey(id), CompetitionApplyDto.class);
		return dto;
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事报名对象主键id为空!");
		}
	}

	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月20日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	CompetitionApply competitionApply = competitionApplyDao.selectByPrimaryKey(id);
	            if (null == competitionApply) {
	                return 0;//无此条记录
	            }
	            competitionApply.setStat(DataStatus.DISABLED);
	            competitionApplyDao.updateByPrimaryKeySelective(competitionApply);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月20日 下午1:17:12
	 */
	@Override
	public List<CompetitionApplyDto> findCompetitionApply(CompetitionApplyDto competitionApplyDto) {
		CompetitionApply competitionApply = BeanUtils.createBeanByTarget(competitionApplyDto, CompetitionApply.class);
		List<CompetitionApply> list = competitionApplyDao.selectByParameter(competitionApply);
		List<CompetitionApplyDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionApplyDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月20日 下午2:18:10
	 */
	@Override
	public PageResult<CompetitionApplyDto> findCompetitionApplyViewByPage(CompetitionApplyDto competitionApplyDto , PageQuery page) {
		CompetitionApply competitionApply = BeanUtils.createBeanByTarget(competitionApplyDto, CompetitionApply.class);
		List<CompetitionApply> list = competitionApplyDao.findCompetitionApplyViewByPage(competitionApply,page);
		int total = competitionApplyDao.selectAccount(competitionApply);
		page.setTotal(total);
		List<CompetitionApplyDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionApplyDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionApplyDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<CompetitionApplyDto>(page, dtoList);
	}
	/**
	 * @author YIn
	 * @time:2016年1月23日 上午10:13:39
	 */
	@Override
	public PageResult<CompetitionApplyDto> findCompApplyViewByPage(CompetitionApplyDto competitionApplyDto, PageQuery page) {
		List<CompetitionApplyDto> list = competitionApplyDao.findCompApplyViewByPage(competitionApplyDto,page);
		int total = competitionApplyDao.selectCompAccount(competitionApplyDto);
		page.setTotal(total);
		List<CompetitionApplyDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionApplyDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionApplyDto dto : dtoList){
			if(dto.getCreateTime() != null){
				dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
				dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<CompetitionApplyDto>(page, dtoList);
	}

}