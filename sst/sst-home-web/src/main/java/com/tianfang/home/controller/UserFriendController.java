package com.tianfang.home.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.Response;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.enums.UserType;
import com.tianfang.user.service.IUserApplyTeamService;
import com.tianfang.user.service.IUserService;

@Controller
@RequestMapping(value = "userFriend")
public class UserFriendController extends BaseController{

	protected static final Log logger = LogFactory.getLog(UserController.class);
	
	@Autowired
	private IUserService userService;	
	
	@Autowired
	private IUserApplyTeamService iUserApplyTeamService;
	
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
    			//队内成员 
    			List<FriendApp> teamFriends = userService.findTeamFriends(dto.getTeamId());
    			for(FriendApp fApp: teamFriends){
    				fApp.setUserId(dto.getId());
    				fApp.setUserMobile(dto.getMobile());
    			}
    			// 赛事好友  + 队内成员 
    			raceFriendList.addAll(teamFriends);
    			
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

}
