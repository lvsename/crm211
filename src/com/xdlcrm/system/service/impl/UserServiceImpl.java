package com.xdlcrm.system.service.impl;

import com.xdlcrm.publics.constants.ReturnConstants;
import com.xdlcrm.publics.dao.IDataAccess;
import com.xdlcrm.publics.utils.MD5Tools;
import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.publics.utils.UserContext;
import com.xdlcrm.system.entity.Dept;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.entity.User;
import com.xdlcrm.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {
    @Autowired
    IDataAccess<User> userDao;
    @Autowired
    IDataAccess<Menu> menuDao;
    @Autowired
    IDataAccess<Role> roleDao;
    @Autowired
    IDataAccess<Dept> deptDao;

    @Override
    public void saveOrUpdateUser(User user) {
        if(user!=null){
            if(user.getUserid()!=null){//修改id存在
                user.setUpdate_id(UserContext.getLoginUser().getUserid());
                user.setUpdate_time(new Timestamp(System.currentTimeMillis()));
                userDao.update(user);

            }else {//保存id不存在，由数据库的序列生成
                user.setCreate_id(UserContext.getLoginUser().getUserid());
                user.setUpdate_time(new Timestamp(System.currentTimeMillis()));
                userDao.insert(user);
            }

        }


    }

    @Override
    public Dept queryDeptInfo(PageBean pageBean) {
        long ldeptid=UserContext.getLoginUser().getDeptid();
        String deptid=String.valueOf(ldeptid);
        Map<String,Object> params=new HashMap<>();
        params.put("deptid",deptid);
        List<Dept> list=deptDao.queryByStatment("queryDeptInfo",params,pageBean);
        if(list!=null){
            Dept dept=list.get(0);
           return dept;

        }
        return null;
    }

    @Override
    public List<Role> queryAllRole(PageBean pageBean) {
       /* long lroleid=UserContext.getLoginUser().getRoleid();
        String roleid=String.valueOf(lroleid);
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("roleid",roleid);*/
        List<Role> list=roleDao.queryByStatment("queryAllRole",null,pageBean);
        return list;
    }

    @Override
    public List<User> queryUserInfo(PageBean pageBean) {

        List<User> list= userDao.queryByStatment("queryUserInfo",null,pageBean);

        return list;
    }

    @Override
    public List<Menu> queryMenusByRoleId(String roleId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("roleid",roleId);
        params.put("parid","true");
        //先根据角色id查询对应的一级菜单
            List<Menu> list =menuDao.queryByStatment("queryMenusByRoleId",params,null);
            //再根据一级菜单的id查询对应的下一级菜单
        if(list!=null && list.size()>0){
            params.put("parid",null);
            Long mid;
            for(int i=0;i<list.size();i++){//循环所有的一级菜单
               mid=list.get(i).getMenuid();
                params.put("mid",mid);
                List<Menu> list1=menuDao.queryByStatment("queryMenusByRoleId",params,null);
                list.get(i).setChildren(list1);
                mid=null;
            }
        }
        return list;
    }


    @Override
    public List<Menu> queryMenuByRoleId(String roleId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("roleid",roleId);
        //先根据角色id查询对应的一级菜单
        List<Menu> list =menuDao.queryByStatment("queryMenuByRoleId",params,null);
        //再根据一级菜单的id查询对应的下一级菜单
        return list;

    }

    @Override
    public void deleteUserByIds(String ids) {
       /* List<String> list=new ArrayList<>();
        for(String id:ids.split(",")){
            list.add(id);
        }*/
       /* Map<String,Object> params=new HashMap<>();
        params.put("list",list);*/
       /* userDao.deleteByStatment("deleteUserByIds",list);*/
     userDao.deleteByIds(User.class,ids);


    }

    @Override
    public String updatePassword(String oldPassword, String newPassword, String userid) {
        if(StringUtils.isBlank(oldPassword)||StringUtils.isBlank(newPassword)||StringUtils.isBlank(userid)){
            return ReturnConstants.PARAM_NULL;
        }
        if(!UserContext.getLoginUser().getUserid().toString().equals(userid)){
            return ReturnConstants.USER_NOT_EXIST;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("userid",userid);
        List<User> list=userDao.queryByStatment("queryUserByUserid",params,null);
        if(list!=null&&list.size()>0){
            User user=list.get(0);
            String md5OldPassword=MD5Tools.encode(oldPassword);
            if(!user.getPassword().equals(md5OldPassword)){
                return ReturnConstants.PASSWORD_ERROR;
            }
            String md5NewPassword=MD5Tools.encode(newPassword);
            user.setPassword(md5NewPassword);
            userDao.update(user);
            return ReturnConstants.SUCCESS;
        }

        return  null;
    }





    @Override
    public boolean isExisPasswordByuserId(String password, String userid) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("userid",userid);
        params.put("password",MD5Tools.encode(password));
        List<User> list = userDao.queryByStatment("isExisPasswordByuserId",params,null);
        if (list != null && list.size() > 0){
            return true;
        }
        return false;

    }


    @Override
    public User queryUserByEmail(String email) {
        List<User> list = userDao.queryByStatment("queryUserByEmail",email,null);
        if (list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
