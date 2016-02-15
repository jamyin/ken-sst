package com.tianfang.train.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dao.CompetitionDao;
import com.tianfang.train.dao.CompetitionNewsDao;
import com.tianfang.train.dto.CompetitionNewsDto;
import com.tianfang.train.pojo.Competition;
import com.tianfang.train.pojo.CompetitionNews;


@Service
public class CompetitionNewsServiceImpl implements ICompetitionNewsService {

	@Autowired
	private CompetitionNewsDao competitionNewsDao;
	@Autowired
	private CompetitionDao compDao;

	/**
	 * @author YIn
	 * @time:2016年1月15日 上午11:20:18
	 */
	@Override
	public int addCompetitionNews(CompetitionNewsDto competitionNewsDto) {
		CompetitionNews competitionNews = BeanUtils.createBeanByTarget(competitionNewsDto, CompetitionNews.class);
		setCompName(competitionNewsDto, competitionNews);
		return competitionNewsDao.insertSelective(competitionNews);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:38
	 */
	@Override
	public int updateCompetitionNews(CompetitionNewsDto competitionNewsDto) {
		CompetitionNews competitionNews = BeanUtils.createBeanByTarget(competitionNewsDto, CompetitionNews.class);
		setCompName(competitionNewsDto, competitionNews);
		return competitionNewsDao.updateByPrimaryKeySelective(competitionNews);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:34
	 */
	@Override
	public int delCompetitionNews(CompetitionNewsDto competitionNewsDto) {
		CompetitionNews competitionNews = BeanUtils.createBeanByTarget(competitionNewsDto, CompetitionNews.class);
		return competitionNewsDao.deleteByPrimaryKey(competitionNews.getId());
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
	        	CompetitionNews competitionNews = competitionNewsDao.selectByPrimaryKey(id);
	            if (null == competitionNews) {
	                return 0;//无此条记录
	            }
	            competitionNews.setStat(DataStatus.DISABLED);
	            competitionNewsDao.updateByPrimaryKeySelective(competitionNews);
	        }
	        return 1;
	}
	
	@Override
	public CompetitionNewsDto getCompetitionNews(String id) {
		CompetitionNews news = competitionNewsDao.selectByPrimaryKey(id);
		if (null != news && news.getStat() == DataStatus.ENABLED){
			return BeanUtils.createBeanByTarget(news, CompetitionNewsDto.class);
		}
		return null;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月15日 下午1:17:12
	 */
	@Override
	public List<CompetitionNewsDto> findCompetitionNews(CompetitionNewsDto dto) {
		List<CompetitionNews> list = competitionNewsDao.findCompetitionNewsByParam(dto);

		return PojoToDto(list);
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午2:18:10
	 */
	@Override
	public PageResult<CompetitionNewsDto> findCompetitionNewsByPage(CompetitionNewsDto dto , PageQuery query) {
		int total = competitionNewsDao.countCompetitionNewsByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<CompetitionNews> results = competitionNewsDao.findCompetitionNewsByParam(dto, query);
			return new PageResult<CompetitionNewsDto>(query, PojoToDto(results));
		}
		return null;
	}
	
	private List<CompetitionNewsDto> PojoToDto(List<CompetitionNews> list) {
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, CompetitionNewsDto.class);
		}
		return null;
	}
	
	/**
	 * 保存更新操作时,添加compName(赛事名称)
	 * @param competitionNewsDto
	 * @param competitionNews
	 * @author xiang_wang
	 * 2016年2月3日上午10:44:24
	 */
	private void setCompName(CompetitionNewsDto competitionNewsDto,
			CompetitionNews competitionNews) {
		if (StringUtils.isNotBlank(competitionNewsDto.getCompId())){
			Competition comp = compDao.selectByPrimaryKey(competitionNewsDto.getCompId());
			if (null != comp){
				competitionNews.setCompName(comp.getTitle());
			}
		}
	}
}