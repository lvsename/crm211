package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.utils.BaseController;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Dept;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.service.IDeptService;
import com.xdlcrm.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
* @Description:部门管理模块
*/
@Controller
public class DeptController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    /**
     *  @Description 跳转部门管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/deptMang.do",method = RequestMethod.GET)
    public String userManager(Model model){
      if(UserContext.getLoginUser()!=null){

          return JumpViewConstants.SYSTEM_DEPT_MANAGE;
      }
    return JumpViewConstants.SYSTEM_LOGIN;

    }

  /*  @RequestMapping(value = "/dept/queryDept.do",method = RequestMethod.GET)
    public @ResponseBody String queryRoleInfo(HttpServletRequest request, Integer currentPage, Integer pageSize){

       Dept dept=userService.queryDeptInfo(processPageBean(pageSize,currentPage));

        return  jsonToPage(dept);
    }*/
    /**
    *  @Description 查询所有部门信息
    *  @Author yuangxing
    *  @Date 2018/9/30 20:28   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    @RequestMapping(value = "/dept/queryDept.do",method = RequestMethod.GET)
    public @ResponseBody String queryRoleInfo(HttpServletRequest request){

        List<Dept> list=deptService.queryDept();
        System.out.println(list);
 /*       if(list!=null && list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                Long deptid=list.get(i).getDeptid();

            }

        }
*/
        return  jsonToPage(list);
    }
    /**
    *  @Description  根据部门id查询对应的角色信息
    *  @Author yuangxing
    *  @Date 2018/9/30 20:52   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
@RequestMapping(value = "/role/queryRoleByDeptid.do",method = RequestMethod.GET)
    public @ResponseBody String queryRoleByDeptId(HttpServletRequest request,String deptid){

   List<Role> list= deptService.queryRoleByDeptId(deptid);

   return  jsonToPage(list);
    }
}
