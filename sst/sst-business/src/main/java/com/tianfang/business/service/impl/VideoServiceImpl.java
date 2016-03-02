package com.tianfang.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.VideoDao;
import com.tianfang.business.dto.VideoDto;
import com.tianfang.business.pojo.Video;
import com.tianfang.business.service.IVideoService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;

@Service
public class VideoServiceImpl implements IVideoService {

	@Autowired
	private VideoDao videoDao;

	@Override
	public PageResult<VideoDto> getCriteriaPage(PageQuery page,
			Map<String, Object> map) {
		List<Video> lis =  videoDao.getCriteriaPage(page,map);
		List<VideoDto> lisDto = BeanUtils.createBeanListByTarget(lis, VideoDto.class);
		for (VideoDto sport : lisDto) {
			if(sport.getCreateTime()!=null){
				sport.setCreateTimeStr(DateUtils.format(sport.getCreateTime(), DateUtils.YMD_DASH));	
			}
			if(sport.getLastUpdateTime()!=null){
				sport.setLastUpdateTimeStr(DateUtils.format(sport.getLastUpdateTime(), DateUtils.YMD_DASH));	
			}
			if(!StringUtils.isEmpty(sport.getVideo())){
				sport.setVideo("vcastr_file="+sport.getVideo().replace("\\", "/"));	
			}
			
		}
		if(page!=null){
			long total = videoDao.getCount(map);
			page.setTotal(total);
		}
		return new PageResult<VideoDto>(page,lisDto);
	}

	@Override
	public long insertVideo(VideoDto videoDto) {
		Video Video = BeanUtils.createBeanByTarget(videoDto, Video.class);
		long stat = videoDao.insertVideo(Video);
		return stat;
	}

	@Override
	public long editVideo(VideoDto videoDto) {
		Video Video = BeanUtils.createBeanByTarget(videoDto, Video.class);
		long stat = videoDao.editVideo(Video);
		return stat;
	}

	@Override
	public long delVideo(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			long stat = videoDao.delVideo(str);
			if(stat ==0 ){
				return 0;
			}
		}
		return 1;
	}

	@Override
	public VideoDto selectById(Map<String, Object> map) {
		List<Video> sport = videoDao.getCriteriaPage(null, map);
		return sport.size()<1 ? null :(VideoDto) BeanUtils.createBeanListByTarget(sport, VideoDto.class).get(0);
	}

	@Override
	public void updateClickCount(String videoId) {
		videoDao.updateClickCount(videoId);
	}

	@Override
	public List<VideoDto> findVideoByTop(int i,Integer videoStatus) {
		List<Video> dataList = videoDao.findVideoByTop(i,videoStatus);
		List<VideoDto> objList = BeanUtils.createBeanListByTarget(dataList, VideoDto.class);
		return objList;
	}
}
