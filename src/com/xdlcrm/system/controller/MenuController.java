package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.constants.ReturnConstants;
import com.xdlcrm.publics.utils.BaseController;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.service.IMenuService;
import com.xdlcrm.system.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:yuanxingwei
 * @Date:2018/9/29 22:27
 * @Description:菜单管理模块
 */
@Controller
public class MenuController extends BaseController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IMenuService menuService;


    /**
     *  @Description 跳转菜单管理页面
     *  @Author yuangxing
     *  @Date 2018/9/29 22:26   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    @RequestMapping(value = "/system/menuMang.do",method = RequestMethod.GET)
    public String menuManager(Model model){
        if(UserContext.getLoginUser()!=null){

            return JumpViewConstants.SYSTEM_MENU_MANAGE;
        }
        return JumpViewConstants.SYSTEM_LOGIN;

    }
@RequestMapping(value = "/menu/queryAllMenu.do",method = RequestMethod.GET)
  public @ResponseBody String queryAllMenu(HttpServletRequest request){
        //查询所有的菜单信息
    List<Menu> list =roleService.queryAllMenu();

        return  jsonToPage(list);
  }

  @RequestMapping(value = "/menu/addOrUpdateMenu.do",method = RequestMethod.POST)
  public @ResponseBody String addOrUpdateMenu(HttpServletRequest request,Menu menu){
        if(menu!=null){
                menuService.addOrUpdateMenu(menu);
                return ReturnConstants.SUCCESS;
        }
        return ReturnConstants.PARAM_NULL;
  }
}
