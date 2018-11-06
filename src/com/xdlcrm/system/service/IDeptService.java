package com.xdlcrm.system.service;

import com.xdlcrm.system.entity.Dept;
import com.xdlcrm.system.entity.Role;

import java.util.List;

public interface IDeptService {
    /**
    *  @Description 查询所有的部门信息
    *  @Author yuangxing
    *  @Date 2018/9/30 20:14   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public List<Dept> queryDept();
    /**
    *  @Description  根据部门id查询角色信息
    *  @Author yuangxing
    *  @Date 2018/9/30 23:10   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public List<Role> queryRoleByDeptId(String deptId);

}
