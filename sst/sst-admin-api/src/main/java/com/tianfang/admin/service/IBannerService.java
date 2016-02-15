package com.tianfang.admin.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.admin.dto.BannerDto;
public interface IBannerService {

	/**
	 * 增加轮播图
	 * @author YIn
	 * @time:2016年1月15日 上午10:50:44
	 * @param bannerDto
	 * @return
	 */
	int addBanner(BannerDto bannerDto);

	/**
	 * 编辑轮播图(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:26
	 * @param bannerDto
	 * @return
	 */
	int updateBanner(BannerDto bannerDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:24
	 * @param bannerDto
	 * @return
	 */
	int delBanner(BannerDto bannerDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 查询轮播图-不分页
	 * @author YIn
	 * @time:2016年1月15日 下午1:25:20
	 * @param bannerDto
	 * @return
	 */
	List<BannerDto> findBanner(BannerDto bannerDto);

	/**
	 * 后台轮播图显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:54
	 * @param bannerDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<BannerDto> findBannerViewByPage(BannerDto bannerDto, PageQuery page);

}