package com.healthy.healthyhelper.teacher.apply.presenter;

import com.google.gson.Gson;
import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.healthy.healthyhelper.teacher.apply.inf.ApplyStudentListInf;
import com.healthy.healthyhelper.teacher.apply.inf.ApplyStudentListInfImpl;
import com.healthy.healthyhelper.teacher.apply.view.ApplyStudentListView;
import com.healthy.healthyhelper.util.StringUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyStudentPersenterImpl implements ApplyStudentPersenter {

    private ApplyStudentListView applyStudentListView;
    private ApplyStudentListInf applyStudentListInf;


    public ApplyStudentPersenterImpl(ApplyStudentListView applyStudentListView) {
        this.applyStudentListView = applyStudentListView;
        applyStudentListInf = new ApplyStudentListInfImpl();
    }

    @Override
    public void loadStudentList(final String trainer) {
        applyStudentListInf.loadStudentList(trainer, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyStudentListView.loadError("request error:"+e==null?"":e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                try{

                    if (StringUtil.isEmpty(response)){
                        applyStudentListView.loadError("response error,please check:"+response);
                    }else {
                        StudentResponse studentResponse = new Gson().fromJson(response,StudentResponse.class);
                        getStudentList("pending",studentResponse.getResult());
                        applyStudentListView.loadStudentList(studentResponse.getResult());
                    }
                }catch (Exception e){
                    applyStudentListView.loadError("json parse error , please check:"+response);
                }
            }
        });
    }
    /**
     *
     * 筛选需要的数据
     *
     * @param type
     * @param mDatas
     */
    private void getStudentList(String type,List<StudentResponse.ResultBean> mDatas)
    {
        List<StudentResponse.ResultBean> returnList = new ArrayList<>();
        if (mDatas!=null && mDatas.size()>0){
            for (StudentResponse.ResultBean resultBean :mDatas){
                if (type.equals(resultBean.getStatus())){
                    returnList.add(resultBean);
                }
            }
        }
        mDatas.removeAll(returnList);
    }

    @Override
    public void loadMoreStudentList(String trainer) {
       /* String[] students={"Abbott ","Abraham","Alexander ","Antoine","Atwood ","Bartholomew","Benjamin "};
        List<Student> list = new ArrayList<>();
        for (int i=0;i<7;i++){
            list.add(new Student(students[i],i+""));
        }
        applyStudentListView.loadMoreStudentList(list);*/
        /*applyStudentListInf.loadStudentList(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyStudentListView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
                //applyStudentListView.loadMoreStudentList();
            }
        });*/
    }

    @Override
    public void auditPass(final StudentResponse.ResultBean student) {
       // applyStudentListView.auditPass(student);
        /*applyStudentListInf.auditPass(student, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyStudentListView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
                applyStudentListView.auditPass(student);
            }
        });*/
    }

    @Override
    public void auditReject(final StudentResponse.ResultBean student) {
        applyStudentListView.auditReject(student);
        applyStudentListInf.auditReject(student, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                applyStudentListView.loadError("request error");
            }

            @Override
            public void onResponse(String response) {
                applyStudentListView.auditReject(student);
            }
        });
    }

    @Override
    public void loadError(String errorMsg) {

    }

    @Override
    public void destory() {
        applyStudentListView = null;
    }
}
