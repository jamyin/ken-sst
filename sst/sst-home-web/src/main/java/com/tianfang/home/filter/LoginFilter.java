package com.tianfang.home.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.home.utils.Const;

/**		
 * <p>Title: LoginFilter </p>
 * <p>Description: 类描述:后台管理登陆拦截器</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月12日下午12:56:29
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class LoginFilter implements HandlerInterceptor{
	protected static final Log logger = LogFactory.getLog(LoginFilter.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if("/admin/toLogin.do".equals(uri) || "/admin/login.do".equals(uri)|| "/admin/logout.do".equals(uri)){
			return true;
		}
	
        Object context= request.getSession().getAttribute(Const.SESSION_USER);   
        
        if(null == context){  
			response.sendRedirect("/admin/toLogin.do");

			return false;
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}