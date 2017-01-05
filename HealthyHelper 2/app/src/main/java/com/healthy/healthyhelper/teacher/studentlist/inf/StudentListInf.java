package com.healthy.healthyhelper.teacher.studentlist.inf;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface StudentListInf {

    void loadStudentList(StringCallback callback,String trainer);

}
