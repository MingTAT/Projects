package com.healthy.healthyhelper.student.apply.persenter;

import com.healthy.healthyhelper.student.apply.inf.ApplyForTeacherInf;
import com.healthy.healthyhelper.student.apply.inf.ApplyForTeacherInfImpl;
import com.healthy.healthyhelper.model.Teacher;
import com.healthy.healthyhelper.student.apply.view.ApplyForTeacherView;
import com.healthy.healthyhelper.util.StringUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyForTeacherPresenterImpl implements ApplyForTeacherPresenter {

    private ApplyForTeacherView applyForTeacherView;//视图层
    private ApplyForTeacherInf applyForTeacherInf;//model获取

    private static int START_PAGE = 1;

    public ApplyForTeacherPresenterImpl(ApplyForTeacherView applyForTeacherView) {
        this.applyForTeacherView = applyForTeacherView;
        applyForTeacherInf = new ApplyForTeacherInfImpl();
    }

    @Override
    public void initTeacherList() {
        List<Teacher> list = new ArrayList<>();
        list.add(new Teacher("Abbott", "A"));
        list.add(new Teacher("Abraham", "B"));
        list.add(new Teacher("Alexander", "C"));
        list.add(new Teacher("Antoine", "D"));
        list.add(new Teacher("Atwood", "E"));
        list.add(new Teacher("Bartholomew", "F"));
        list.add(new Teacher("Benjamin", "G"));
        applyForTeacherView.loadTeacherList(list);
        /*applyForTeacherInf.getTeacherList(START_PAGE, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyForTeacherView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
                //这里是假数据  接口有了以后建议用 Gson 直接解析

            }
        });*/
    }

    @Override
    public void loadMoreTeacher(int page) {
        //这里是假数据  接口有了以后建议用 Gson 直接解析
        List<Teacher> list = new ArrayList<>();
        list.add(new Teacher("Abbott", "A"));
        list.add(new Teacher("Abraham", "B"));
        list.add(new Teacher("Alexander", "C"));
        list.add(new Teacher("Antoine", "D"));
        list.add(new Teacher("Atwood", "E"));
        list.add(new Teacher("Bartholomew", "F"));
        list.add(new Teacher("Benjamin", "G"));
        applyForTeacherView.loadMoreTeacher(list);
        //接口调用
       /* applyForTeacherInf.getTeacherList(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyForTeacherView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {

            }
        });*/
    }

    @Override
    public void applyForTeacher(final Teacher teacher, String userName) {
        applyForTeacherInf.applyForTeacher(teacher, userName, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyForTeacherView.loadError("request error,please check:" + e == null ? "null" : e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                //成功回调
                if (StringUtil.isEmpty(response)) {
                    applyForTeacherView.loadError("request failed,response is null");
                } else {
                    if ("Successfully requested".toLowerCase().equals(response.trim().toLowerCase())) {
                        //success
                        applyForTeacherView.applyForTeacher(teacher);
                    } else {
                        //fail
                        applyForTeacherView.loadError("request failed,response:" + response);
                    }
                }

            }
        });
       /* //成功回调
        applyForTeacherView.applyForTeacher(teacher);*/
    }

    @Override
    public void destory() {
        applyForTeacherView = null;
    }
}
