package com.tianfang.admin.controller.admin;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.dto.AdminMenuAuthRespDto;
import com.tianfang.admin.service.IAdminMenuService;
import com.tianfang.admin.service.IAdminService;
import com.tianfang.common.digest.MD5Coder;
import com.tianfang.common.ext.ExtPageQuery;
import com.tianfang.common.model.MessageResp;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**		
 * <p>Title: AdminController </p>
 * <p>Description: 类描述:后台管理员管理</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月12日上午9:18:43
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController extends BaseController {

    protected static final Log logger = LogFactory.getLog(AdminController.class);
    
    @Autowired
    private IAdminService adminService;
    
    @Autowired
    private IAdminMenuService menuService;
    
    @RequestMapping(value = "list")
    public ModelAndView findpage(AdminDto dto, ExtPageQuery page) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        PageResult<AdminDto> result = adminService.findPage(dto, page.changeToPageQuery());
        mv.setViewName("/admin/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }
    
    @RequestMapping(value = "add")
    public ModelAndView add() {
        logger.info("打开新增用户页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        try {
            mv.setViewName("/admin/add");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }                       
        return mv;
    }
    
    @RequestMapping(value = "save")
    @ResponseBody
    public Map<String, Object> save(AdminDto dto) throws Exception{
        if (StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPwd())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        String md5passwd = MD5Coder.encodeMD5Hex(dto.getPwd());
        dto.setPwd(md5passwd);
        Map<String, Object> result;
        try {
			adminService.save(dto);
			result = MessageResp.getMessage(true, "新增成功~");
		} catch (Exception e) {
			result = MessageResp.getMessage(false, e.getMessage());
			e.printStackTrace();
			logger.error("save方法抛出异常:"+e.getMessage());
		}
        return result;
    }
    
    @RequestMapping(value = "edit")
    public ModelAndView edit(String id) {
        logger.info("打开编辑用户页面");
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        AdminDto dto = adminService.getAdminById(id);
        mv.setViewName("/admin/edit");
        mv.addObject("datas", dto);
        return mv;
    }
    
    @RequestMapping(value = "getAdmin")
    @ResponseBody
    public Map<String, Object> getAdmin(String id) {
    	AdminDto dto = adminService.getAdminById(id);
        if(dto == null){ return  MessageResp.getMessage(false, "没有此条信息");}
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("data", dto);
        return result;
    }
    
    @RequestMapping(value = "update")
    @ResponseBody
    public Map<String, Object> update(AdminDto dto) throws Exception{
        if (StringUtils.isBlank(dto.getAccount())) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        if (StringUtils.isBlank(dto.getPwd())) {
        	dto.setPwd(null);
        }
        if (StringUtils.isNotBlank(dto.getPwd())) {
            String md5passwd = MD5Coder.encodeMD5Hex(dto.getPwd());
            dto.setPwd(md5passwd);
        }     
        Map<String, Object> result;
        try {
			adminService.edit(dto);
			result = MessageResp.getMessage(true, "编辑成功~");
		} catch (Exception e) {
			e.printStackTrace();
			result = MessageResp.getMessage(false, e.getMessage());
			logger.error("update方法抛出异常:"+e.getMessage());
		}
        return result;
    }
    
    @RequestMapping(value = "delAdIds")
    @ResponseBody
    public Map<String, Object> delAdIds(String delAsIds) throws Exception{
        if (StringUtils.isBlank(delAsIds)) {
            return MessageResp.getMessage(false, "请求参数不能为空~");
        }
        Integer resObject =(Integer) adminService.delAdIds(delAsIds);
        if (resObject == 0) {
            return MessageResp.getMessage(false, "无此条记录~");
        }
        if (resObject == 1) {
            return MessageResp.getMessage(true, "删除成功~");
        }
        return MessageResp.getMessage(false, "未知错误~");
    }
    
    /**
     * 打开菜单权限
     * @param adminId
     * @return
     * @author xiang_wang
     * 2016年1月12日上午11:55:47
     */
    @RequestMapping(value = "auth")
    public ModelAndView auth(String adminId) {
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());   
        List<AdminMenuAuthRespDto> trainingMenuAuthRespDtos = menuService.getAdminAuthById(adminId);
        String jsonStr = JsonUtil.getJsonStr(trainingMenuAuthRespDtos);
        jsonStr = jsonStr.replaceAll("menuTitle", "name");
        logger.info("jsonStr="+jsonStr);
        mv.setViewName("/admin/admin_auth");
        mv.addObject("adminId", adminId);
        mv.addObject("zTreeNodes", jsonStr);
        return mv;
    }
    
    @RequestMapping(value="distributionAuth")
    @ResponseBody
    public Map<String, Object> distributionAuth(String adminId,String menuIds) {
        adminService.distributionAuth(adminId, menuIds);
        return MessageResp.getMessage(true, "授权成功~");
    }
}