package com.xdlcrm.system.service.impl;
import com.xdlcrm.publics.dao.IDataAccess;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.entity.Role_menu;
import com.xdlcrm.system.service.IRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements IRoleService {
    @Autowired
 IDataAccess<Menu> menuDao;

    @Autowired
    IDataAccess<Role_menu>  role_menuDao;
    @Autowired
    IDataAccess<Role> roleDao;

    @Override
    public List<Menu> queryAllMenu() {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("parid","true");
      //只查询一级菜单
        List<Menu> list=menuDao.queryByStatment("queryAllMenu",params,null);
      //遍历一级菜单，查询对应的二级菜单
     if(list!=null && list.size()>0){
         Long mid;
         params.put("parid",null);
         for(int i=0;i<list.size();i++){
             mid=list.get(i).getMenuid();
             params.put("mid",mid);
             List<Menu> list1=menuDao.queryByStatment("queryAllMenu",params,null);
             list.get(i).setChildren(list1);
         }
         return list;
     }


        return null;
    }


    @Override
    public void deleteRoleMenu(String roleid) {
        if(StringUtils.isNotBlank(roleid)){
            role_menuDao.deleteByStatment("deleteRoleMenu",roleid);
        }


    }


    @Override
    public void savRoleMenu(Role_menu role_menu) {
        if(role_menu!=null){
            role_menuDao.insert(role_menu);

        }
    }

    @Override
    public void addOrUpdateRole(Role role) {
        if(role!=null) {
            if (role.getRoleid() != null) {
                //id不为空，就修改操作
              roleDao.update(role);
            }
            //为空就增加操作
             roleDao.insert(role);

        }

    }

    @Override
    public void deleteRoleByIds(String ids) {
        if(StringUtils.isNotBlank(ids)){
            roleDao.deleteByIds(Role.class,ids);

        }

    }
}
