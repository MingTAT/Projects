 package com.cybeacon.course;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cybeacon.APPConfig;
import com.cybeacon.R;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.constants.ErrorCode;
import com.cybeacon.constants.IntentExtras;
import com.cybeacon.course.adapter.CourseListAdapter;
import com.cybeacon.course.adapter.MyApplication;
import com.cybeacon.course.adapter.courseDetailListAdapter;
import com.cybeacon.course.model.CourseResult;
import com.cybeacon.course.presenter.CourseListPresenter;
import com.cybeacon.course.presenter.DetailListPresenter;
import com.cybeacon.course.view.CourseListView;
import com.cybeacon.course.view.DetailListView;
import com.cybeacon.home.HomeActivity;
import com.cybeacon.home.bleScanService;
import com.cybeacon.manager.SystemBarTintManager;
import com.cybeacon.professor.course_detail.CourseDetail;
import com.cybeacon.refresh.XZRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;

/**
 * Created by Sam on 3/30/2017.
 */
public class DetailListActivity extends BaseMVPActivity<DetailListPresenter,DetailListView> implements DetailListView {

    private XZRecyclerView DetailListView;
    private courseDetailListAdapter detailadapter;
    private String userType;
    private int currentPage=1;
    private Context mContext;
    private ArrayList<ClassInfo> classes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        classes = new ArrayList<>();
        //con = this;
        //设置 view 和 presenter
        view = attachView();
        presenter = attachPresenter();
        if (presenter != null) {
            presenter.setView(view);
        }
        try {
            //设置布局
            rootView = LayoutInflater.from(this).inflate(getLayoutResId(), null);
            setContentView(rootView);
            //是否启用状态栏设置
            if (APPConfig.statusBarEnabled) {
                new SystemBarTintManager(this)
                        .setUpAppStatusBar(
                                getResources().getColor(R.color.transparent),
                                (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT), fitsSystemWindows);
            }
            //绑定UI
            bindUI(rootView);
            //绑定事件
            bindEvent();
            //业务逻辑代码 (如 请求数据 更新UI)
            businessCode();
        } catch (Exception e) {
            e.printStackTrace();
            if (presenter != null)
                presenter.onError(ErrorCode.CODE_ERROR, getString(R.string.error_message_tip), e);
        }

       // IntentFilter intent_filt = new IntentFilter();
        //intent_filt.addAction("GET_CLASS_LIST");
        //registerReceiver(ReceivefromCourseActivity, intent_filt);
    }


    @Override
    protected void businessCode() {
        //userType = getIntent().getStringExtra(IntentExtras.UserType);
        ArrayList<String> tempClassNames = new ArrayList<String>();
        ArrayList<String> tempURL = new ArrayList<String>();
        //tempClassNames = getIntent().getStringArrayListExtra("Class_List");
        tempURL = getIntent().getStringArrayListExtra("GET_CLASS_NAMES");
        int i;
        //ArrayList<ClassInfo> tempclasses = new ArrayList<ClassInfo>();
        classes.clear();
        for(i=0;i<tempURL.size();i++){
            ClassInfo temp = new ClassInfo();
            //temp.ClassName = tempClassNames.get(i);
            temp.URL = tempURL.get(i);
            classes.add(temp);
        }
        //((MyApplication)getApplication()).setclassList(classes);
//        load data from server
        presenter.loadCourse(currentPage);

    }
/*
    BroadcastReceiver ReceivefromCourseActivity = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if(classes == null){classes = new ArrayList<ClassInfo>();}
            ArrayList<String> tempClassNames = new ArrayList<String>();
            ArrayList<String> tempURL = new ArrayList<String>();
            Bundle b = intent.getExtras();
            tempClassNames = b.getStringArrayList("CLASS_NAMES");
            tempURL = b.getStringArrayList("CLASS_URLS");
            int i;
            for(i=0;i<tempClassNames.size();i++){
                ClassInfo temp = new ClassInfo();
                temp.ClassName = tempClassNames.get(i);
                temp.URL = tempURL.get(i);
                classes.add(temp);
            }
            presenter.loadCourse(currentPage);
        }
    };
*/
    @Override
    protected void bindUI(View rootView) {
        DetailListView = ((XZRecyclerView) findViewById(R.id.detailList));
    }

    @Override
    protected void bindEvent() {
        DetailListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        DetailListView.setOnRecyclerViewRefreshListener(refreshListener);
    }

    @Override
    protected DetailListPresenter attachPresenter() {
        return new DetailListPresenter(mContext);
    }

    @Override
    protected DetailListView attachView() {
        return this;
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    XZRecyclerView.OnRecyclerViewRefreshListener refreshListener = new XZRecyclerView.OnRecyclerViewRefreshListener() {
        @Override
        public void onLoadMore() {
            //presenter.loadCourse(currentPage);
        }

        @Override
        public void onRefresh() {
            Context con = getApplicationContext();
            //con.startService(new Intent(con, bleScanService.class));
            //currentPage = 1;
            //presenter.loadCourse(currentPage);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail_list_layout;
    }


    MultiItemTypeAdapter.OnItemClickListener onItemClickListener = new MultiItemTypeAdapter.OnItemClickListener() {


        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (HomeActivity.UserType.PROFESSER.equals(userType)) {

            } else { //modified by sam on 3.23.2017

                TextView temp = (TextView)view.findViewById(R.id.classNameText);
                        String temp_text = temp.getText().toString();

                if(!(temp_text == null)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(temp_text));
                    startActivity(intent);
                }
                //OpenClassURL(position-2);
            }
        }
        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    public void OpenClassURL(int index){
        if(!(classes.size() == 0 )) {
            String url = classes.get(index).getURL();

        }
    }

    @Override
    public void loadData(ArrayList<ClassInfo> classinfo) {
        //if(!(classinfo == null)) {
            ArrayList<CourseResult> mDatas = new ArrayList<CourseResult>();
            for (int i = 0; i < classes.size(); i++) {
                String course = classes.get(i).URL;
                CourseResult courseResult = new CourseResult(course);
                mDatas.add(courseResult);
            }

            //if ((classinfo == null)) {
            //    if (classes == null) {
            //        classes = new ArrayList<>();
            //    }
            //    classes.clear();
            classinfo=classes;

            // classinfo = classes;
            if (classinfo.size() > 0) {
                DetailListView.refreshComplete();
                if (currentPage == 1) {
                    if (detailadapter == null) {
                        detailadapter = new courseDetailListAdapter(mContext, R.layout.activity_student_course_detail, mDatas);
                        detailadapter.setOnItemClickListener(onItemClickListener);
                        DetailListView.setIAdapter(detailadapter);
                    } else {
                        detailadapter.refreshDatas(mDatas);
                    }
                } else {
                    detailadapter.addDatas(mDatas);
                }
                classinfo.clear();
                currentPage++;
            }

    }
}
