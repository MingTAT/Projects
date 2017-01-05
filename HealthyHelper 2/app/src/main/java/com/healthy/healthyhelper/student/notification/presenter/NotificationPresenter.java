package com.healthy.healthyhelper.student.notification.presenter;

/**
 * Created by Ming on 2016/4/3.
 */
public interface NotificationPresenter {

    /**
     * 首次加载或下拉刷新
     * 分页读取通知消息
     * @param page
     */
    void loadNotification(int page);

    /**
     * 上拉加载
     * 分页读取通知消息
     * @param page
     */
    void loadMoreNotification(int page);

    void onDestory();


}
