package com.tianfang.home.controller;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.enums.AuditType;
import com.tianfang.user.enums.UserType;
import com.tianfang.user.service.IUserApplyTeamService;
import com.tianfang.user.service.IUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**		
 * <p>Title: TeamController </p>
 * <p>Description: 类描述:与球队操作相关接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月4日下午3:36:12
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "team")
public class TeamController extends BaseController{
	protected static final Log logger = LogFactory.getLog(TeamController.class);
	
	@Autowired
	private ITeamService teamService;
	@Autowired
	private IUserApplyTeamService userApplyTeamService;
	@Autowired
	private IUserService userService;

	/**
	 * 用户申请加入球队接口
	 * @param userId
	 * @param teamId
	 * @param reason 申请理由
	 * @return
	 * @author xiang_wang
	 * 2016年3月4日下午5:30:26
	 */
	@RequestMapping(value="apply")
    @ResponseBody
    public Response<String> apply(String userId, String teamId, String reason) {
		Response<String> result = new Response<String>();
		if (checkUserApplyTeam(result, userId, teamId)){
			UserApplyTeamDto dto = new UserApplyTeamDto();
			dto.setTeamId(teamId);
			dto.setUserId(userId);
			dto.setReason(reason);
			dto.setStatus(AuditType.UNAUDIT.getIndex());
			userApplyTeamService.save(dto);

			result.setStatus(DataStatus.HTTP_SUCCESS);
			result.setMessage("申请成功,请耐心等待队长审核...");
		}
		return result;
    }
	
	/**
	 * 查询所有球队
	 * @return
	 * @author xiang_wang
	 * 2016年3月4日下午5:34:44
	 */
	@RequestMapping(value="all")
    @ResponseBody
    public Response<List<TeamDto>> all() {
		Response<List<TeamDto>> result = new Response<List<TeamDto>>();
		List<TeamDto> teams = teamService.findAll();
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(teams);
		return result;
    }
	
	/**
	 * 队长查看用户球队申请记录接口
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午9:59:27
	 */
	@RequestMapping(value="listApply")
    @ResponseBody
	public Response<PageResult<UserApplyTeamDto>> listApply(String userId, PageQuery query){
		Response<PageResult<UserApplyTeamDto>> result = new Response<PageResult<UserApplyTeamDto>>();
		TeamDto team = isOwnerTeam(userId);
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您没有权限查看!");
			return result;
		}
		UserApplyTeamDto dto = new UserApplyTeamDto();
		dto.setTeamId(team.getId());
		PageResult<UserApplyTeamDto> datas = userApplyTeamService.findUserApplyTeamExByParam(dto, query);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(datas);
		
