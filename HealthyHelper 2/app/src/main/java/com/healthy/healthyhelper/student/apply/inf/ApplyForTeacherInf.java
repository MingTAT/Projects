package com.healthy.healthyhelper.student.apply.inf;

import com.healthy.healthyhelper.model.Teacher;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface ApplyForTeacherInf {
    /**
     * 获取教师列表
     */
    void getTeacherList(int page, StringCallback callback);

    /**
     *
     * 申请老师课程
     *
     * @param teacher
     * @param callback
     */
    void applyForTeacher(Teacher teacher,String userName,StringCallback callback);
}
