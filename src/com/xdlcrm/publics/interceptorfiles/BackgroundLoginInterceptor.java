package com.xdlcrm.publics.interceptorfiles;

import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackgroundLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //http://www.n.com/user/saveUser.do
        //获取当前请求的接口地址
        String path = request.getServletPath();
        System.out.println(path);
        // ContextPath();//获取请求的相对路径
        // ServletPath();// 获取请求的原始路径地址
        // RealPath("");//代表获取真实的路径(绝对路径)
        User user =UserContext.getLoginUser();
        if(user!=null){
            return true;
        }else{
            if ("/login.do".equals(path)){
                return true;
            }else{
                response.sendRedirect( request.getContextPath() + "/login.do");
                return false;
            }

        }

        //如果是请求的logind.do接口，则返回true
        //如果是其它接口地址，则首选判断用户是否登录，如果没有登录，则跳转login.do，如果已经登录则返回true

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
