package com.xdlcrm.system.service.impl;

import com.xdlcrm.publics.dao.IDataAccess;
import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.system.entity.Course;
import com.xdlcrm.system.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServicImpl implements ICourseService {
    @Autowired
    IDataAccess<Course> courseDao;

    @Override
    public List<Course> queryCourse(PageBean pageBean) {

        List<Course> list=courseDao.queryByStatment("queryCourse",null,pageBean);

        return list;
    }
}
