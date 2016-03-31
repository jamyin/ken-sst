package com.tianfang.home.controller;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.*;
import com.tianfang.home.dto.AppOption;
import com.tianfang.home.dto.AppVoteDatas;
import com.tianfang.user.app.VoteApp;
import com.tianfang.user.dto.*;
import com.tianfang.user.service.IVoteService;
import com.tianfang.user.service.IVoteUserOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
	 * 发起投票接口(ios端)
	 * @param datas
	 * @param request
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value ="publishIOS" ,method = RequestMethod.POST)
	@ResponseBody
	public Response<String> publishIOS(String datas, HttpServletRequest request) throws Exception{
		Response<String> result = new Response<String>();
		AppVoteDatas vote = JSON.parseObject(datas, AppVoteDatas.class);
		if (StringUtils.isBlank(vote.getUserId())){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("用户未登陆");
			return result;
		}
		if (null == vote.getToUserIds() || vote.getToUserIds().length == 0){
			result.setStatus(DataStatus.HTTP_FAILE);
			result.setMessage("未选择发送用户");
			return result;
		}
		UserDto user = getUserByCache(vote.getUserId());
		if (null != user){
			try {
				// 组装Vote数据
				String voteId = UUIDGenerator.getUUID();
				VoteDto dto = assemblyVote(vote, user, voteId);
				// 组装VoteUserTemp数据
				List<VoteUserTempDto> temps = assemblyTemps(vote, voteId);
				// 组装VoteOption(ios端组装方式)
				List<VoteOptionDto> options = assemblyVoteOptions(vote, voteId);
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
	 * 发起投票接口(android端)
	 * @param vote
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午1:11:40
	 */
	@RequestMapping(value ="publish" ,method = RequestMethod.POST)
	@ResponseBody
	public Response<String> publish(@RequestBody AppVoteDatas vote) throws Exception{
		Response<String> result = new Response<String>();
		if (StringUtils.isBlank(vote.getUserId())){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("用户未登陆");
    		return result;
		}
		if (null == vote.getToUserIds() || vote.getToUserIds().length == 0){
			result.setStatus(DataStatus.HTTP_FAILE);
    		result.setMessage("未选择发送用户");
    		return result;
		}
		UserDto user = getUserByCache(vote.getUserId());
    	if (null != user){
			try {
				// 组装Vote数据
				String voteId = UUIDGenerator.getUUID();
				VoteDto dto = assemblyVote(vote, user, voteId);
				// 组装VoteUserTemp数据
				List<VoteUserTempDto> temps = assemblyTemps(vote, voteId);
				// 组装VoteOption
				List<VoteOptionDto> options = assemblyVoteOptions(vote, voteId);
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
	 * 根据用户id获取最新一条投票信息
	 * @param userId
	 * @return
     */
	@RequestMapping(value = "getLast")
	@ResponseBody
	public Response<VoteDto> getLast(String userId){
		Response<VoteDto> response = new Response<VoteDto>();
		if (StringUtils.isBlank(userId)){
			response.setStatus(DataStatus.HTTP_FAILE);
			response.setMessage("请求参数异常");
			return response;
		}
		VoteDto last = voteService.getLast(userId);
		response.setData(last);
		return response;
	}

	/**
	 * base64图片 选项数据组装
	 * @param vote
	 * @param voteId
     * @return
     */
	private List<VoteOptionDto> assemblyVoteOptions(AppVoteDatas vote, String voteId) {
		logger.info("---------base64图片 端数据组装逻辑-------------------");
		List<AppOption> options1 = vote.getOptions();
		if (null != options1 && options1.size() > 0){
			List<VoteOptionDto> options = new ArrayList<VoteOptionDto>(options1.size());
			VoteOptionDto option;
			for (AppOption a : options1){
				option = new VoteOptionDto();
				option.setId(UUIDGenerator.getUUID());
				option.setVoteId(voteId);
				option.setText(a.getOptionText());
				option.setCreateTime(new Date());
				option.setNum(0);
				option.setStat(DataStatus.ENABLED);
				if (StringUtils.isNotBlank(a.getBase64Img())){
					String fileUrl = null;
					try {
						fileUrl = FileUtils.upload64Image(a.getBase64Img());
					} catch (Exception e) {
						logger.error("图片上传失败.....");
						e.printStackTrace();
					}
					option.setPic(fileUrl);
				}

				options.add(option);
			}

			return options;
		}

		return null;
	}

	/**
	 * 组装投票用户关联表数据
	 * @param vote
	 * @param voteId
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午3:10:00
	 */
	private List<VoteUserTempDto> assemblyTemps(AppVoteDatas vote, String voteId) {
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
		voteUser.setUserId(vote.getUserId());
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
	 * @param optionId
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
}