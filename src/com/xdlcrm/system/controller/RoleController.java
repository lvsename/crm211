package com.xdlcrm.system.controller;

import com.xdlcrm.publics.constants.JumpViewConstants;
import com.xdlcrm.publics.constants.ReturnConstants;
import com.xdlcrm.publics.utils.BaseController;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.entity.Role_menu;
import com.xdlcrm.system.service.IRoleService;
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
* @Date:2018/9/29 22:43
* @Description:角色管理模块
*/
@Controller
public class RoleController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;
/**
*  @Description 跳转角色页面
*  @Author yuangxing
*  @Date 2018/9/29 22:43   注释内容
*  @Param
*  @Return
*  @Exception
*/
@RequestMapping(value = "/system/roleMang.do",method = RequestMethod.GET)
    public String roleManager(Model model){
        if(UserContext.getLoginUser()!=null){
            return JumpViewConstants.SYSTEM_ROLE_MANAGE;
        }
        return JumpViewConstants.SYSTEM_LOGIN;

    }

   @RequestMapping(value = "/role/queryAllRole.do",method = RequestMethod.GET)
    public @ResponseBody String queryRoleInfo(HttpServletRequest request, Integer currentPage, Integer pageSize){

       List<Role> list=userService.queryAllRole(processPageBean(pageSize,currentPage));

    return  jsonToPage(list);
    }
    /**
     *  @Description  1.查询所有的菜单信息
     *  2.查询所有的角色对应的菜单信息
     *  3.对比某个角色菜单和所有菜单，所有菜单包含某个角色菜单，就在对应的所有菜单menu对象属性select设为true
     *  @Author yuangxing
     *  @Date 2018/10/5 8:56   注释内容
     *  @Param [request, roleid]
     *  @Return java.lang.String
     *  @Exception
     */

    @RequestMapping(value = "/rolemenu/queryAllMenuAndSelected.do",method = RequestMethod.GET)
    public @ResponseBody String queryRoleMenu(HttpServletRequest request,String roleid){
        if (roleid != null) {
           //查询所有的菜单信息
               List<Menu> list=roleService.queryAllMenu();
            //根据roleid查询对应的菜单信息
             List<Menu> listselect=userService.queryMenuByRoleId(roleid);
            //返回菜单信息

            if(list!=null && list.size()>0){
                if(listselect!=null && listselect.size()>0) {
                    for (int i = 0; i < list.size(); i++) {
                        Long listid = list.get(i).getMenuid();
                        for (int j = 0; j < listselect.size(); j++) {
                            Long listselectid = listselect.get(j).getMenuid();//比对一级菜单
                            if (listid == listselectid){
                                List<Menu> listchildren=list.get(i).getChildren();
                                for(int l=0;l<listchildren.size();l++){
                                    Long listchildrenid=listchildren.get(l).getMenuid();
                                    for(int m=0;m<listselect.size();m++){
                                        Long listselectid1=listselect.get(m).getMenuid();
                                        if(listchildrenid==listselectid1){//比对二级菜单
                                            listchildren.get(l).setSelected(true);

                                        }
                                    }
                                }
                                list.get(i).setSelected(true);
                            break;

                        }
                    }
                }
                }

            }
            return jsonToPage(list);

        }
        return ReturnConstants.PARAM_NULL;


    }
    /**
     *  @Description  权限分配—保存功能
     *  @Author yuangxing
     *  @Date 2018/10/5 13:56   注释内容
     *  @Param [request, roleid 角色id, menuid 角色id对应的菜单id，多个用字符串表示]
     *  @Return java.lang.String
     *  @Exception
     */
@RequestMapping(value = "/rolemenu/assignMenu.do",method = RequestMethod.POST)
public @ResponseBody String asignMenu(HttpServletRequest request,String roleid,String menuid){
if(StringUtils.isNotBlank(roleid)){
    //操作的是role_menu表
    //先删除role_menu的逻辑关系
roleService.deleteRoleMenu(roleid);
    //保存一份最新的role_menu关系
    Role_menu rm=new Role_menu();
    rm.setRoleid(Long.valueOf(roleid));
    String[] menuids=menuid.split(",");
    for(String mid:menuids){
        rm.setMenuid(Long.valueOf(mid));
        roleService.savRoleMenu(rm);
        rm.setMenuid(null);
    }

return  ReturnConstants.SUCCESS;
}

return  ReturnConstants.PARAM_NULL;
    }
/**
*  @Description  修改或者增加角色信息
*  @Author yuangxing
*  @Date 2018/10/6 11:32   注释内容
*  @Param   role 角色对象
*  @Return
*  @Exception
*/
    @RequestMapping(value = "/role/addOrUpdateRole.do",method = RequestMethod.POST)
    public @ResponseBody String addOrUpdateRole(HttpServletRequest request,Role role){
if(role!=null) {
   roleService.addOrUpdateRole(role);
   return ReturnConstants.SUCCESS;
}
return  ReturnConstants.PARAM_NULL;
    }
  /**
  *  @Description 根据id删除角色信息
  *  @Author yuangxing
  *  @Date 2018/10/6 11:31   注释内容
  *  @Param
  *  @Return
  *  @Exception
  */
    @RequestMapping(value = "/role/deleteRole.do",method = RequestMethod.POST)
    public @ResponseBody String deleteRole(HttpServletRequest request,String ids){
      if(StringUtils.isNotBlank(ids)){
          roleService.deleteRoleByIds(ids);
          return ReturnConstants.SUCCESS;
      }
     return ReturnConstants.PARAM_NULL;
    }
}
