package com.tianfang.home.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.PropertiesUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.home.dto.AppVoteDatas;
import com.tianfang.user.app.VoteApp;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.dto.VoteDto;
import com.tianfang.user.dto.VoteOptionDto;
import com.tianfang.user.dto.VoteParams;
import com.tianfang.user.dto.VoteUserOptionDto;
import com.tianfang.user.dto.VoteUserTempDto;
import com.tianfang.user.service.IVoteOptionService;
import com.tianfang.user.service.IVoteService;
import com.tianfang.user.service.IVoteUserOptionService;

/**		
 * <p>Title: VoteController </p>
 * <p>Description: 类描述:投票接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年3月8日下午3:14:01
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value = "vote")
public class VoteController extends BaseController{
	
	@Autowired
	private IVoteService voteService;
	@Autowired
	private IVoteOptionService voteOptionService;
	@Autowired
	private IVoteUserOptionService voteUserOptionService;

	/**
	 * 分页查询用户参与的投票
	 * @param query
	 * @param params
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日上午9:22:11
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Response<PageResult<VoteDto>> list(PageQuery query, VoteParams params){
		Response<PageResult<VoteDto>> result = new Response<PageResult<VoteDto>>();
		if (StringUtils.isBlank(params.getUserId())){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户未登陆");
    		return result;
		}
		UserDto user = getUserByCache(params.getUserId());
    	if (null != user){
			try {
				PageResult<VoteDto> votes = voteService.findVoteTempByParam(params, query);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(votes);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
		
		return result;
	}
	
	/**
	 * 根据投票id查询投票详情
	 * @param voteId
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日上午9:32:22
	 */
	@RequestMapping(value = "detail")
	@ResponseBody
	public Response<VoteApp> detail(String voteId, String userId){
		Response<VoteApp> result = new Response<VoteApp>();
		if (StringUtils.isBlank(userId)){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户未登陆");
    		return result;
		}
		UserDto user = getUserByCache(userId);
    	if (null != user){
			try {
				VoteApp vote = voteService.getVoteAppById(voteId);
				result.setStatus(DataStatus.HTTP_SUCCESS);
				result.setData(vote);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
		
		return result;
	}
	
	/**
	 * 用户投票接口
	 * @param userId
	 * @param optionId
	 * @param voteId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日上午10:56:18
	 */
	@RequestMapping(value = "put")
	@ResponseBody
	public Response<String> put(String userId, String optionId, String voteId){
		Response<String> result = new Response<String>();
		if (StringUtils.isBlank(userId)){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户未登陆");
    		return result;
		}
		UserDto user = getUserByCache(userId);
    	if (null != user){
			try {
				VoteDto vote = checkVoteException(userId, optionId, voteId, result);
				if (result.getStatus() == DataStatus.HTTP_FAILE){
					return result;
				}
				String[] optionIds = optionId.split(",");
				// 保存用户投票结果
				for (String opId : optionIds){
					VoteUserOptionDto vuo = new VoteUserOptionDto();
					vuo.setOptionId(opId);
					vuo.setUserId(userId);
					vuo.setVoteId(voteId);
					voteUserOptionService.save(vuo);
				}
				
				// 更新投票用户关联表selected字段
				VoteUserTempDto temp = new VoteUserTempDto();
				temp.setId(vote.getTempId());
				temp.setSelected(DataStatus.ENABLED);
				voteService.update(temp);
				
				// 累加投票表amount字段
				int amount = vote.getAmount() == null? 0 : vote.getAmount();
				vote.setAmount(amount + optionIds.length);
				voteService.update(vote);
				result.setMessage("投票成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
		
		return result;
	}
	
	/**
	 * 发起投票接口
	 * @param vote
	 * @param userIds
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午1:11:40
	 */
	@RequestMapping(value ="publish")
	@ResponseBody
	public Response<String> publish(AppVoteDatas vote, String userId, HttpServletRequest request){
		Response<String> result = new Response<String>();
		if (StringUtils.isBlank(userId)){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户未登陆");
    		return result;
		}
		if (null == vote.getToUserIds() || vote.getToUserIds().length == 0){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("未选择发送用户");
    		return result;
		}
		UserDto user = getUserByCache(userId);
    	if (null != user){
			try {
				// 组装Vote数据
				String voteId = UUIDGenerator.getUUID();
				VoteDto dto = assemblyVote(vote, user, voteId);
				// 组装VoteUserTemp数据
				List<VoteUserTempDto> temps = assemblyTemps(vote, voteId, userId);
				// 组装VoteOption
				List<VoteOptionDto> options = assemblyVoteOptions(vote, request, voteId);
				// 批量保存数据
				voteService.save(dto, temps, options);
				
				result.setMessage("发布成功!");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				result.setStatus(DataStatus.HTTP_FAILE);
	    		result.setMessage("系统异常");
			}
    	}else{
    		result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户不存在");
    	}
		
		return result;
	}

	/**
	 * 组装投票选择数据
	 * @param vote
	 * @param request
	 * @param voteId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午3:11:57
	 */
	private List<VoteOptionDto> assemblyVoteOptions(AppVoteDatas vote, HttpServletRequest request, String voteId) {
		List<VoteOptionDto> options = new ArrayList<VoteOptionDto>(vote.getOptionTexts().length);
		VoteOptionDto option;
		MultipartFile file;
		for (int i = 0, len = vote.getOptionTexts().length; i < len; i++){
			option = new VoteOptionDto();
			option.setId(UUIDGenerator.getUUID());
			option.setVoteId(voteId);
			option.setText(vote.getOptionTexts()[i]);
			option.setCreateTime(new Date());
			option.setNum(0);
			option.setStat(DataStatus.ENABLED);
			
			file = vote.getFiles()[i];
			if(!file.isEmpty()){
				Map<String, String> map = uploadImages(file, request);
				option.setPic(map.get("fileUrl"));
			}
			options.add(option);
		}
		return options;
	}

	/**
	 * 组装投票用户关联表数据
	 * @param vote
	 * @param voteId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午3:10:00
	 */
	private List<VoteUserTempDto> assemblyTemps(AppVoteDatas vote, String voteId, String userId) {
		List<VoteUserTempDto> temps = new ArrayList<VoteUserTempDto>(vote.getToUserIds().length);
		VoteUserTempDto voteUser;
		for (String uid : vote.getToUserIds()){
			voteUser = new VoteUserTempDto();
			voteUser.setId(UUIDGenerator.getUUID());
			voteUser.setSelected(DataStatus.DISABLED);
			voteUser.setUserId(uid);
			voteUser.setVoteId(voteId);
			voteUser.setCreateTime(new Date());
			voteUser.setStat(DataStatus.ENABLED);
			
			temps.add(voteUser);
		}
		// 给创建者,添加一条关联数据
		voteUser = new VoteUserTempDto();
		voteUser.setId(UUIDGenerator.getUUID());
		voteUser.setSelected(DataStatus.DISABLED);
		voteUser.setUserId(userId);
		voteUser.setVoteId(voteId);
		voteUser.setCreateTime(new Date());
		voteUser.setStat(DataStatus.ENABLED);
		
		temps.add(voteUser);
		
		return temps;
	}

	/**
	 * 组装Vote数据
	 * @param vote
	 * @param user
	 * @param voteId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午3:09:06
	 */
	private VoteDto assemblyVote(AppVoteDatas vote, UserDto user, String voteId) {
		VoteDto dto = new VoteDto();
		dto.setId(voteId);
		dto.setPublishId(user.getId());
		dto.setPublishName(user.getNickName());
		dto.setEndTime(DateUtils.parse(vote.getEndTime(), "yyyy-MM-dd HH"));
		dto.setIsAnonymous(vote.getIsAnonymous());
		dto.setOptionNum(vote.getOptionNum());
		dto.setTitle(vote.getTitle());
		dto.setAmount(0);
		
		return dto;
	}
	
	
	/**
	 * 1:校验用户是否已经投了.
	 * 2:校验该投票是否过期
	 * 3:校验用户投票选项是否超过投票所限制的条数
	 * @param userId
	 * @param optinId
	 * @author xiang_wang
	 * 2016年3月9日上午10:19:38
	 */
	private VoteDto checkVoteException(String userId, String optionId, String voteId, Response<?> result){
		if (StringUtils.isBlank(optionId)){
			result.setMessage("用户未选择投票选项!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		if (StringUtils.isBlank(voteId)){
			result.setMessage("参数异常!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		VoteParams params = new VoteParams();
		params.setVoteId(voteId);
		params.setUserId(userId);
		List<VoteDto> list = voteService.findVoteTempByParam(params);
		if (null == list || list.size() == 0){
			result.setMessage("对不起,系统异常!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		VoteDto vote = list.get(0);
		if (null != vote.getSelected() && vote.getSelected() == DataStatus.ENABLED){
			result.setMessage("对不起,您已经投过了!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		if (vote.getOverdue() == DataStatus.ENABLED){
			result.setMessage("对不起,该投票已经截止!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		
		String[] optionIds = optionId.split(",");
		if (optionIds.length > vote.getOptionNum()){
			result.setMessage("对不起,投票最多选择"+vote.getOptionNum()+"项!");
			result.setStatus(DataStatus.HTTP_FAILE);
			return null;
		}
		return vote;
	}
	
	
	private Map<String, String> uploadImages(@RequestParam("file") MultipartFile file,HttpServletRequest request){      	
    	//String context = "/upload";
		String realPath = PropertiesUtils.getProperty("upload.url");
		String fileDe = DateUtils.format(new Date(), DateUtils.YMD);
		String path = "";
		String filePath = "";
		String fileName = ""; //重新新命名
		String realName = "";
		Map<String, String> m = new HashMap<String, String>();
    	if(file.isEmpty()){
    		System.out.println("请选择需要上传的文件!");  
    		m.put("message", "请选择需要上传的文件!");
	       	return m;
    	}else{
    			realName = file.getOriginalFilename();
 	            System.out.println("fileName4---------->" + realName); 
 	            if(file.getSize()> DataStatus._FILESIZE_){
 	       		System.out.println("上传图片大小不允许超过1M");
 	       		m.put("message", "上传图片大小不允许超过1M");
 	       		return m;
 	            }
 	                int pre = (int) System.currentTimeMillis();  
 	                path = realPath + "/" + fileDe;
 	                fileName = this.getUploadFileName(file.getOriginalFilename());
 	                filePath = path  + "/" + fileName;
 	                File f = new File(path);
 	                //如果文件夹不存在则创建    
 	                if(!f.exists() && !f.isDirectory()) {
 	                  f.mkdir();    
 	                }
 	                try {  
 	                	file.transferTo(new File(path + "/" + fileName));
 	                    int finaltime = (int) System.currentTimeMillis();  
 	                    System.out.println("上传3共耗时：" + (finaltime - pre) + "毫秒");  
 	                }catch (FileNotFoundException e) {
 	                    e.printStackTrace();
 	                }catch (IOException e) {  
 	                    e.printStackTrace();  
 	                }  
    	}
        System.out.println("上传成功4"); 
        m.put("fileUrl", filePath);
        m.put("realName", realName);
        return m;  
    }
	
	public String getUploadFileName(String fileName){
		String tempFile = fileName.substring(fileName.lastIndexOf(".")+1);
		return UUIDGenerator.getUUID32Bit() + "." + tempFile;
	}
}