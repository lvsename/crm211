package com.xdlcrm.system.service.impl;

import com.xdlcrm.publics.dao.IDataAccess;
import com.xdlcrm.system.entity.Menu;
import com.xdlcrm.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements IMenuService {

    @Autowired
    IDataAccess<Menu> menuDao;
    @Override
    public void addOrUpdateMenu(Menu menu) {

        if(menu!=null){
            if(menu.getMenuid()!=null){
                //修改操作
                menuDao.update(menu);
            }
            menuDao.insert(menu);

        }
    }
}
