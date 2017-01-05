package com.healthy.healthyhelper.student.apply;

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
import com.healthy.healthyhelper.model.Teacher;
import com.healthy.healthyhelper.student.apply.adapter.TeacherListAdapter;
import com.healthy.healthyhelper.student.apply.persenter.ApplyForTeacherPresenter;
import com.healthy.healthyhelper.student.apply.persenter.ApplyForTeacherPresenterImpl;
import com.healthy.healthyhelper.student.apply.view.ApplyForTeacherView;
import com.healthy.healthyhelper.ui.MsgDialog;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyForTeacherFragment extends BaseFragment implements ApplyForTeacherView {

    private View contentView;

    private PullToRefreshListView refreshList;
    private ListView listView;
    private TeacherListAdapter listAdapter;
    private ApplyForTeacherPresenter applyForTeacherPresenter;

    private int currentPage = 0;//当前页数

    private MsgDialog msgDialog;//弹出框

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.apply_list_layout,null);
        applyForTeacherPresenter = new ApplyForTeacherPresenterImpl(this);
        findViewById();
        initView();
        return contentView;
    }

    @Override
    protected void findViewById() {
        refreshList = (PullToRefreshListView) contentView.findViewById(R.id.refreshList);
        listView = refreshList.getRefreshableView();
    }

    @Override
    protected void initView() {
        applyForTeacherPresenter.initTeacherList();
        //上拉刷新下拉加载都支持
       // refreshList.setMode(PullToRefreshBase.Mode.BOTH);
        refreshList.setOnRefreshListener(onRefreshListener);
       //如果需要整行都可以响应点击事件的话，需要把item中的button焦点去掉才可以响应
        // listView.setOnItemClickListener(onItemClickListener);
    }
    /*//item点击时间，申请老师课程
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Teacher teacher = (Teacher) parent.getItemAtPosition(position);
            new MsgDialog(getActivity())
                    .title("确认申请吗?")
                    .message("确认申请"+teacher.getName()+"老师的训练课程吗")
                    .leftText("取消").rightText("确认")
                    .onClicklistener(dialogClickListener)
                    .show();
        }
    };

    MsgDialog.DialogClickListener dialogClickListener = new MsgDialog.DialogClickListener() {
        @Override
        public void leftButtonClick() {
            msgDialog.dismissDialog();
        }

        @Override
        public void rightButtonClick() {
            applyForTeacher();
        }
    };
*/
    PullToRefreshBase.OnRefreshListener2 onRefreshListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            applyForTeacherPresenter.initTeacherList();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            applyForTeacherPresenter.loadMoreTeacher(currentPage);
        }
    };

    @Override
    public void loadTeacherList(List<Teacher> teachers) {
        if (listAdapter == null){
            listAdapter = new TeacherListAdapter(getActivity(),applyForTeacherPresenter,R.layout.item_list_teacher_layout,teachers);
            listView.setAdapter(listAdapter);
        }else {
            listAdapter.refreshDatas(teachers);
        }
        //假数据时间太短了有bug，用此方法解决
        refreshList.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshList.onRefreshComplete();
            }
        },500);
    }

    @Override
    public void applyForTeacher(Teacher teacher) {
        new MsgDialog(getActivity()).message("恭喜您申请的"+teacher.getName()+"老师的课程成功，待老师审核通过便可加入小组！")
                .leftText(null).
                rightText("确定")
                .show();
    }

    @Override
    public void loadMoreTeacher(List<Teacher> list) {
        currentPage++;
        if (listAdapter!=null){
            listAdapter.addDatas(list);
        }
        refreshList.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshList.onRefreshComplete();
            }
        },500);
    }

    @Override
    public void loadError(String errorMsg) {
        ToastUtil.show(getActivity().getApplicationContext(),errorMsg,2000);
        refreshList.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshList.onRefreshComplete();
            }
        },500);
    }
}
