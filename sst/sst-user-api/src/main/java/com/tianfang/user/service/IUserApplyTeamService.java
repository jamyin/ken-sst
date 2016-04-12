package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.app.AppUserInfo;
import com.tianfang.user.dto.UserApplyTeamDto;

public interface IUserApplyTeamService {

	String save(UserApplyTeamDto dto);
	
	void del(String id);
	
	void update(UserApplyTeamDto dto);
	
	UserApplyTeamDto getUserApplyTeamById(String id);
	
	List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto);
	
	PageResult<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto, PageQuery query);
	
	/**
	 * 自定义sql查询(联表查询)
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午10:19:33
	 */
	PageResult<UserApplyTeamDto> findUserApplyTeamExByParam(UserApplyTeamDto dto, PageQuery query);

	/**		
	 * <p>Description: 根据申请表主键id,查询用户申请详细信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param id
	 * @return AppUserInfo
	 * @author wangxiang	
	 * @date 16/4/9 下午3:09
	 * @version 1.0
	 */
	AppUserInfo getUserApplyInfoById(String id);

	/**		
	 * <p>Description: 分页查询用户申请 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param param
	 * @param query
	 * @return PageResult<AppUserInfo>
	 * @author wangxiang	
	 * @date 16/4/9 下午3:47
	 * @version 1.0
	 */
	PageResult<AppUserInfo> queryUserApplyInfoByParam(AppUserInfo param, PageQuery query);
	
	
	PageResult<AppUserInfo> queryUserTeamApplyInfoByParam(UserApplyTeamDto param, PageQuery query);

	/**		
	 * <p>Description: 查询用户申请 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param param
	 * @return List<AppUserInfo>
	 * @author wangxiang	
	 * @date 16/4/9 下午3:47
	 * @version 1.0
	 */
	List<AppUserInfo> queryUserApplyInfoByParam(AppUserInfo param);
}