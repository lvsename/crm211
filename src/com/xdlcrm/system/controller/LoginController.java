package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.constants.ReturnConstants;
import com.xdlcrm.publics.utils.ContextUtil;
import com.xdlcrm.publics.utils.MD5Tools;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.User;
import com.xdlcrm.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author:yuanxingwei
* @Date:2018/9/26 23:28
* @Description:登陆页面控制器
*/
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    private static final String COOKIE_KEY = "auth_key";
    private static final String COOKIE_SPI = "_#_";

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String loginManager(Model model) {
        //判断是否登陆过了
        if (UserContext.getLoginUser() != null) {
           // return JumpViewConstants.SYSTEM_INDEX;
            return "redirect:/main.do";
        }
        return JumpViewConstants.SYSTEM_LOGIN;

    }

    /**
     * @Description 登录功能
     * @Author likang
     * @Date 2018/9/26 15:46
     * @Param [email 用户名, password 密码, sign 特殊标识]
     * @Return java.lang.String
     * @Exception
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, String email, String password, String sign, Model model) {

//        if (email != null && !"".equals(email)){""
//        }
        //isNotBlank：判断str即不等于null，又不等于""
        //isNotEntity：只会判断str不等于null
        if (StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)) {
            //判断用户名是否存在
            email = email + ContextUtil.getInitConfig("email_suffix");
            User user = userService.queryUserByEmail(email);
            if (user == null) {//用户不存在
                model.addAttribute("msg", ReturnConstants.USER_NOT_EXIST);
                return JumpViewConstants.SYSTEM_LOGIN;
            }
            //判断密码是否匹配
            if (!userService.isExisPasswordByuserId(password, String.valueOf(user.getUserid()))) {
                model.addAttribute("msg", ReturnConstants.PASSWORD_ERROR);
                return JumpViewConstants.SYSTEM_LOGIN;
            }
//处理session
            UserContext.setLoginUser(user);
            request.getSession(true).setAttribute("loginName", user.getUsername());
            request.getSession(true).setAttribute("ischange", user.getIschange());

            //处理cookie---防止客户端禁用cookie
            //需要在服务器端单独存放一份cookie
            Cookie cookie = new Cookie(COOKIE_KEY, MD5Tools.encode(user.getPhone()) + COOKIE_SPI + user.getEmail());
            cookie.setPath("/");//设置路径
            cookie.setMaxAge(-1);// 小于0： cookie数据立即生效，并且生命周期和服务器的生命周期一致
            response.addCookie(cookie);

            //处理cookie

            //跳转登录成功页面
           // return JumpViewConstants.SYSTEM_INDEX;
            return "redirect:/main.do";
        }
        return ReturnConstants.PARAM_NULL;//接收参数为空


    }
@RequestMapping(value ="/logout.do",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //清除session
        UserContext.clearLoginUser();
        //清除服务器中的cookie
        Cookie cookie = new Cookie(COOKIE_KEY, null);
         cookie.setPath("/");
         cookie.setMaxAge(0);
         response.addCookie(cookie);
         //清除客户端中的cookie
        Cookie cookieC = new Cookie("JESESSIONID", null);
           cookieC.setPath(request.getContextPath());
           cookieC.setMaxAge(0);
           response.addCookie(cookieC);
           return  "redirect:/login.do";
}
@RequestMapping(value ="/main.do" ,method = RequestMethod.GET)
public String indexManager(Model model){

      if(UserContext.getLoginUser()!=null){
         Long roleid= UserContext.getLoginUser().getRoleid();
          System.out.println("roleid: " + UserContext.getLoginUser().getRoleid());
          List<Menu> list =userService.queryMenusByRoleId(String.valueOf(UserContext.getLoginUser().getRoleid()));
          System.out.println(list);
          model.addAttribute("menus",list);
          return JumpViewConstants.SYSTEM_INDEX;
      }
    return JumpViewConstants.SYSTEM_LOGIN;


}
}
