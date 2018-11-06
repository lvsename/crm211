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
* @Description:科目管理模块
*/
@Controller
public class SubjectController {
    /**
     *  @Description 跳转科目管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/subjectMang.do",method = RequestMethod.GET)
    public String userManager(Model model){
      if(UserContext.getLoginUser()!=null){

          return JumpViewConstants.SYSTEM_SUBJECT;
      }
    return JumpViewConstants.SYSTEM_LOGIN;

    }



}
