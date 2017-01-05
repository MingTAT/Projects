package com.healthy.healthyhelper.teacher.apply.presenter;

import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public interface ApplyStudentPersenter {
    /**
     * 用于初始化或者下拉刷新
     * 加载申请学生列表
     *
     * @param trainer
     */
    void loadStudentList(String trainer);

    /**
     * 用于上拉加载更多
     * 加载学生列表
     * @param trainer
     */
    void loadMoreStudentList(String trainer);

    /**
     * 审核通过
     */
    void auditPass(StudentResponse.ResultBean student);

    /**
     * 审核拒绝
     * @param student
     */
    void auditReject(StudentResponse.ResultBean student);

    /**
     *
     * 用于页面处理接口错误加载
     *
     * @param errorMsg
     */
    void loadError(String errorMsg);

    /**
     * 资源回收
     */
    void destory();

}
