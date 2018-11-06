package com.xdlcrm.system.service;

import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.entity.Role_menu;

import java.util.List;

public interface IRoleService {
    /**
    *  @Description 查询所有的菜单信息，等级展示
    *  @Author yuangxing
    *  @Date 2018/10/5 9:07   注释内容
    *  @Param 
    *  @Return 
    *  @Exception 
    */
    public List<Menu> queryAllMenu();
     /**
     *  @Description  根据roleid删除role_menu关系
     *  @Author yuangxing
     *  @Date 2018/10/5 14:03   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    public void deleteRoleMenu(String roleid);
    /**
    *  @Description  保存对应的角色菜单关系
    *  @Author yuangxing
    *  @Date 2018/10/5 14:15   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public void savRoleMenu(Role_menu role_menu);

    /**
    *  @Description  增加或修改角色信息
    *  @Author yuangxing
    *  @Date 2018/10/6 10:55   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public void addOrUpdateRole(Role role);

    /**
    *  @Description  根据id删除角色信息
    *  @Author yuangxing
    *  @Date 2018/10/6 11:38   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public void deleteRoleByIds(String ids);
}
