package com.healthy.healthyhelper.student.apply.view;

import com.healthy.healthyhelper.model.Teacher;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public interface ApplyForTeacherView {

    /**
     * 加载教师列表
     *
     * @return
     */
    void loadTeacherList(List<Teacher> list);

    /**
     * 申请某个老师
     */
    void applyForTeacher(Teacher teacher);

    /**
     * 加载更多
     *
     * @param list
     */
    void loadMoreTeacher(List<Teacher> list);

    /**
     * 加载失败
     *
     * @param errorMsg
     */
    void loadError(String errorMsg);

}
