package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.mapper.AlbumMapper;
import com.tianfang.business.pojo.Album;
import com.tianfang.business.pojo.AlbumExample;
import com.tianfang.business.pojo.AlbumExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

@Repository
public class AlbumDao extends MyBatisBaseDao<Album>{
	
	@Autowired
	@Getter
	private AlbumMapper mapper;

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午2:28:30
	 */
	public List<Album> selectAlbumByPage(Album album, PageQuery page) {
		AlbumExample example = new AlbumExample();
		AlbumExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(album, criteria);   //组装参数
        List<Album> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午2:29:02
	 */
	public long selectAccount(Album album) {
		AlbumExample example = new AlbumExample();
		AlbumExample.Criteria criteria = example.createCriteria();
        assemblyParams(album, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年2月29日 下午2:29:58
	 * @param Album
	 * @param criteria
	 */
	private void assemblyParams(Album album, Criteria criteria) {
		if (StringUtils.isNotBlank(album.getId())){
    		criteria.andIdEqualTo(album.getId());
    	}
		if (StringUtils.isNotBlank(album.getTitle())){
    		criteria.andTitleLike("%" +album.getTitle()+"%");
    	}
		if (StringUtils.isNotBlank(album.getPublisher())){
    		criteria.andPublisherLike("%" +album.getPublisher()+"%");
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午2:46:47
	 */
	public List<Album> selectByParameter(Album album) {
		AlbumExample example = new AlbumExample();
		AlbumExample.Criteria criteria = example.createCriteria();
        assemblyParams(album, criteria);   //组装参数
        List<Album> result = mapper.selectByExample(example);  
        return result;
	}
}
