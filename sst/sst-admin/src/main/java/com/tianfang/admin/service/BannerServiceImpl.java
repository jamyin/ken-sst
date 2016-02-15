package com.tianfang.admin.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.admin.dao.BannerDao;
import com.tianfang.admin.dto.BannerDto;
import com.tianfang.admin.pojo.Banner;
import com.tianfang.admin.service.IBannerService;


@Service
public class BannerServiceImpl implements IBannerService {

	@Autowired
	private BannerDao bannerDao;

	/**
	 * @author YIn
	 * @time:2016年1月15日 上午11:20:18
	 */
	@Override
	public int addBanner(BannerDto bannerDto) {
		Banner banner = BeanUtils.createBeanByTarget(bannerDto, Banner.class);
		return bannerDao.insertSelective(banner);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:38
	 */
	@Override
	public int updateBanner(BannerDto bannerDto) {
		Banner banner = BeanUtils.createBeanByTarget(bannerDto, Banner.class);
		return bannerDao.updateByPrimaryKeySelective(banner);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:34
	 */
	@Override
	public int delBanner(BannerDto bannerDto) {
		Banner banner = BeanUtils.createBeanByTarget(bannerDto, Banner.class);
		return bannerDao.deleteByPrimaryKey(banner.getId());
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
	        	Banner banner = bannerDao.selectByPrimaryKey(id);
	            if (null == banner) {
	                return 0;//无此条记录
	            }
	            banner.setStat(DataStatus.DISABLED);
	            bannerDao.updateByPrimaryKeySelective(banner);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月15日 下午1:17:12
	 */
	@Override
	public List<BannerDto> findBanner(BannerDto bannerDto) {
		Banner banner = BeanUtils.createBeanByTarget(bannerDto, Banner.class);
		List<Banner> list = bannerDao.selectByParameter(banner);
		List<BannerDto> dtoList = BeanUtils.createBeanListByTarget(list, BannerDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午2:18:10
	 */
	@Override
	public PageResult<BannerDto> findBannerViewByPage(BannerDto bannerDto , PageQuery page) {
		Banner banner = BeanUtils.createBeanByTarget(bannerDto, Banner.class);
		List<Banner> list = bannerDao.findbannerViewByPage(banner,page);
		int total = bannerDao.selectAccount(banner);
		page.setTotal(total);
		List<BannerDto> dtoList = BeanUtils.createBeanListByTarget(list, BannerDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(BannerDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<BannerDto>(page, dtoList);
	}

}