package com.tianfang.admin.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.admin.mapper.BannerMapper;
import com.tianfang.admin.pojo.Banner;
import com.tianfang.admin.pojo.BannerExample;
import com.tianfang.admin.pojo.BannerExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

@Repository
public class BannerDao extends MyBatisBaseDao<Banner> {

	@Getter
	@Autowired
	private BannerMapper mapper;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月13日 下午1:24:21
	 */
	public List<Banner> selectByParameter(Banner banner) {
		BannerExample example = new BannerExample();
		BannerExample.Criteria criteria = example.createCriteria();
        assemblyParams(banner, criteria);   //组装参数
        List<Banner> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月13日 下午1:27:43
	 */
	private void assemblyParams(Banner banner, Criteria criteria) {
		if (StringUtils.isNotBlank(banner.getId())){
    		criteria.andIdEqualTo(banner.getId());
    	}
    	if (StringUtils.isNotBlank(banner.getTitle())){
    		criteria.andTitleLike("%" +banner.getTitle()+"%");
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询轮播图
	 * @author YIn
	 * @time:2016年1月13日 下午2:43:29
	 */
	public List<Banner> findbannerViewByPage(Banner banner, PageQuery page) {
		BannerExample example = new BannerExample();
		BannerExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(banner, criteria);   //组装参数
        List<Banner> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月13日 下午2:43:59
	 */
	public int selectAccount(Banner banner) {
		BannerExample example = new BannerExample();
		BannerExample.Criteria criteria = example.createCriteria();
        assemblyParams(banner, criteria);   //组装参数
        return mapper.countByExample(example);
	}

	
	
}