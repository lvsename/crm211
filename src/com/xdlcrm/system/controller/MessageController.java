package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.utils.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* @author:yuanxingwei
* @Date:2018/9/29 22:27
* @Description:信息管理模块
*/
@Controller
public class MessageController {
    /**
     *  @Description 跳转信息管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/systemMessage.do",method = RequestMethod.GET)
    public String userManager(Model model){
      if(UserContext.getLoginUser()!=null){

          return JumpViewConstants.SYSTEM_MESSAGE;
      }
    return JumpViewConstants.SYSTEM_LOGIN;

    }



}
