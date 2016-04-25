package com.tianfang.admin.controller.team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianfang.admin.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamImageDto;
import com.tianfang.train.service.ITeamImageService;
import com.tianfang.train.service.ITeamService;

/**
 * 球队图片Controller
 * @author YIn
 * @time:2016年1月27日 上午11:28:36
 * @ClassName: TeamController
 * @
 */

@Controller
@RequestMapping("/team/image")
public class TeamImageController extends BaseController {

	@Autowired
	private ITeamImageService teamImageService;
	
	@Autowired
	private ITeamService teamService;
	
	/**
	 * 跳转至图片新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.setViewName("/team/image/image_add");
		return mv;
	}

	/**
	 * 增加球队图片
	 * @author YIn
	 * @time:2016年1月27日 上午11:29:12
	 * @param teamImageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addTeamImage(TeamImageDto teamImageDto){
		Response<String> data = new Response<String>();
		Integer flag = 0;
		List<TeamImageDto> list = new ArrayList<TeamImageDto>();
		if(StringUtils.isNotEmpty(teamImageDto.getImgUrls())){
			String[] pics = teamImageDto.getImgUrls().split(",");
			for(int i = 0;i < pics.length;i++){
				TeamImageDto dto_ =new TeamImageDto();
				dto_.setNote(teamImageDto.getNote());
				dto_.setTeamId(teamImageDto.getTeamId());
				dto_.setTitle(teamImageDto.getTitle());
				String imgurl = pics[i];
				dto_.setImgUrl(imgurl);
				dto_.setId(UUIDGenerator.getUUID());
				dto_.setCreateTime(new Date());
				dto_.setStat(1);
				list.add(dto_);
//				flag = teamImageService.addTeamImage(teamImageDto);
			}
			AdminDto admin = getSessionAdmin();
			teamImageDto.setCreateUserId(admin.getId());
			teamImageDto.setCreateUserName(admin.getAccount());
			flag = teamImageService.addTeamImageBatch(list);
		}
		if(flag > 0){
			data.setMessage("添加球队图片成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加球队图片失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 跳转至编辑页面
	 * @return
	 */
	@RequestMapping(value = "goEdit.do")
	public ModelAndView goEdit(String id) {
		logger.info("去球队图片修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		TeamImageDto teamImageDto = new TeamImageDto();
		teamImageDto.setId(id);
		List<TeamImageDto> list =teamImageService.findTeamImage(teamImageDto);
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		try {
			mv.setViewName("/team/image/image_edit");
			mv.addObject("msg", "edit");
			mv.addObject("teamImageDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑球队图片
	 * @author YIn
	 * @time:2016年1月27日 上午11:30:11
	 * @param TeamImageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateTeamImage(TeamImageDto teamImageDto){
		Response<String> data = new Response<String>();
		int flag = teamImageService.updateTeamImage(teamImageDto);
		if(flag > 0){
			data.setMessage("编辑球队图片成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑球队图片失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 下午16:28:05
	 * @param teamImageDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delteamImageLogic(TeamImageDto teamImageDto){
		Response<String> data = new Response<String>();
		int flag = teamImageService.delTeamImage(teamImageDto);
		if(flag > 0){
			data.setMessage("删除球队图片成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除球队图片失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:31:05
	 * @param teamImageDto
	 * @return
	 */
	 
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delTeamImage(TeamImageDto teamImageDto){
		logger.info("TeamImageDto"+teamImageDto);
		teamImageDto.setStat(0); //逻辑删除
		int status = teamImageService.updateTeamImage(teamImageDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除球队图片成功");
		}
			return MessageResp.getMessage(false,"删除球队图片失败");
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:31:29
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delByIds.do")
    public Map<String, Object> delByIds(String  ids) throws Exception{
	    if (StringUtils.isEmpty(ids)) {
	        return MessageResp.getMessage(false, "请选择需要删除的项！");
	    }
	    Integer resObject =(Integer) teamImageService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询球队图片-不分页
	 * @author YIn
	 * @time:2016年1月27日 上午11:31:50
	 * @param teamImageDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<TeamImageDto>> findTeamImage(TeamImageDto teamImageDto){
		Response<List<TeamImageDto>> data = new Response<List<TeamImageDto>>();
		List<TeamImageDto> result = teamImageService.findTeamImage(teamImageDto);
		if(result.size() > 0){
			data.setMessage("查询球队图片成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询球队图片失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台球队图片显示页面-分页
	 * @author YIn
	 * @time:2016年1月13日 下午2:17:03
	 * @param teamImageDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findTeamImageView")
	public ModelAndView findTeamImageViewByPage(TeamImageDto teamImageDto, ExtPageQuery page){
		logger.info("TeamImageDto  : " + teamImageDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<TeamImageDto> result = teamImageService.findTeamImageViewByPage(teamImageDto, page.changeToPageQuery());
		List<TeamDto> teamList = teamService.findTeam(new TeamDto());
		mv.addObject("allTeam", teamList);
		mv.addObject("pageList", result);
		mv.setViewName("/team/image/image_list");
		return mv;
	}
	
	
}