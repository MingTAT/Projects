package com.healthy.healthyhelper.teacher.studentlist.presenter;

/**
 * Created by Ming on 2016/4/3.
 */
public interface StudentListPresenter {

    /**
     * 首次加载或下拉刷新
     *
     * @param trainer
     */
    void loadStudentList(String trainer);

    /**
     * 上拉加载
     * 分页读取通知消息
     * @param page
     */
    void loadMoreStudentList(int page);

    void onDestory();

}
