package com.healthy.healthyhelper.teacher.studentlist;

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
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.healthy.healthyhelper.teacher.studentlist.adapter.StudentListAdapter;
import com.healthy.healthyhelper.teacher.studentlist.presenter.StudentListPresenter;
import com.healthy.healthyhelper.teacher.studentlist.presenter.StudentListPresenterImpl;
import com.healthy.healthyhelper.teacher.studentlist.view.StudentListView;
import com.healthy.healthyhelper.util.SPUtils;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class StudentListFragment extends BaseFragment implements StudentListView {

    private View contentView;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private StudentListPresenter studentListPresenter;
    private StudentListAdapter studentListAdapter;
    private int currentPage = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.notification_list_layout,null);
        studentListPresenter = new StudentListPresenterImpl(this);
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
        //refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setOnRefreshListener(onRefreshListener2);
        listView.setOnItemClickListener(onItemClickListener);
        studentListPresenter.loadStudentList((String) SPUtils.get(getActivity(), Config.USERNAME_SHARED_PREF,"null"));
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Student student = (Student) parent.getItemAtPosition(position);
            ToastUtil.show(getActivity(),student.getName());
            /*new MsgDialog(getActivity()).message(student.getName())
                    .leftText(null).rightText("确定").show();*/
        }
    };

    PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            currentPage = 1;
            studentListPresenter.loadStudentList((String) SPUtils.get(getActivity(), Config.USERNAME_SHARED_PREF,"null"));
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            studentListPresenter.loadMoreStudentList(currentPage);
        }
    };


    @Override
    public void loadStudentList(List<StudentResponse.ResultBean> students) {
        refreshComplete();
        if (studentListAdapter == null){
            studentListAdapter = new StudentListAdapter(getActivity(),R.layout.item_list_student2_layout,students);
            listView.setAdapter(studentListAdapter);
        }else {
            studentListAdapter.refreshDatas(students);
        }

    }

    @Override
    public void loadMoreStudentList(List<StudentResponse.ResultBean> students) {
        refreshComplete();
        currentPage++;
        if (studentListAdapter != null){
            studentListAdapter.addDatas(students);
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
    public void loadError(String msg) {
        ToastUtil.show(getActivity().getApplicationContext(),msg);
        refreshComplete();
    }
}
