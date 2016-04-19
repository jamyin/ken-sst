package com.tianfang.home.controller;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.home.dto.AppTeamPlayer;
import com.tianfang.home.dto.AppUser;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.train.service.ITeamService;
import com.tianfang.user.app.AppUserInfo;
import com.tianfang.user.dto.ReasonJson;
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.enums.AuditType;
import com.tianfang.user.enums.UserType;
import com.tianfang.user.service.IUserApplyTeamService;
import com.tianfang.user.service.IUserInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private ITeamPlayerService playerService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 用户申请加入球队接口
	 * @param userId
	 * @param teamId
	 * @param reason 申请理由
	 * @return Response<String>
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
	public Response<PageResult<AppUserInfo>> listApply(String userId, PageQuery query){
		Response<PageResult<AppUserInfo>> result = new Response<PageResult<AppUserInfo>>();
		TeamDto team = isOwnerTeam(userId, result);
		if (null == team){
			return result;
		}
		AppUserInfo dto = new AppUserInfo();
		dto.setTeamId(team.getId());
		PageResult<AppUserInfo> datas = userApplyTeamService.queryUserApplyInfoByParam(dto, query);
		result.setStatus(DataStatus.HTTP_SUCCESS);
		result.setData(datas);

		return result;
	}

	/**
	 * <p>Description: 根据申请记录id,查询用户申请球队详情 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param id
	 * @return Response<AppUserInfo>
	 * @author wangxiang
	 * @date 16/4/9 下午3:56
	 * @version 1.0
	 */
	@RequestMapping(value="detailApply")
	@ResponseBody
	public Response<AppUserInfo> detailApply(String id){
		Response<AppUserInfo> response = new Response<>();
		try {
			AppUserInfo  info = userApplyTeamService.getUserApplyInfoById(id);
			if (null != info){
				if (info.getStatus() == AuditType.FAIL.getIndex()){
					if (StringUtils.isNotBlank(info.getReason())){
						ReasonJson reasonJson = JSON.parseObject(info.getReason(), ReasonJson.class);
						info.setReason(reasonJson.getRefuse());
					}
				}
			}
			response.setData(info);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("系统异常!");
		}

		return response;
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
		TeamDto team = isOwnerTeam(userId, result);
		if (null == team){
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
	 * @param reason 拒绝理由
	 * @return Response<String>
	 * @author xiang_wang
	 * 2016年3月7日上午11:06:45
	 */
	@RequestMapping(value="auditApply")
    @ResponseBody
	public Response<String> auditApply(String userId, String id, Integer status, String reason){
		Response<String> result = new Response<String>();
		if (null == status || (status != AuditType.PASS.getIndex() && status != AuditType.FAIL.getIndex())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("审核状态异常");
			return result;
		}
		TeamDto team = isOwnerTeam(userId, result);
		if (null == team){
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

		// 逻辑改动:由sport_user下查询teamId字段  改为  查询sst_team_player表中是否有用户
		TeamPlayerDto teamPlayer = getTeamPlayerByUserId(userApplyTeam.getUserId());
		if (null != teamPlayer){
			TeamDto userTeam = teamService.getTeamById(teamPlayer.getTeamId());
			if (null != userTeam){
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("对不起,该用户已经加入其它球队!");
				userApplyTeam.setStat(DataStatus.DISABLED);
				userApplyTeamService.update(userApplyTeam);
				return result;
			}
		}
		if (status == AuditType.PASS.getIndex()){
			UserInfoDto userInfo = userInfoService.getUserInfo(userApplyTeam.getUserId());
			if (null != userInfo){
				TeamPlayerDto player = assemblyPlayer(userApplyTeam, userInfo);
				playerService.save(player);
				result.setMessage("恭喜你,操作成功!");
			}else{
				result.setStatus(DataStatus.HTTP_FAILE);
				result.setMessage("用户参赛信息未填写!");
			}
		}
		// 如果拒绝本次申请
		if (status == AuditType.FAIL.getIndex()){
			ReasonJson reasonJson = new ReasonJson();
			reasonJson.setApply(userApplyTeam.getReason());
			reasonJson.setRefuse(reason);
			userApplyTeam.setReason(JSON.toJSONString(reasonJson));
			result.setMessage("恭喜你,拒绝成功!");
		}
		userApplyTeam.setStatus(status);
		userApplyTeamService.update(userApplyTeam);
		return result;
	}

	/**
	 * <p>Description: 查询球队下成员,按管理员和成员分组展示 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @return Response<Map<String, Object>>
	 * @author wangxiang
	 * @date 16/4/6 下午6:14
	 * @version 1.0
	 */
	@RequestMapping(value="queryPlayers")
	@ResponseBody
	public Response<Map<String, Object>> queryPlayers(String userId){
		Response<Map<String, Object>> result = new Response<Map<String, Object>>();
		UserDto curruser = getUserByCache(userId);
		if (null == curruser){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户不存在!");
			return result;
		}
		TeamPlayerDto player = getTeamPlayerByUserId(userId);
		if (null == player){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("暂未加入球队!");
			return result;
		}
		TeamDto team = teamService.getTeamById(player.getTeamId());
		if (null == team || team.getStat() == DataStatus.DISABLED){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("您加入的球队已经解散了!");
			return result;
		}
		TeamPlayerDto dto = new TeamPlayerDto();
		dto.setTeamId(team.getId());
		List<TeamPlayerDto> players = playerService.findTeamPlayerByParam(dto);
		Map<String, Object> map = new HashMap<String, Object>(2);
		List<AppTeamPlayer> results = new ArrayList<>(2);
		if (null != players && players.size() > 0){
			AppTeamPlayer gl = new AppTeamPlayer("管理员", new ArrayList<AppUser>());
			AppTeamPlayer cy = new AppTeamPlayer("球队成员", new ArrayList<AppUser>());
			for (TeamPlayerDto user : players){
				if (null != user){
					if (null == user.getUtype()){
						continue;
					}
					if (user.getUtype().intValue() == UserType.GENERAL.getIndex()){
						cy.getList().add(UserDtoToAppUser(user));
					}
					if (user.getUtype().intValue() == UserType.CAPTAIN.getIndex()){
						gl.getList().add(UserDtoToAppUser(user));
					}
					if (user.getUtype().intValue() == UserType.COACH.getIndex()){
						gl.getList().add(UserDtoToAppUser(user));
					}
					if (user.getUserId().equals(curruser.getId())){
						map.put("currUser", UserDtoToAppUser(user));
					}
				}
			}
			results.add(gl);
			results.add(cy);
		}
		map.put("list", results);
		result.setData(map);

		return result;
	}

	/**
	 * <p>Description: 球队球员踢出操作 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @param kickingId 被踢出人userId
	 * @return Response<String>
	 * @author wangxiang
	 * @date 16/4/5 下午1:54
	 * @version 1.0
	 */
	@RequestMapping(value="kicking")
	@ResponseBody
	public Response<String> kicking(String userId, String kickingId){
		Response<String> result = new Response<String>();
		TeamDto team = isOwnerTeam(userId, result);
		if (null == team){
			return result;
		}
		try {
			if (StringUtils.isNotBlank(kickingId)){
                String[] ids = kickingId.split(",");
                for (String id : ids){
                    TeamPlayerDto player = playerService.getTeamPlayerById(id);
                    if (null != player){
                        player.setStat(DataStatus.DISABLED);
                    }
                    playerService.update(player);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,球员踢出失败!");
			return result;
		}
		result.setMessage("恭喜你,球员已踢出!");
		return result;
	}

	/**
	 * 查询该用户是否是该球队的管理员
	 * @param userId
	 * @param result
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午10:01:24
	 */
	private TeamDto isOwnerTeam(String userId, Response<?> result) {
		UserDto user = getUserByCache(userId);
		if (user.getUtype() == UserType.GENERAL.getIndex()){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您没有权限查看!!");
			return null;
		}
		TeamPlayerDto player = playerService.getTeamPlayeByUserId(userId);
		if (null != player){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您还未创建球队!");
			return null;
		}
		TeamDto team = teamService.getTeamById(player.getTeamId());
		if (null == team){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("对不起,您所在球队已解散!");
			return null;
		}

		return team;
	}

	/**
	 * <p>Description: 用户申请加入球队逻辑 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param result
	 * @param userId
	 * @param teamId
	 * @return boolean
	 * @author wangxiang
	 * @date 16/4/12 上午9:39
	 * @version 1.0
	 */
	public boolean checkUserApplyTeam(Response<?> result, String userId, String teamId){
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
				// 取最新一条申请记录
				UserApplyTeamDto apply = applies.get(0);
				if (apply.getStatus() == AuditType.PASS.getIndex()){
					TeamPlayerDto player = getTeamPlayerByUserId(userId);
					if (null != player){
						result.setStatus(DataStatus.HTTP_FAILE);
						result.setMessage("您已经是该球队中一员");
						return false;
					}
				}
				// 24小时才可以申请一次
				if (System.currentTimeMillis() - apply.getCreateTime().getTime() < DataStatus.HOUR_24){
					result.setStatus(DataStatus.HTTP_FAILE);
					result.setMessage("24小时之内只准申请一次");
					return false;
				}
    		}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    		return false;
    	}

		return true;
	}

	private AppUser UserDtoToAppUser(TeamPlayerDto dto){
		if (null != dto){
			AppUser user = new AppUser();
			user.setId(dto.getId());
			user.setName(dto.getName());
			user.setCreateTime(dto.getCreateTime());
			user.setGender(dto.getGender());
			user.setUserId(dto.getUserId());
			user.setMobile(dto.getMobile());
			user.setPic(dto.getPic());
			user.setPosition(dto.getPositionStr());
			user.setTeamId(dto.getTeamId());
			user.setUtype(dto.getUtype());

			return user;
		}
		return null;
	}

	/**
	 * <p>Description: 根据用户id查询球队用户 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @return TeamPlayerDto
	 * @author wangxiang
	 * @date 16/4/14 上午11:37
	 * @version 1.0
	 */
	private TeamPlayerDto getTeamPlayerByUserId(String userId) {
		TeamPlayerDto param = new TeamPlayerDto();
		param.setUserId(userId);
		List<TeamPlayerDto> teamPlayer = playerService.findTeamPlayerByParam(param);
		if (null != teamPlayer && teamPlayer.size() > 0){
			if (teamPlayer.size() != 1){
				logger.error("sst_team_plaryer 数据出现异常:"+JSON.toJSONString(teamPlayer));
			}
			return teamPlayer.get(0);
		}
		return null;
	}

	/**		
	 * <p>Description: 组装球队球员数据 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param
	 * @return
	 * @author wangxiang	
	 * @date 16/4/14 上午11:58
	 * @version 1.0
	 */
	private TeamPlayerDto assemblyPlayer(UserApplyTeamDto userApplyTeam, UserInfoDto userInfo) {
		TeamPlayerDto player = new TeamPlayerDto();
		player.setMobile(userInfo.getMobile());
		player.setAge(userInfo.getAge());
		player.setCardNo(userInfo.getCardNo());
		player.setGender(userInfo.getGender());
		player.setName(userInfo.getName());
		player.setStudentNo(userInfo.getStudentNo());
		player.setSchool(userInfo.getSchool());
		// 保存用户头像
		player.setPic(userApplyTeam.getPic());
		player.setTeamId(userApplyTeam.getTeamId());
		player.setUserId(userApplyTeam.getUserId());

		return player;
	}
}