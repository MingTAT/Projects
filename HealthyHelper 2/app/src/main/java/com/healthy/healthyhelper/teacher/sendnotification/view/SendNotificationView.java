package com.healthy.healthyhelper.teacher.sendnotification.view;

import com.healthy.healthyhelper.model.Group;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public interface SendNotificationView {

    /**
     *
     * 加载小组列表
     *
     * @param groups
     */
    void loadGroupList(List<Group> groups);

    /**
     *
     * 加载小组列表
     *
     * @param groups
     */
    void loadMoreGroupList(List<Group> groups);

    /**
     * 发送通知消息
     */
    void sendNotificationSucc();

    void loadError(String errorMsg);

}
