package com.healthy.healthyhelper.teacher.sendnotification.presenter;

import com.healthy.healthyhelper.model.Group;

/**
 * Created by Ming on 2016/4/3.
 */
public interface SendNotificationPresenter {

    /**
     * 下拉刷新或首次加载
     * 加载小组列表
     *
     * @param page
     */
    void loadGroupList(int page);

    /**
     * 加载更多小组列表
     *
     * @param page
     */
    void loadMoreGroupList(int page);

    /**
     * 发送公告
     *
     * @param userName
     * @param content
     */
    void sendNotificationg(String userName,String content);

}
