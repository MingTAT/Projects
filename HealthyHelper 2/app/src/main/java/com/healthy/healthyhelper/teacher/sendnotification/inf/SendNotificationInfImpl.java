package com.healthy.healthyhelper.teacher.sendnotification.inf;

import com.healthy.healthyhelper.model.Group;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ming on 2016/4/3.
 */
public class SendNotificationInfImpl implements SendNotificationInf {
    @Override
    public void loadGroupList(int page, StringCallback stringCallback) {
        OkHttpUtils.post().url("").addParams("","").build().execute(stringCallback);
    }

    @Override
    public void sendNotification(String userName, String content,StringCallback stringCallback) {
        OkHttpUtils.post().url("http://proj-309-05.cs.iastate.edu/postUpload.php")
                .addParams("username",userName)
                .addParams("text",content)
                .addParams("time", DateFormat.getDateTimeInstance().format(new Date()).toString().trim())
                .build().execute(stringCallback);
    }
}
