package com.xdlcrm.system.service;

import com.xdlcrm.system.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public interface IMenuService {
    /**
     *  @Description  增加或修改菜单信息
     *  @Author yuangxing
     *  @Date 2018/10/6 11:55   注释内容
     *  @Param
     *  @Return
     *  @Exception
     */
    public void addOrUpdateMenu(Menu menu);

}
