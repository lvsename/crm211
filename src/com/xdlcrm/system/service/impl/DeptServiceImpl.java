package com.xdlcrm.system.service.impl;

import com.xdlcrm.publics.dao.IDataAccess;
import com.xdlcrm.system.entity.Dept;
import com.xdlcrm.system.entity.Role;
import com.xdlcrm.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private IDataAccess<Dept> deptDao;

    @Override
    public List<Role> queryRoleByDeptId(String deptId) {
        Map<String,Object> params=new HashMap<>();
        params.put("deptid",deptId);
        List<Role> list=deptDao.queryByStatment("queryRoleByDeptId",params,null);


        return list;
    }

    @Override
    public List<Dept> queryDept() {
        List<Dept> list=deptDao.queryByStatment("queryDept",null,null);
        return list;
    }
}
