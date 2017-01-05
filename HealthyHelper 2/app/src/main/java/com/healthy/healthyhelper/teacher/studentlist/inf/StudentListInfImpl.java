package com.healthy.healthyhelper.teacher.studentlist.inf;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public class StudentListInfImpl implements StudentListInf {

    @Override
    public void loadStudentList( StringCallback callback,String trainer) {
        OkHttpUtils.get().url("http://proj-309-05.cs.iastate.edu/TshowStuList.php").addParams("trainer",trainer).build().execute(callback);
    }
}
