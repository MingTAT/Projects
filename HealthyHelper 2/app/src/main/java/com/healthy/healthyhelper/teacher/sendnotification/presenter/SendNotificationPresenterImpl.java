package com.healthy.healthyhelper.teacher.sendnotification.presenter;

import com.healthy.healthyhelper.model.Group;
import com.healthy.healthyhelper.teacher.sendnotification.SendNotificationFragment;
import com.healthy.healthyhelper.teacher.sendnotification.inf.SendNotificationInf;
import com.healthy.healthyhelper.teacher.sendnotification.inf.SendNotificationInfImpl;
import com.healthy.healthyhelper.teacher.sendnotification.view.SendNotificationView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class SendNotificationPresenterImpl implements SendNotificationPresenter {
    private SendNotificationView sendNotificationView;
    private SendNotificationInf sendNotificationInf;
    public SendNotificationPresenterImpl(SendNotificationView sendNotificationView) {
        this.sendNotificationView = sendNotificationView;
        sendNotificationInf = new SendNotificationInfImpl();
    }

    @Override
    public void loadGroupList(int page) {
        String [] groupArr = {"小组1","小组2","小组3","小组4","小组5","小组6","小组7","小组8","小组9","小组10",};
        List<Group> groups = new ArrayList<>();
        for (int i=0;i<groupArr.length;i++){
            groups.add(new Group(i,groupArr[i]));
        }
        sendNotificationView.loadGroupList(groups);
        /*sendNotificationInf.loadGroupList(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                sendNotificationView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
                //这里转换   respose ---> grouplist
                // sendNotificationView.loadGroupList();
            }
        });*/
    }

    @Override
    public void loadMoreGroupList(int page) {
        String [] groupArr = {"小组1","小组2","小组3","小组4","小组5","小组6","小组7","小组8","小组9","小组10",};
        List<Group> groups = new ArrayList<>();
        for (int i=0;i<groupArr.length;i++){
            groups.add(new Group(i,groupArr[i]));
        }
        sendNotificationView.loadMoreGroupList(groups);
        /*sendNotificationInf.loadGroupList(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                sendNotificationView.loadError(call,e);
            }

            @Override
            public void onResponse(String response) {
                //这里转换   respose ---> grouplist
                //
            }
        });*/
    }

    @Override
    public void sendNotificationg(String userName, String content) {
        //sendNotificationView.sendNotification(group);
        sendNotificationInf.sendNotification(userName, content, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                sendNotificationView.loadError("request error"+e==null?"null":e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                sendNotificationView.sendNotificationSucc();
            }
        });
    }
}
