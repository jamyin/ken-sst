package com.tianfang.admin.aspect;

import com.alibaba.fastjson.JSON;
import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.dto.AdminLogDto;
import com.tianfang.admin.service.IAdminLogService;
import com.tianfang.admin.utils.Const;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Description: 类描述:后台用户操作日志切面 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 *
 * @author wangxiang
 * @version 1.0
 * @date 2016/4/25 9:50
 */
public class AdminLogAspect {
    @Autowired
    private IAdminLogService adminLogService;

    protected static final Log logger = LogFactory.getLog(AdminLogAspect.class);

    public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object proceed = joinPoint.proceed();
        if (joinPoint.getTarget() instanceof IAdminLogService){
            logger.info("过滤adminLogService");
        }else{
            AdminDto admin = getSessionAdmin();
            AdminLogDto adminLog = new AdminLogDto();
            adminLog.setAdminId(admin.getId());
            adminLog.setAdminAccount(admin.getAccount());
            adminLog.setServiceName(className);
            adminLog.setMethodName(methodName);
            adminLog.setArgs(JSON.toJSONString(args));
            adminLog.setResult(JSON.toJSONString(proceed));
            adminLogService.save(adminLog);
        }

        return proceed;
    }

    private AdminDto getSessionAdmin(){
        AdminDto admin = (AdminDto)getRequest().getSession().getAttribute(Const.SESSION_USER);
        return admin;
    }

    /**
     * 得到request对象
     */
    private HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        return request;
    }
}
