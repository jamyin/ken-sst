package com.tianfang.admin.controller.comp;

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
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.dto.CompetitionNewsDto;
import com.tianfang.train.service.ICompetitionNewsService;
import com.tianfang.train.service.ICompetitionService;

/**
 * 赛事动态Controller
 * @author YIn
 * @time:2016年1月15日 上午9:39:09
 * @ClassName: competitionNewsController
 * @Description: TODO
 * @
 */
@Controller
@RequestMapping("/competition/news")
public class CompetitionNewsController extends BaseController {

	@Autowired
	private ICompetitionNewsService competitionNewsService;
	
	@Autowired
	private ICompetitionService competitionService;
	
	/**
	 * 跳转至新增页面
	 * @return
	 */
	@RequestMapping(value = "goAdd.do")
	public ModelAndView goAdd() {
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		mv.setViewName("/competition/news/news_add");
		return mv;
	}

	/**
	 * 增加赛事动态
	 * @author YIn
	 * @time:2016年1月15日 上午10:11:14
	 * @param albumPicDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public Response<String> addCompetitionNews(CompetitionNewsDto competitionNewsDto){
		Response<String> data = new Response<String>();
		AdminDto admin = getSessionAdmin();
		competitionNewsDto.setCreateAdminId(admin.getId());
		competitionNewsDto.setCreateAdminName(admin.getAccount());
		int flag = competitionNewsService.addCompetitionNews(competitionNewsDto);
		if(flag > 0){
			data.setMessage("添加赛事动态成功");
			data.setStatus(200);
		}else{
			data.setMessage("添加赛事动态失败");
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
		logger.info("去赛事动态修改页面");
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		CompetitionNewsDto competitionNewsDto = new CompetitionNewsDto();
		competitionNewsDto.setId(id);
		List<CompetitionNewsDto> list =competitionNewsService.findCompetitionNews(competitionNewsDto);
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("allComp", compList);
		try {
			mv.setViewName("/competition/news/news_edit");
			mv.addObject("msg", "edit");
			mv.addObject("competitionNewsDto", list.get(0));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 根据主键Id编辑赛事动态
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNewsDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public Response<String> updateCompetitionNews(CompetitionNewsDto competitionNewsDto){
		Response<String> data = new Response<String>();
		
		int flag = competitionNewsService.updateCompetitionNews(competitionNewsDto);
		if(flag > 0){
			data.setMessage("编辑赛事动态成功");
			data.setStatus(200);
		}else{
			data.setMessage("编辑赛事动态失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNewsDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/del")
	public Response<String> delCompetitionNewsLogic(CompetitionNewsDto competitionNewsDto){
		Response<String> data = new Response<String>();
		
		int flag = competitionNewsService.delCompetitionNews(competitionNewsDto);
		if(flag > 0){
			data.setMessage("删除赛事动态成功");
			data.setStatus(200);
		}else{
			data.setMessage("删除赛事动态失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 根据主键Id删除 -逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午5:54:33
	 * @param competitionNewsDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete.do")
	public Map<String, Object> delCompetitionNews(CompetitionNewsDto competitionNewsDto){
		logger.info("competitionNewsDto"+competitionNewsDto);
		competitionNewsDto.setStat(0); //逻辑删除
		int status = competitionNewsService.updateCompetitionNews(competitionNewsDto);
		if(status > 0){
			return MessageResp.getMessage(true,"删除赛事动态成功");
		}
			return MessageResp.getMessage(false,"删除赛事动态失败");
		
	}
	
	/**
	 * 批量删除
	 * @author YIn
	 * @time:2015年11月16日 下午3:14:51
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
	    Integer resObject =(Integer) competitionNewsService.delByIds(ids);
	    if (resObject == 0) {
            return MessageResp.getMessage(false, "批量删除失败");
        }
	    if (resObject == 1) {
            return MessageResp.getMessage(true, "批量删除成功");
        }
	    return MessageResp.getMessage(false, "删除异常");
	}
	
	/**
	 * 查询赛事动态-不分页
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:05
	 * @param competitionNewsDto
	 * @return
	 */
	@RequestMapping(value="/find")
	@ResponseBody
	public Response<List<CompetitionNewsDto>> findCompetitionNews(CompetitionNewsDto competitionNewsDto){
		Response<List<CompetitionNewsDto>> data = new Response<List<CompetitionNewsDto>>();
		List<CompetitionNewsDto> result = competitionNewsService.findCompetitionNews(competitionNewsDto);
		if(result.size() > 0){
			data.setMessage("查询赛事动态成功");
			data.setStatus(200);
			data.setData(result);
		}else{
			data.setMessage("查询赛事动态失败");
			data.setStatus(500);
		}	   	
		return data;
	}
	
	/**
	 * 后台赛事动态显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:03
	 * @param competitionNewsDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompetitionNewsView")
	public ModelAndView findCompetitionNewsViewByPage(CompetitionNewsDto competitionNewsDto, ExtPageQuery page){
		logger.info("competitionNewsDto  : " + competitionNewsDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionNewsDto> result = competitionNewsService.findCompetitionNewsByPage(competitionNewsDto, page.changeToPageQuery());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("pageList", result);
		mv.addObject("allComp", compList);
		mv.addObject("competitionNewsDto", competitionNewsDto);
		mv.setViewName("/competition/news/news_list");
		return mv;
	}
	/**
	 * 后台赛事动态显示页面-分页(连接赛事表查询)
	 * @author YIn
	 * @time:2016年1月22日 上午11:45:38
	 * @param competitionNewsDto
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/findCompNewsView")
	public ModelAndView findCompNewsViewByPage(CompetitionNewsDto competitionNewsDto, ExtPageQuery page){
		logger.info("competitionNewsDto  : " + competitionNewsDto);
		ModelAndView mv = this.getModelAndView(this.getSessionUserId());
		PageResult<CompetitionNewsDto> result = competitionNewsService.findCompetitionNewsByPage(competitionNewsDto, page.changeToPageQuery());
		List<CompetitionDto> compList =  competitionService.findCompetition(new CompetitionDto());
		mv.addObject("pageList", result);
		mv.addObject("allComp", compList);
		mv.addObject("competitionNewsDto", competitionNewsDto);
		mv.setViewName("/competition/news/news_list");
		return mv;
	}
	
	
}