		return result;
	}
	
	/**
	 * 判断是否是球队的队长
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午11:31:17
	 */
	@RequestMapping(value= "isOwner")
	@ResponseBody
	public Response<String> isOwner(String userId){
		Response<String> result = new Response<String>();
		TeamDto team = isOwnerTeam(userId);
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您不是球队的队长");
			return result;
		}
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("欢迎队长回家");
		return result;
	}
	
	/**
	 * 审核球队用户申请
	 * @param userId
	 * @param id
	 * @param status
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午11:06:45
	 */
	@RequestMapping(value="auditApply")
    @ResponseBody
	public Response<String> auditApply(String userId, String id, Integer status){
		Response<String> result = new Response<String>();
		if (null == status || (status != AuditType.PASS.getIndex() && status != AuditType.FAIL.getIndex())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("审核状态异常");
			return result;
		}
		TeamDto team = isOwnerTeam(userId);
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您没有权限审核");
			return result;
		}
		UserApplyTeamDto userApplyTeam = userApplyTeamService.getUserApplyTeamById(id);
		if (null == userApplyTeam){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,该记录不存在!");
			return result;
		}
		if (null != userApplyTeam.getStatus() && userApplyTeam.getStatus() != AuditType.UNAUDIT.getIndex()){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,已经审核过了!");
			return result;
		}
		
		UserDto user = userService.getUserById(userApplyTeam.getUserId());
		if (null != user){
			if (StringUtils.isNotBlank(user.getTeamId())){
				TeamDto userTeam = teamService.getTeamById(user.getTeamId());
				if (null != userTeam ){
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("对不起,该用户已经加入其它球队!");
					userApplyTeam.setStat(DataStatus.DISABLED);
					userApplyTeamService.update(userApplyTeam);
					return result;
				}
			}
		}
		if (status == AuditType.PASS.getIndex()){
			user.setTeamId(team.getId());
			userService.update(user);
		}
		userApplyTeam.setStatus(status);
		userApplyTeamService.update(userApplyTeam);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setMessage("恭喜你,审核成功!");
		return result;
	}

	/**		
	 * <p>Description: 分页查询球队下所有用户 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @param query
	 * @return Response<PageResult<UserDto>>
	 * @author wangxiang	
	 * @date 16/4/5 下午1:31
	 * @version 1.0
	 */
	@RequestMapping(value="playersList")
	@ResponseBody
	public Response<PageResult<UserDto>> playersList(String userId, PageQuery query){
		Response<PageResult<UserDto>> result = new Response<PageResult<UserDto>>();
		TeamDto team = isOwnerTeam(userId);
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您没有权限查看!");
			return result;
		}
		UserDto dto = new UserDto();
		dto.setTeamId(team.getId());
		PageResult<UserDto> datas = userService.findUserByParam(dto, query);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(datas);

		return result;
	}

	/**
	 * 审核球队用户申请
	 * @param userId
	 * @param kickingId
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午11:06:45
	 */
	@RequestMapping(value="kicking")
	@ResponseBody
	public Response<String> kicking(String userId, String kickingId){
		Response<String> result = new Response<String>();
		TeamDto team = isOwnerTeam(userId);
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您没有权限审核");
			return result;
		}
		UserDto user = userService.kickingTeam(kickingId);
		if (null == user){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,球员踢出失败!");
			return result;
		}
		result.setMessage("恭喜你,球员已踢出!");
		return result;
	}

	/**
	 * 查询该用户是否是该球队的队长
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午10:01:24
	 */
	private TeamDto isOwnerTeam(String userId) {
		UserDto user = getUserByCache(userId);
		if (StringUtils.isBlank(user.getTeamId())){
			return null;
		}
		if (user.getUtype() == UserType.GENERAL.getIndex()){
			return null;
		}
		if (StringUtils.isBlank(user.getTeamId())){
			return null;
		}
		TeamDto team = teamService.getTeamById(user.getTeamId());
		
		if (null != team && team.getStat() == DataStatus.ENABLED){
			return team;
		}
		/*if (StringUtils.equals(team.getCreateUserId(), userId)){
			return team;
		}*/
		return null;
	}

	private boolean checkUserApplyTeam(Response<?> result, String userId, String teamId){
		if (StringUtils.isBlank(userId)){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("请先登录...");
			return false;
		}
		UserDto user = getUserByCache(userId);
    	if (null != user){
			if (user.getUtype() == UserType.CAPTAIN.getIndex()){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("队长请创建自己的球队");
				return false;
			}

    		UserApplyTeamDto dto = new UserApplyTeamDto();
    		dto.setUserId(userId);
    		dto.setTeamId(teamId);
    		List<UserApplyTeamDto> applies = userApplyTeamService.findUserApplyTeamByParam(dto);
    		if (null != applies && applies.size() > 0){
    			for (UserApplyTeamDto apply : applies){
    				if (apply.getStatus() == AuditType.UNAUDIT.getIndex()){
    					result.setStatus(DataStatus.HTTP_FAILE);
    					result.setMessage("正在申请中,请耐心等待队长审核...");
    					return false;
    				}
    				if (apply.getStatus() == AuditType.PASS.getIndex()){
    					result.setStatus(DataStatus.HTTP_FAILE);
    					result.setMessage("您已经是该球队中一员");
    					return false;
    				}
    			}
    		}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    		return false;
    	}
		
		return true;
	}
	
}