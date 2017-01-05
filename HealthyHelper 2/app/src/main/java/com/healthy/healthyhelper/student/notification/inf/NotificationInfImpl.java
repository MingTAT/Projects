package com.healthy.healthyhelper.student.notification.inf;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public class NotificationInfImpl implements NotificationInf {
    @Override
    public void loadNotification(int page, StringCallback callback) {
        OkHttpUtils.get().url("http://proj-309-05.cs.iastate.edu/postFeed.php").addParams("page",page+"").build().execute(callback);
    }
}
