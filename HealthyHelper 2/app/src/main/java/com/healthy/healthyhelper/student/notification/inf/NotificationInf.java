package com.healthy.healthyhelper.student.notification.inf;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface NotificationInf {

    /**
     * 首次加载或下拉刷新
     * 分页读取通知消息
     * @param page
     */
    void loadNotification(int page, StringCallback callback);


}
