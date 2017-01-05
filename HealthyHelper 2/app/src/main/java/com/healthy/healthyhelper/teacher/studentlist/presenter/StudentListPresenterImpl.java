package com.healthy.healthyhelper.teacher.studentlist.presenter;

import com.google.gson.Gson;
import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.healthy.healthyhelper.teacher.studentlist.StudentListFragment;
import com.healthy.healthyhelper.teacher.studentlist.inf.StudentListInf;
import com.healthy.healthyhelper.teacher.studentlist.inf.StudentListInfImpl;
import com.healthy.healthyhelper.teacher.studentlist.view.StudentListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class StudentListPresenterImpl implements StudentListPresenter{
    private StudentListView studentListView;
    private StudentListInf studentListInf;
    public StudentListPresenterImpl(StudentListView studentListView) {
        this.studentListView = studentListView;
        studentListInf = new StudentListInfImpl();
    }


    @Override
    public void loadStudentList(String trainer) {
       /* String[] students={"Abbott ","Abraham","Alexander ","Antoine","Atwood ","Bartholomew","Benjamin "};
        List<Student> studentList = new ArrayList<>();
        for (int i=0;i<students.length;i++){
            studentList.add(new Student(students[i],i+""));
        }
        studentListView.loadStudentList(studentList);*/
        studentListInf.loadStudentList(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                studentListView.loadError("request error"+e==null?"null":e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                StudentResponse studentResponse = new Gson().fromJson(response, StudentResponse.class);
                getStudentList("active",studentResponse.getResult());
                if (studentResponse!=null){
                    studentListView.loadStudentList(studentResponse.getResult());
                }else {
                    studentListView.loadError("response is error,please check:"+response);
                }
               // studentListView.loadStudentList();
            }
        },trainer);
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
    public void loadMoreStudentList(int page) {
        /*studentListInf.loadStudentList(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                studentListView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
               // view加载更多
            }
        });*/
    }

    @Override
    public void onDestory() {

    }
}
