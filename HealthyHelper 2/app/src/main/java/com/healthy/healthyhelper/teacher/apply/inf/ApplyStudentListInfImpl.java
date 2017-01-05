package com.healthy.healthyhelper.teacher.apply.inf;

import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyStudentListInfImpl implements ApplyStudentListInf {
    @Override
    public void loadStudentList(String trainer,StringCallback callback) {
        OkHttpUtils.get().url("http://proj-309-05.cs.iastate.edu/TshowStuList.php").addParams("trainer",trainer).build().execute(callback);
    }

    @Override
    public void auditPass(StudentResponse.ResultBean student,StringCallback callback) {
        OkHttpUtils.post().url("http://proj-309-05.cs.iastate.edu/tAcceptS.php").addParams("username",student.getStudent()).build().execute(callback);
    }

    @Override
    public void auditReject(StudentResponse.ResultBean student,StringCallback callback) {
    }
}
