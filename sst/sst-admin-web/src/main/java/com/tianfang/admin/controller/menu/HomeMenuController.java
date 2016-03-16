package com.tianfang.admin.controller.menu;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.dto.HomeMenuDto;
import com.tianfang.admin.service.IHomeMenuService;
import com.tianfang.admin.utils.PageData;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;

@Controller
@RequestMapping("/menu")
public class HomeMenuController extends BaseController{

protected static final Log logger = LogFactory.getLog(HomeMenuController.class);
    
    @Autowired
    private IHomeMenuService iHomeMenuService;
    
    @RequestMapping("/findPage")
    public ModelAndView findPage(HomeMenuDto dto,ExtPageQuery page){
    	ModelAndView mv = this.getModelAndView(this.getSessionUserId());
    	PageResult<HomeMenuDto> results= iHomeMenuService.findPage(dto, page.changeToPageQuery());
    	 mv.setViewName("/menu/list");
         mv.addObject("pageList", results);
         mv.addObject("params", dto);
         mv.addObject("alls", iHomeMenuService.findAll());
//         mv.addObject("menuTypes", MenuTypeEnums.getValus());
//         mv.addObject("acTypes", ActivityTypeEnums.getValus());
//         mv.addObject("videoTypes", VideoTypeEnums.getValus());
         return mv;
    }
    
    @RequestMapping("/openAddView")
    public ModelAndView openAddView(){
        logger.info("打开新增菜单页面");
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("/sport/menu/add");
//        mv.addObject("menuTypes", MenuTypeEnums.getValus());
//        mv.addObject("acTypes", ActivityTypeEnums.getValus());
//        mv.addObject("videoTypes", VideoTypeEnums.getValus());
        return mv;
    }
    
    /**
     * ajax查询上级菜单
     * @param type
     * @return
     * @author xiang_wang
     * 2015年12月22日下午3:18:39
     */
    @RequestMapping("/selectMenumType")
    @ResponseBody
    public List<HomeMenuDto> selectMenumType(Integer menuType){
    	List<HomeMenuDto> HomeMenuDtos = null;
    	if (null != menuType && menuType.intValue() > 1 ){
    		HomeMenuDtos = iHomeMenuService.findByMenuType(menuType - 1);
    	}
    	return HomeMenuDtos;
    }
    
    /**
     * 更新菜单排序
     * @param HomeMenuDto
     * @return
     * @author xiang_wang
     * 2015年12月23日下午3:36:44
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public Map<String, Object> updateOrder(HomeMenuDto homeMenuDto){
    	Integer result = iHomeMenuService.edit(homeMenuDto);
    	if (result == 1) {
            return MessageResp.getMessage(true,"更新菜单排序成功");
        }
        return MessageResp.getMessage(false,"更新菜单排序失败");	
    }

    /**
     * 新增菜单
     * @param HomeMenuDto
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(HomeMenuDto homeMenuDto) {
//    	if (null != HomeMenuDto.getMenuType() && HomeMenuDto.getMenuType().intValue() == MenuTypeEnums.ONE.getIndex()) {
//    		HomeMenuDto.setParentId("0");
//    	}
        Integer result = iHomeMenuService.save(homeMenuDto);
        if (result == 1) {
            return MessageResp.getMessage(true,"新增成功");
        }
        return MessageResp.getMessage(false,"新增失败");
    }
    
    @RequestMapping("/openEditView")
    public ModelAndView openEditView () {
        logger.info("打开编辑菜单页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String id = pd.getString("id");
        HomeMenuDto homeMenuDto = iHomeMenuService.findById(id);     
//        if (null != HomeMenuDto.getParentId() && !"0".equals(HomeMenuDto.getParentId().trim()) && null != HomeMenuDto.getMenuType() && HomeMenuDto.getMenuType().intValue() > 1){
//        	List<HomeMenuDto> HomeMenuDtos = iHomeMenuService.findByMenuType(homeMenuDto.getMenuType() - 1);
//        	mv.addObject("parents", HomeMenuDtos);
//        }
        mv.setViewName("/sport/menu/edit");
        mv.addObject("data", homeMenuDto);
//        mv.addObject("menuTypes", MenuTypeEnums.getValus());
//        mv.addObject("acTypes", ActivityTypeEnums.getValus());
//        mv.addObject("videoTypes", VideoTypeEnums.getValus());
        
        return mv;
    }
    
    /**
     * 编辑菜单
     * @param HomeMenuDto
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(HomeMenuDto homeMenuDto) {
//    	if (null != HomeMenuDto.getMenuType() && HomeMenuDto.getMenuType().intValue() == MenuTypeEnums.ONE.getIndex()) {
//    		HomeMenuDto.setParentId("0");
//    	}
    	Integer result = iHomeMenuService.edit(homeMenuDto);
    	if (result == 1) {
            return MessageResp.getMessage(true,"编辑成功");
        }
        return MessageResp.getMessage(false,"编辑失败");		
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        if(ids == null || "".equals(ids)){
            return MessageResp.getMessage(false, "请求参数不能为空~") ;
        }
        iHomeMenuService.delete(ids);
        return MessageResp.getMessage(true, "删除成功！");
    }
}
