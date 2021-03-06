package com.tianfang.home.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.service.ITeamPlayerService;
import com.tianfang.user.app.AppUserInfo;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.ReasonJson;
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

import java.util.*;

@Controller
@RequestMapping(value = "userFriend")
public class UserFriendController extends BaseController{

	protected static final Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private IUserService userService;	
	@Autowired
	private IUserApplyTeamService iUserApplyTeamService;
	@Autowired
	private ITeamPlayerService playerService;
	
	/**
	 * 
	  @Name: UserFriendController.java
	  @Author: pengpeng
	  @Date: 2016年4月9日 下午4:31:32 
	  @Description:用户申请球队列表
	 */
	@RequestMapping(value="/user/teamList")
	@ResponseBody
	public Response<PageResult<AppUserInfo>> findUserTeamPage(UserApplyTeamDto dtos,ExtPageQuery page) {
		Response<PageResult<AppUserInfo>> result = new Response<PageResult<AppUserInfo>>();
		UserDto user = getUserByCache(dtos.getUserId());		
		if (null != user){
			PageResult<AppUserInfo> userApplyTeamDtos = iUserApplyTeamService.queryUserTeamApplyInfoByParam(dtos, page.changeToPageQuery());
			if (null != userApplyTeamDtos && null != userApplyTeamDtos.getResults() && userApplyTeamDtos.getResults().size() > 0){
				for (AppUserInfo info : userApplyTeamDtos.getResults()){
					if (null != info){
						assemblyReason(info);
					}
				}
			}
			if (null != userApplyTeamDtos && userApplyTeamDtos.getResults().size()>0) {
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("查询成功");
				result.setData(userApplyTeamDtos);
			} else {
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setMessage("无数据");
			}
		}else {
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
		}
		return result;
	}
	
	 /**
     * 移动端查询用户赛事好友
		普通用户：赛事好友
		管理员：赛事好友+队内成员（最好去重复）   
     * @return {"status":状态码(200-成功,500-失败),"message":"提示信息","data":List<FriendApp>}
     * @author xiang_wang
     * 2016年1月19日上午10:13:04
     */
    @RequestMapping(value="list")
    @ResponseBody
    public Response<List<FriendApp>> findFriends(String userId) {
    	Response<List<FriendApp>> result = new Response<List<FriendApp>>();
    	UserDto dto = getUserByCache(userId);
    	if (!Objects.equal(dto, null)){
			//赛事好友
			List<FriendApp> raceFriendList = userService.findFriendsByUserId(userId);//普通用户
			
    		if(!Objects.equal(dto.getUtype(), UserType.GENERAL)){//管理员用户
				TeamPlayerDto player = playerService.getTeamPlayeByUserId(userId);
				if (null != player){
					//队内成员
					List<FriendApp> teamFriends = userService.findTeamFriends(player.getTeamId());
					Iterator<FriendApp> iterator = teamFriends.iterator();
					while (iterator.hasNext()){
						FriendApp fApp = iterator.next();
						if(Objects.equal(userId, fApp.getFriendId())){//排除自身用户
							iterator.remove();
						}
						fApp.setUserId(dto.getId());
						fApp.setUserMobile(dto.getMobile());
					}
					// 赛事好友  + 队内成员
					raceFriendList.addAll(teamFriends);
				}

    			raceFriendList = removeDuplicateWithOrder(raceFriendList);
    			
    		}
			result.setData(raceFriendList);
//			try {
//				result.setStatus(DataStatus.HTTP_SUCCESS);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error(e.getMessage());
//				result.setStatus(DataStatus.HTTP_FAILE);
//	    		result.setMessage("系统异常");
//			}  
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
       
        return result;
    }
    //数据去重复
    public static List<FriendApp> removeDuplicateWithOrder(List<FriendApp> list) {
        Set<FriendApp> set = new HashSet<FriendApp>();
        List<FriendApp> newList = new ArrayList<FriendApp>();
        for (Iterator<FriendApp> iter = list.iterator(); iter.hasNext();) {
        	FriendApp element = iter.next();
            if (set.add(element)){
            	newList.add(element);
            }
        }
        return newList;
    }

	private void assemblyReason(AppUserInfo info) {
		if (info.getStatus() == AuditType.FAIL.getIndex()){
			if (StringUtils.isNotBlank(info.getReason())){
				ReasonJson reasonJson = JSON.parseObject(info.getReason(), ReasonJson.class);
				if (null != reasonJson.getRefuse()){
					info.setReason(reasonJson.getRefuse());
				}else{
					info.setReason("");
				}
			}
		}
	}
}
