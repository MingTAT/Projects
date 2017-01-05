package com.healthy.healthyhelper.teacher.apply.inf;

import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface ApplyStudentListInf {

    /**
     * 用于初始化或者下拉刷新
     * 加载申请学生列表
     *
     * @param trainer
     * @param callback
     */
    void loadStudentList(String trainer, StringCallback callback);
    /**
     * 审核通过
     */
    void auditPass(StudentResponse.ResultBean student,StringCallback callback);

    /**
     * 审核拒绝
     * @param student
     */
    void auditReject(StudentResponse.ResultBean student,StringCallback callback);
}
