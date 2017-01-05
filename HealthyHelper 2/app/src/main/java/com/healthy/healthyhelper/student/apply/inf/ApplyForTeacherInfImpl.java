package com.healthy.healthyhelper.student.apply.inf;

import com.healthy.healthyhelper.model.Teacher;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyForTeacherInfImpl implements ApplyForTeacherInf  {
    @Override
    public void getTeacherList(int page, StringCallback callback) {

        //OkHttpUtils.post().url("接口url").addParams("参数key","参数value").build().execute(callback);
        //在这里请求接口数据  目前是写的假数据n list;
    }

    @Override
    public void applyForTeacher(Teacher teacher,String userName, StringCallback callback) {
        OkHttpUtils.post().url("http://proj-309-05.cs.iastate.edu/sReqT.php")
                .addParams("trainer",teacher.getName())
                .addParams("grp_name",teacher.getGrp_name())
                .addParams("username",userName)
                .build().execute(callback);
        //在这里请求接口数据  目前是写的假数据n list;
    }
}
