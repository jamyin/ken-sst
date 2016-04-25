package com.tianfang.admin.controller.team;

import java.util.List;

import com.tianfang.admin.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.model.Response;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.dto.TeamResultDto;
import com.tianfang.train.service.ITeamResultService;
import com.tianfang.train.service.ITeamService;

@Controller
@RequestMapping(value = "team/result")
public class TeamResultController extends BaseController {
	protected static final Log logger = LogFactory.getLog(TeamResultController.class);
	
	@Autowired
	private ITeamResultService resultService;
	@Autowired
	private ITeamService teamService;
	
	@RequestMapping(value = "list")
    public ModelAndView findpage(TeamResultDto dto, ExtPageQuery page) throws Exception {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<TeamResultDto> result = resultService.findTeamResultByParam(dto, page.changeToPageQuery());
        mv.setViewName("/team/result/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
	
	@RequestMapping(value = "add")
    public ModelAndView add() {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
			List<TeamDto> teams = teamService.findAll();
			mv.addObject("teams", teams);
			mv.setViewName("/team/result/add");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/save")
	public Response<String> save(TeamResultDto dto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		dto.setCreateUserId(admin.getId());
		dto.setCreateUserName(admin.getAccount());
		try {
			resultService.save(dto);
			data.setMessage("新增成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("新增失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "edit")
    public ModelAndView edit(String id) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
        	TeamResultDto result = resultService.getTeamResultById(id);
			List<TeamDto> teams = teamService.findAll();
			mv.addObject("teams", teams);
			mv.addObject("result", result);
			mv.setViewName("/team/result/edit");
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			mv.setViewName("500");
		}
        return mv;
    }
	
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> update(TeamResultDto dto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		dto.setUpdateUserId(admin.getId());
		dto.setUpdateUserName(admin.getAccount());
		try {
			resultService.update(dto);
			data.setMessage("编辑成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("编辑失败");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
		return data;
	}
	
	@RequestMapping(value = "delIds")
    @ResponseBody
    public Response<String> delAdIds(String ids){
		Response<String> data = new Response<String>();
        if (StringUtils.isBlank(ids)) {
        	data.setMessage("请求参数不能为空~");
			data.setStatus(DataStatus.HTTP_FAILE);
            return data;
        }
        try {
			resultService.del(ids);
			data.setMessage("删除成功");
			data.setStatus(DataStatus.HTTP_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			data.setMessage("删除失败~");
			data.setStatus(DataStatus.HTTP_FAILE);
		}
        return data;
    }
}