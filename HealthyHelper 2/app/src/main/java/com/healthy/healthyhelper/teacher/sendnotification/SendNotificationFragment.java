package com.healthy.healthyhelper.teacher.sendnotification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.BaseFragment;
import com.healthy.healthyhelper.model.Group;
import com.healthy.healthyhelper.teacher.sendnotification.adapter.GroupListAdapter;
import com.healthy.healthyhelper.teacher.sendnotification.presenter.SendNotificationPresenter;
import com.healthy.healthyhelper.teacher.sendnotification.presenter.SendNotificationPresenterImpl;
import com.healthy.healthyhelper.teacher.sendnotification.view.SendNotificationView;
import com.healthy.healthyhelper.ui.MsgDialog;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class SendNotificationFragment extends BaseFragment implements SendNotificationView {

    private View contentView;
    private SendNotificationPresenter presenter;
    private PullToRefreshListView refreshListView;
    private GroupListAdapter groupListAdapter;
    private ListView listView;
    private int currentPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.send_notification_layout,null);
        presenter = new SendNotificationPresenterImpl(this);
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
        //refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(onRefreshListener2);
        presenter.loadGroupList(currentPage);
    }

    PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            currentPage = 1;
            presenter.loadGroupList(currentPage);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            presenter.loadMoreGroupList(currentPage);
        }
    };

    /**
     * 假数据加载过快，需要延迟加载，控件有bug
     */
    private void refreshEnd(){
        refreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshListView.onRefreshComplete();
            }
        },500);
    }

    @Override
    public void loadGroupList(List<Group> groups) {
        if (groupListAdapter == null){
            groupListAdapter = new GroupListAdapter(getActivity(),presenter,R.layout.item_list_group_layout,groups);
            listView.setAdapter(groupListAdapter);
        }else {
            groupListAdapter.refreshDatas(groups);
        }
        refreshEnd();
    }

    @Override
    public void loadMoreGroupList(List<Group> groups) {
        if (groupListAdapter!=null){
            groupListAdapter.addDatas(groups);
        }
        refreshEnd();
    }

    @Override
    public void sendNotificationSucc() {
        new MsgDialog(getActivity()).message("消息已成功发送").leftText(null).rightText("确定").show();
    }

    @Override
    public void loadError(String errorMsg) {
        ToastUtil.show(getActivity(),errorMsg,2000);
        refreshEnd();
    }
}
