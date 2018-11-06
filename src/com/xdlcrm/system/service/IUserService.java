package com.xdlcrm.system.service;

import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.system.entity.Dept;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.entity.User;
import org.apache.commons.mail.Email;

import java.util.List;

public interface IUserService {
/**
*  @Description  根据邮箱名称查询用户
*  @Author yuangxing
*  @Date 2018/9/27 0:19   注释内容
*  @Param 
*  @Return 
*  @Exception 
*/
   public User queryUserByEmail(String email);
   /**
    * @Description 判断密码是否匹配
    * @Author  likang
    * @Date   2018/9/26 16:55
    * @Param
    * @Return
    * @Exception
    */
   public boolean isExisPasswordByuserId(String password,String userid);
   /**
   *  @Description 根据当前用户角色id，查询对应的菜单信息,分级展示
   *  @Author yuangxing
   *  @Date 2018/9/28 16:24   注释内容
   *  @Param  roleId 角色id
   *  @Return 
   *  @Exception 
   */
  public List<Menu> queryMenusByRoleId(String roleId);
    /**
     *  @Description 根据当前用户角色id，查询对应的菜单信息,不分级展示
     *  @Author yuangxing
     *  @Date 2018/9/28 16:24   注释内容
     *  @Param  roleId 角色id
     *  @Return
     *  @Exception
     */

    public List<Menu> queryMenuByRoleId(String roleId);

    /**
     *  @Description 查询用户信息，支持分页查询
     *  @Author yuangxing
     *  @Date 2018/9/30 12:18   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
  public List<User> queryUserInfo(PageBean pageBean);
/**
*  @Description 查询用户的角色部门和菜单信息
*  @Author yuangxing
*  @Date 2018/9/30 14:42   注释内容
*  @Param
*  @Return
*  @Exception
*/
public List<Role> queryAllRole(PageBean pageBean);
/**
*  @Description  查询用户对应的部门信息
*  @Author yuangxing
*  @Date 2018/9/30 18:02   注释内容
*  @Param
*  @Return
*  @Exception
*/
public Dept queryDeptInfo(PageBean pageBean);
/**
*  @Description 增加或修改用户
*  @Author yuangxing
*  @Date 2018/9/30 21:43   注释内容
*  @Param
*  @Return
*  @Exception
*/
public void saveOrUpdateUser(User user);
/**
*  @Description 修改密码
*  @Author yuangxing
*  @Date 2018/9/30 23:14   注释内容
*  @Param
*  @Return
*  @Exception
*/
public String updatePassword(String oldPassword,String newPassword,String userid);
/**
*  @Description  根据id删除用户，支持批量删除
*  @Author yuangxing
*  @Date 2018/10/3 13:30   注释内容
*  @Param ids 主键id
*  @Return
*  @Exception
*/
public void deleteUserByIds(String ids);
}
