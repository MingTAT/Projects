package com.healthy.healthyhelper.student.notification.view;

import com.healthy.healthyhelper.model.Announcement;
import com.healthy.healthyhelper.model.Notification;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public interface NotificationView {

    /**
     * 首次加载或下拉刷新
     * 分页读取通知消息
     * @param notifications
     */
    void loadNotification(List<Announcement>  notifications);

    /**
     * 上拉加载通知消息
     * @param notifications
     */
    void loadMoreNotification(List<Announcement> notifications);

    void loadError(String errorMsg);

}
