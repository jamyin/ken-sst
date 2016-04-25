package com.tianfang.admin.controller.admin;

import com.tianfang.admin.controller.BaseController;
import com.tianfang.admin.dto.AdminLogDto;
import com.tianfang.admin.service.IAdminLogService;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Description: 类描述:后台用户操作日志管理界面 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 11:14
 */
@Controller
@RequestMapping(value = "adminLog")
public class AdminLogController extends BaseController{
    @Autowired
    private IAdminLogService amdinLogService;
    private static final int PAGESIZE = 10;


    protected static final Log logger = LogFactory.getLog(AdminLogController.class);

    @RequestMapping(value = "list")
    public ModelAndView findpage(AdminLogDto dto, PageQuery page) throws Exception{
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        page.setPageSize(PAGESIZE);
        PageResult<AdminLogDto> result = amdinLogService.findAdminLogByParam(dto, page);
        mv.setViewName("/admin/log/list");
        mv.addObject("pageList", result);
        mv.addObject("params", dto);
        return mv;
    }

    @RequestMapping(value = "detail")
    public ModelAndView detail(String id) throws Exception{
        ModelAndView mv = this.getModelAndView(this.getSessionUserId());
        AdminLogDto datas = amdinLogService.getAdminLogById(id);
        mv.setViewName("/admin/log/detail");
        mv.addObject("datas", datas);
        return mv;
    }
}
