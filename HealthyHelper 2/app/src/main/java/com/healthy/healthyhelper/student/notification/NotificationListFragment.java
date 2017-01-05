package com.healthy.healthyhelper.student.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.BaseFragment;
import com.healthy.healthyhelper.model.Announcement;
import com.healthy.healthyhelper.model.Notification;
import com.healthy.healthyhelper.student.notification.adapter.NotificationListAdapter;
import com.healthy.healthyhelper.student.notification.presenter.NotificationPresenter;
import com.healthy.healthyhelper.student.notification.presenter.NotificationPresenterImpl;
import com.healthy.healthyhelper.student.notification.view.NotificationView;
import com.healthy.healthyhelper.ui.MsgDialog;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.List;

import okhttp3.Call;
/**
 * Created by Ming on 2016/4/3.
 */
public class NotificationListFragment extends BaseFragment implements NotificationView{

    private View contentView;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private NotificationPresenter notificationPresenter;
    private NotificationListAdapter notificationListAdapter;
    private int currentPage = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.notification_list_layout,null);
        notificationPresenter = new NotificationPresenterImpl(this);
        findViewById();
        initView();
        return contentView;
    }

    @Override
    protected void findViewById() {
        refreshListView = (PullToRefreshListView) contentView.findViewById(R.id.refreshList);
        listView = refreshListView.getRefreshableView();
    }

    @Override
    protected void initView() {
        //上拉刷新和下拉加载都要开启
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(onRefreshListener2);
        listView.setOnItemClickListener(onItemClickListener);
        notificationPresenter.loadNotification(currentPage);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Announcement notification = (Announcement) parent.getItemAtPosition(position);
            new MsgDialog(getActivity()).message(notification.getText())
                    .leftText(null).rightText("确定").show();
        }
    };

    PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            currentPage = 1;
            notificationPresenter.loadNotification(currentPage);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            notificationPresenter.loadMoreNotification(currentPage);
        }
    };


    @Override
    public void loadNotification(List<Announcement> notifications) {
        refreshComplete();
        if (notificationListAdapter == null){
            notificationListAdapter = new NotificationListAdapter(getActivity(),R.layout.item_list_notification_layout,notifications);
            listView.setAdapter(notificationListAdapter);
        }else {
            notificationListAdapter.refreshDatas(notifications);
        }

    }

    @Override
    public void loadMoreNotification(List<Announcement> notifications) {
        refreshComplete();
        currentPage++;
        if (notificationListAdapter != null){
            notificationListAdapter.addDatas(notifications);
        }
    }

    /**
     * 刷新控件刷新完成
     */
    private void refreshComplete(){
        if (refreshListView!=null){
            refreshListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshListView.onRefreshComplete();
                }
            },1000);
        }
    }

    @Override
    public void loadError(String errorMsg) {
        ToastUtil.show(getActivity().getApplicationContext(),errorMsg,2000);
        refreshComplete();
    }
}
