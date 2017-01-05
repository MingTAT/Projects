package com.healthy.healthyhelper.student.notification.presenter;

import com.healthy.healthyhelper.model.Announcement;
import com.healthy.healthyhelper.student.notification.inf.NotificationInf;
import com.healthy.healthyhelper.student.notification.inf.NotificationInfImpl;
import com.healthy.healthyhelper.student.notification.view.NotificationView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class NotificationPresenterImpl implements NotificationPresenter {
    NotificationView notificationView ;
    NotificationInf notificationInf;
    public NotificationPresenterImpl(NotificationView notificationView) {
        this.notificationView = notificationView;
        notificationInf = new NotificationInfImpl();
    }

    @Override
    public void loadNotification(int page) {
        //假数据
        String response = "[{\"username\":\"Kohaku\",\"text\":\"Hello World!\",\"time\":\"8:30pm 4\\/15\\/2016\"},{\"username\":\"Kohaku\",\"text\":\"Yosi\",\"time\":\"8:31pm 4\\/15\\/2016\"},{\"username\":\"Kohaku\",\"text\":\"MLGB\",\"time\":\"8:32pm 4\\/15\\/2016\"}]";
        try {
           List<Announcement> datas = Announcement.arrayAnnouncementFromData(response);
            if (datas == null || datas.size()<0){
                notificationView.loadError("parse json error,please check:"+response);
            }else {
                notificationView.loadNotification(datas);
            }
        }catch (Exception e){
            notificationView.loadError("parse json error,please check:" + "(" + e == null ? "null" : e.getMessage() + ")" + response);
        }
        notificationInf.loadNotification(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                notificationView.loadError(e == null?"call server error":e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (response == null){
                    notificationView.loadError("response is null,please check:"+response);
                }else {
                    try {
                        List<Announcement> datas = Announcement.arrayAnnouncementFromData(response);
                        if (datas == null || !(datas.size()>0)){
                            notificationView.loadError("parse json error,please check:"+response);
                        }else {
                            notificationView.loadNotification(datas);
                        }
                    }catch (Exception e){
                        notificationView.loadError("parse json error,please check:" + "(" + e == null ? "null" : e.getMessage() + ")" + response);
                    }

                }
            }
        });
    }

    @Override
    public void loadMoreNotification(int page) {
        //假数据
        String response = "[{\"username\":\"Kohaku\",\"text\":\"Hello World!\",\"time\":\"8:30pm 4\\/15\\/2016\"},{\"username\":\"Kohaku\",\"text\":\"Yosi\",\"time\":\"8:31pm 4\\/15\\/2016\"},{\"username\":\"Kohaku\",\"text\":\"MLGB\",\"time\":\"8:32pm 4\\/15\\/2016\"}]";
        try {
            List<Announcement> datas = Announcement.arrayAnnouncementFromData(response);
            if (datas == null || !(datas.size()>0)){
                notificationView.loadError("parse json error,please check:"+response);
            }else {
                notificationView.loadMoreNotification(datas);
            }
        }catch (Exception e){
            notificationView.loadError("parse json error,please check:" + "(" + e == null ? "null" : e.getMessage() + ")" + response);
        }
        /*notificationInf.loadNotification(page, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                notificationView.loadError(e == null ? "call server error" : e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    notificationView.loadError("response is null,please check:" + response);
                } else {
                    try {
                        Announcement announcement = new Gson().fromJson(response, Announcement.class);
                        if (announcement == null) {
                            notificationView.loadError("parse json error,please check:" + response);
                        } else {
                            notificationView.loadMoreNotification(announcement.getResult());
                        }
                    }catch (Exception e){
                        notificationView.loadError("parse json error,please check:"+"("+e==null?"null":e.getMessage()+")"+response);
                    }

                }
            }
        });*/
    }

    @Override
    public void onDestory() {
        notificationView = null;
    }
}
