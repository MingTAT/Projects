package com.healthy.healthyhelper.student.apply.persenter;

import com.healthy.healthyhelper.model.Teacher;

/**
 * Created by Ming on 2016/4/3.
 */
public interface ApplyForTeacherPresenter {

    /**
     * 初始化/刷新
     */
    void initTeacherList();

    /**
     * 加载更多
     */
    void loadMoreTeacher(int page);

    /**
     *
     * 申请老师
     *
     * @param teacher  申请的老师
     * @param userName 申请学生的姓名
     */
    void applyForTeacher(Teacher teacher,String userName);

    /**
     * 销毁  在此资源释放
     */
    void destory();

}
