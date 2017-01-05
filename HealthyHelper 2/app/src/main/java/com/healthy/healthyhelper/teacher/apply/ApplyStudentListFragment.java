package com.healthy.healthyhelper.teacher.apply;

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
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.healthy.healthyhelper.teacher.apply.adapter.ApplyStudentListAdapter;
import com.healthy.healthyhelper.teacher.apply.presenter.ApplyStudentPersenter;
import com.healthy.healthyhelper.teacher.apply.presenter.ApplyStudentPersenterImpl;
import com.healthy.healthyhelper.teacher.apply.view.ApplyStudentListView;
import com.healthy.healthyhelper.util.SPUtils;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyStudentListFragment extends BaseFragment implements ApplyStudentListView {

    private ApplyStudentPersenter applyStudentPersenter;
    private View contentView;
    private ApplyStudentListAdapter listAdapter;
    private PullToRefreshListView refreshListView;
    private ListView listView;
    private int currentPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.apply_student_list_layout,null);
        applyStudentPersenter = new ApplyStudentPersenterImpl(this);
        findViewById();
        initView();
        return contentView;
    }

    @Override
    public void loadStudentList(List<StudentResponse.ResultBean> students) {

        if (listAdapter == null){
            listAdapter = new ApplyStudentListAdapter(getActivity(),applyStudentPersenter,R.layout.item_list_student_layout,students);
            listView.setAdapter(listAdapter);
        }else {
            listAdapter.refreshDatas(students);
        }
        refreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshListView.onRefreshComplete();
            }
        },500);
    }

    @Override
    public void loadMoreStudentList(List<StudentResponse.ResultBean> students) {
        currentPage++;
        refreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshListView.onRefreshComplete();
            }
        },500);
        if (listAdapter!=null){
            listAdapter.addDatas(students);
        }
    }

    @Override
    public void auditPass(StudentResponse.ResultBean student) {
        ToastUtil.show(getActivity(),student.getStudent()+"审核通过！");
    }

    @Override
    public void auditReject(StudentResponse.ResultBean student) {
        ToastUtil.show(getActivity(),student.getStudent()+"的审核已拒绝！");
    }

    @Override
    public void loadError(String msg) {
        refreshListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshListView.onRefreshComplete();
            }
        },500);
        ToastUtil.show(getActivity(),msg,2000);
    }
    @Override
    protected void findViewById() {
        refreshListView  = (PullToRefreshListView) contentView.findViewById(R.id.refreshList);
        listView = refreshListView.getRefreshableView();
    }

    @Override
    protected void initView() {
        //refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(onRefreshListener2);
        applyStudentPersenter.loadStudentList((String) SPUtils.get(getActivity(), Config.USERNAME_SHARED_PREF, "null"));
    }

    PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            currentPage = 1;
            applyStudentPersenter.loadStudentList((String) SPUtils.get(getActivity(), Config.USERNAME_SHARED_PREF,"null"));
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            applyStudentPersenter.loadMoreStudentList((String) SPUtils.get(getActivity(), Config.USERNAME_SHARED_PREF,"null"));
        }
    };

}
