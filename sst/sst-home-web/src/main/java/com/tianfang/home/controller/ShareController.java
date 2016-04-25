package com.tianfang.home.controller;

import com.tianfang.common.constants.ShareType;
import com.tianfang.home.dto.ActiveShareDto;
import com.tianfang.home.dto.ShareDto;
import com.tianfang.message.dto.ActivityDto;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.service.IActivityService;
import com.tianfang.message.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>Description: 类描述:分享接口(新闻/活动) </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 13:44
 */
@Controller
public class ShareController extends BaseController{

    @Autowired
    private IInformationService infoService;
    @Autowired
    private IActivityService activityService;

    @RequestMapping(value = "share")
    public ModelAndView view(String id, int type){
        ModelAndView mv = getModelAndView();

        ShareDto share = null;
        // 新闻
        if (type == ShareType.NEWS.getIndex()){
            InformationDto information = infoService.getInformationById(id);
            share = new ShareDto();
            share.setId(information.getId());
            share.setContent(information.getContent());
            share.setPic(information.getPic());
            share.setSummary(information.getSummary());
            share.setTitle(information.getTitle());
        }
        // 活动
        else if (type == ShareType.ACTIVE.getIndex()){
            ActivityDto activity = activityService.getActivityById(id);
            share = new ShareDto();
            share.setId(activity.getId());
            share.setContent(activity.getContent());
            share.setPic(activity.getPic());
            share.setSummary(activity.getSummary());
            share.setTitle(activity.getTitle());
            ActiveShareDto active = new ActiveShareDto();
            active.setActivityDate(activity.getActivityDate());
            active.setAddress(activity.getAddress());
            active.setEndTime(activity.getEndTime());
            active.setStartTime(activity.getStartTime());
            share.setActive(active);
        }

        mv.addObject("data", share);
        mv.setViewName("share");
        return mv;
    }
}
