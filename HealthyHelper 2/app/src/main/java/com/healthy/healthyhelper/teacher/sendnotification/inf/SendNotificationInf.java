package com.healthy.healthyhelper.teacher.sendnotification.inf;

import com.healthy.healthyhelper.model.Group;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface SendNotificationInf {
    /**
     * 加载小组列表
     *
     * @param page
     * @param stringCallback
     */
    void loadGroupList(int page, StringCallback stringCallback);

    /**
     *
     * @param userName
     * @param content
     * @param stringCallback
     */
    void sendNotification(String userName,String content,StringCallback stringCallback);
}
