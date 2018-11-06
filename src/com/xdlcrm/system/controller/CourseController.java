package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.utils.BaseController;
import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Course;
import com.xdlcrm.system.service.ICourseService;
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
* @Description:课程管理模块
*/
@Controller
public class CourseController extends BaseController {
    @Autowired
    ICourseService courseService;
    /**
     *  @Description 跳转课程管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/courseMang.do",method = RequestMethod.GET)
    public String userManager(Model model){
      if(UserContext.getLoginUser()!=null){

          return JumpViewConstants.SYSTEM_COURSE;
      }
    return JumpViewConstants.SYSTEM_LOGIN;

    }

@RequestMapping(value = "/course/queryCourse.do",method = RequestMethod.GET)
public @ResponseBody String queryCourse(HttpServletRequest request, Integer currentPage, Integer pageSize){
List<Course> list =courseService.queryCourse(processPageBean(pageSize,currentPage));

return jsonToPage(list);
}
}
