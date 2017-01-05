package com.healthy.healthyhelper.teacher.studentlist.view;

import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public interface StudentListView {

    /**
     * 首次加载或下拉刷新
     * 分页读取
     * @param students
     */
    void loadStudentList(List<StudentResponse.ResultBean> students);

    /**
     * 上拉加载
     */
    void loadMoreStudentList(List<StudentResponse.ResultBean> students);

    void loadError(String errorMsg);

}
