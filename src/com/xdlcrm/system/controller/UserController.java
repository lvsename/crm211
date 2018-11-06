package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.constants.ReturnConstants;
import com.xdlcrm.publics.utils.BaseController;
import com.xdlcrm.publics.utils.MD5Tools;
import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.User;
import com.xdlcrm.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author:yuanxingwei
* @Date:2018/9/29 22:27
* @Description:用户管理模块
*/
@Controller
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    /**
     *  @Description 跳转用户管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/userMang.do",method = RequestMethod.GET)
    public String userManager(Model model){
      if(UserContext.getLoginUser()!=null){
          int i=1;
          int b=++i;
          int a=i++;
          System.out.println("i="+i+" a="+a+" b="+b);

          return JumpViewConstants.SYSTEM_USER_MANAGE;
      }
    return JumpViewConstants.SYSTEM_LOGIN;

    }

    /**
     *  @Description查询用户信息，支持分页查询
     *  @Author yuangxing
     *  @Date 2018/9/30 12:05   注释内容
     *  @Param [request, currentPage显示的页码, pageSize请求的数据个数]
     *  @Return java.lang.String
     *  @Exception
     */
@RequestMapping(value = "/system/userlist.do",method = RequestMethod.GET)
public @ResponseBody String queryUserInfo(HttpServletRequest request, Integer currentPage, Integer pageSize){

   /* PageBean pageBean=new PageBean();
    pageBean.setCurrentPage(currentPage);
    pageBean.setPageSize(pageSize);*///将pageBean的生成封装在了BaseController中，这个controller继承BaseController
     List<User> list=userService.queryUserInfo(processPageBean(pageSize,currentPage));

    return  jsonToPage(list);
}
@RequestMapping(value = "/system/saveOrUpdate.do",method = RequestMethod.POST)
public @ResponseBody String saveOrUpdateUser(HttpServletRequest request,User user){
    if(user!=null){
       userService.saveOrUpdateUser(user);
        return ReturnConstants.SUCCESS;
    }
        return  ReturnConstants.PARAM_NULL;


}
@RequestMapping(value = "/system/editPassword.do",method = RequestMethod.POST)
public @ResponseBody String updatePassword(String oldPassword, String newPassword, String userid) {

    if(StringUtils.isBlank(oldPassword)||StringUtils.isBlank(newPassword)||StringUtils.isBlank(userid)){
        return ReturnConstants.PARAM_NULL;
    }
    String data=userService.updatePassword(oldPassword,newPassword,userid);
    return  jsonToPage(data);

}



@RequestMapping(value ="/system/deleteUser.do",method = RequestMethod.POST)
public  @ResponseBody String deleteUserByIds(HttpServletRequest request,String ids){
    if(StringUtils.isNotBlank(ids)){
        userService.deleteUserByIds(ids);
        return ReturnConstants.SUCCESS;
    }
    return  ReturnConstants.PARAM_NULL;


    }
}
