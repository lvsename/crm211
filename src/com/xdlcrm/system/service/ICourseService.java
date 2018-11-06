package com.xdlcrm.system.service;

import com.xdlcrm.publics.utils.PageBean;
import com.xdlcrm.system.entity.Course;

import java.util.List;

public interface ICourseService {

    /**
    *  @Description  查询所有的课程信息
    *  @Author yuangxing
    *  @Date 2018/10/5 20:31   注释内容
    *  @Param
    *  @Return
    *  @Exception
    */
    public List<Course> queryCourse(PageBean pageBean);

}
