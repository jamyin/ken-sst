package com.tianfang.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.AlbumDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

/**
 * @author YIn
 * @time:2015年11月14日 上午11:11:42
 * @ClassName: IAlbumService
 * @Description: TODO
 * @
 */
@Service("iAlbumService")
public interface IAlbumService {
	
	/**
	 * 球队相片列表分页显示
	 * @author YIn
	 * @time:2015年11月13日 下午5:53:30
	 */
	PageResult<AlbumDto> findAlbumByPage(AlbumDto albumDto, PageQuery page);
	
	/**
	 * 添加球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int addAlbum(AlbumDto albumDto);

	/**
	 * 修改球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int updateAlbum(AlbumDto albumDto);

	/**
	 * 删除球队相片
	 * @author YIn
	 * @time:2015年11月14日 下午5:11:00
	 * @param albumDto
	 * @return
	 */
	int delAlbum(String id);
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:16:03
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 根据条件查询-不分页
	 * @author YIn
	 * @time:2016年2月29日 下午2:46:22
	 * @param albumDto
	 * @return
	 */
	List<AlbumDto> findAlbum(AlbumDto albumDto);

	List<AlbumDto> findalbumByTop(Integer tOPNUM, Integer enabled);

	AlbumDto getAlbumById(String albumId);
}
