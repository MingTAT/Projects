package com.cybeacon.course;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.cybeacon.constants.ErrorCode;
import com.cybeacon.course.adapter.courseDetailListAdapter;
import com.cybeacon.course.view.DetailListView;
import com.cybeacon.home.Config;
import com.cybeacon.course.ClassInfo;
import com.cybeacon.home.bleScanService;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cybeacon.R;
import com.cybeacon.APPConfig;
import com.cybeacon.base.BaseMVPActivity;
import com.cybeacon.constants.IntentExtras;
import com.cybeacon.course.adapter.CourseListAdapter;
import com.cybeacon.course.model.CourseResult;
import com.cybeacon.course.presenter.CourseListPresenter;
import com.cybeacon.course.view.CourseListView;
import com.cybeacon.home.HomeActivity;
import com.cybeacon.home.bleScanService;
import com.cybeacon.manager.SystemBarTintManager;
import com.cybeacon.professor.course_detail.CourseDetail;
import com.cybeacon.refresh.XZRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ming
 */

public class CourseListActivity extends BaseMVPActivity<CourseListPresenter,CourseListView> implements CourseListView {
    /**
     * courseList
     */
    private XZRecyclerView courseListView;

    private int currentPage = 1 ;
    private CourseListAdapter adapter;
    private courseDetailListAdapter detailadapter;
    private String userType;

    public ArrayList<String> listUUID;
    public int UUIDindex;
    public int Courseindex;
    public int Classindex;
    public ArrayList<String> StringListCourses;
    public ArrayList<Courses> listCourses;

    public ArrayList<CourseResult> courseresult = new ArrayList<>();

    public boolean search_complete;
    public boolean wait;
    public String strCyBeaconID = "7B44B47B-52A1-5381-90C2-F09B6838C5D4";
    public String beaconID = "001";
    //bleScanService bss = new bleScanService();
    //ArrayList<String> IDList = new ArrayList<>(bss.getUUID());
    //beaconID = IDList.get()

    public ArrayList<String> classNames = new ArrayList<>();
    private ProgressDialog loading;
    public Context con;


   @Override
    protected void businessCode() {
        userType = getIntent().getStringExtra(IntentExtras.UserType);
        //load data from server
        presenter.loadCourse(currentPage);
       IntentFilter intent_filt = new IntentFilter();
       intent_filt.addAction("ACTION_SCAN_UPDATE");
       registerReceiver(ReceiveFromService, intent_filt);
    }

    @Override
    protected void bindUI(View rootView) {
        courseListView = ((XZRecyclerView) findViewById(R.id.courseList));
    }

    @Override
    protected void bindEvent() {
        courseListView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        courseListView.setOnRecyclerViewRefreshListener(refreshListener);
    }

    XZRecyclerView.OnRecyclerViewRefreshListener refreshListener = new XZRecyclerView.OnRecyclerViewRefreshListener() {
        @Override
        public void onLoadMore() {
            presenter.loadCourse(currentPage);
        }

        @Override
        public void onRefresh() {
            Context con = getApplicationContext();
            con.startService(new Intent(con, bleScanService.class));
            currentPage = 1;
            presenter.loadCourse(currentPage);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_course_list_layout;
    }

    /*after clicking on course, opens up list of classes/lectures*/
    MultiItemTypeAdapter.OnItemClickListener onItemClickListener = new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            if (HomeActivity.UserType.PROFESSER.equals(userType)){
                Intent intent = new Intent(mContext, CourseDetail.class);
                startActivity(intent);
            }else { //modified by sam on 3.23.2017

                Intent intent = new Intent(mContext, DetailListActivity.class);
                //intent.putExtra(IntentExtras.UserType, HomeActivity.UserType.STUDENT);
                int i;
                ArrayList<String> tempClassNames = new ArrayList<String>();
                ArrayList<String> tempURL = new ArrayList<String>();
                for(i=0;i<listCourses.get(position-2).Classes.size();i++){
                    tempClassNames.add(listCourses.get(position-2).Classes.get(i).ClassName);
                    tempURL.add(listCourses.get(position-2).Classes.get(i).URL);
                }

                //intent.putExtra("Class_List",tempClassNames);
                intent.putExtra("GET_CLASS_NAMES",tempURL);
                mContext.startActivity(intent);

                //Intent class_intent = new Intent();
                //class_intent.setAction("GET_CLASS_LIST");
                //class_intent.putExtra("CLASS_NAMES",tempClassNames);
                //class_intent.putExtra("CLASS_URLS",tempURL);
                //mContext.sendBroadcast(class_intent);
            }
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    @Override
    protected CourseListPresenter attachPresenter() {
        return new CourseListPresenter(mContext);
    }

    @Override
    protected CourseListView attachView() {
        return this;
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    public ArrayList<ClassInfo> getClasses(int index){
        return listCourses.get(index).Classes;
    }

    BroadcastReceiver ReceiveFromService = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            int i;
            Bundle b;
            b = intent.getExtras();
            listUUID = b.getStringArrayList("UUIDlist");

            listCourses = new ArrayList<Courses>();


            Courseindex = 0;
            loading = ProgressDialog.show(con,"Please wait...","Fetching...",false,false);
            if(listUUID.size() >0) {
                getCourseData(listUUID.get(Courseindex));
            }else{
                loading.dismiss();
            }
            /*
            for(i=0;i<listUUID.size();i++){
                //query for Courses and fill listCourses
                search_complete = false;
                wait = true;
                getCourseData(listUUID.get(i));
                //while(wait){}
            }
            //while(!search_complete){}

            //load courses found into rows on UI
            loadData(courseresult);

            //search database for classes for each course
            for(i=0;i<listCourses.size();i++){
                search_complete = false;
                getClassData(listCourses.get(i).UUID, listCourses.get(i).CourseName, i);
            }
            //while(!search_complete){}

            //search database to get URL for each class found
            for(i = 0;i<listCourses.size();i++){
                int j;
                for(j = 0;j<listCourses.get(i).Classes.size();j++){
                    search_complete = false;
                    getClassURL(listCourses.get(i).UUID, listCourses.get(i).CourseName,listCourses.get(i).Classes.get(j).ClassName,i,j);
                }
            }
            while(search_complete){}

            */

            //currentPage = 1;
            //presenter.loadCourse(currentPage);
        }
    };

    private void getClassURL(final String uuid, final String coursename, final String classname, final int course_index, final int class_index){
        String url = APPConfig.CLASS_URL_URL + uuid + "&class_name=" + coursename + "&class=" + classname;
        //search database using url
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                if(!(response.equals("{\"result\":[]}"))) {
                    showURLJSON(response, uuid, coursename, course_index, class_index);
                }
                //if(Courseindex < listCourses.size()){
                    if(Classindex < listCourses.get(Courseindex).Classes.size()){
                        getClassURL(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName,listCourses.get(Courseindex).Classes.get(Classindex).ClassName,Courseindex, Classindex);
                        Classindex++;
                    }else{
                        if(Courseindex < listCourses.size()-1){
                            Courseindex++;
                            Classindex = 0;
                            if(listCourses.get(Courseindex).Classes.size() > 0) {
                                getClassURL(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, listCourses.get(Courseindex).Classes.get(Classindex).ClassName, Courseindex, Classindex);
                            }else{
                                boolean temp = true;
                                while(temp){
                                    if(Courseindex < listCourses.size()-1) {
                                        Courseindex++;
                                        if (listCourses.get(Courseindex).Classes.size() > 0) {
                                            getClassURL(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, listCourses.get(Courseindex).Classes.get(Classindex).ClassName, Courseindex, Classindex);
                                            temp = false;
                                        }
                                    }else{
                                        temp = false;
                                    }
                                }
                            }
                        }else{
                            // database searching finished
                            loading.dismiss();

                        }
                    }
                //}
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CourseListActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getClassData(final String uuid, final String coursename, final int index){
        String url = APPConfig.CLASS_DATA_URL + uuid + "&class_name=" + coursename;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loading.dismiss();
                if(!(response.equals("{\"result\":[]}"))) {
                    showClassJSON(response, uuid, coursename, index);
                }
                if(Courseindex < listCourses.size()){
                    getClassData(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, Courseindex);
                    Courseindex++;
                }else{
                    Classindex = 0;
                    Courseindex = 0;
                    if(listCourses.get(Courseindex).Classes.size() > 0) {
                        getClassURL(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, listCourses.get(Courseindex).Classes.get(Classindex).ClassName, Courseindex, Classindex);
                    }else{
                        boolean temp = true;
                        while(temp){
                            if(Courseindex < listCourses.size()-1) {
                                Courseindex++;
                                if (listCourses.get(Courseindex).Classes.size() > 0) {
                                    getClassURL(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, listCourses.get(Courseindex).Classes.get(Classindex).ClassName, Courseindex, Classindex);
                                    temp = false;
                                }
                            }else{
                                temp = false;
                            }
                        }
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CourseListActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getCourseData(final String uuid) {



        String url =  APPConfig.COURSE_DATA_URL + uuid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!(response.equals("{\"result\":[]}"))) {
                    showCourseJSON(response, uuid);
                }
                if(UUIDindex<listUUID.size()){
                    getCourseData(listUUID.get(UUIDindex));
                    UUIDindex++;
                }else {
                    currentPage = 1;
                    presenter.loadCourse(currentPage);
                    Courseindex = 0;
                    loading.dismiss();
                    if ((listCourses.size() > 0)) {
                            AudioManager AppAudio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                            AppAudio.setRingerMode(1);
                            AppAudio.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_ALARM, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_MUSIC, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_RING, true);
                            AppAudio.setStreamMute(AudioManager.STREAM_SYSTEM, true);
                        getClassData(listCourses.get(Courseindex).UUID, listCourses.get(Courseindex).CourseName, Courseindex);
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CourseListActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //*open network
    }



    private void showCourseJSON(String response,String uuid){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(APPConfig.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++)
            {
                String className = "";
                JSONObject linkData = result.getJSONObject(i);
                className = linkData.getString(APPConfig.CLASS_NAME); //assigns className to the text associated with class_name
                Courses tempcourse = new Courses();
                tempcourse.UUID = uuid;
                tempcourse.CourseName = className;
                tempcourse.Classes = new ArrayList<ClassInfo>();
                classNames.add(className);

                listCourses.add(tempcourse);
                search_complete = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showClassJSON(String response,String uuid, String CourseName, int index){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(APPConfig.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++)
            {
                String className = "";
                JSONObject linkData = result.getJSONObject(i);
                className = linkData.getString(APPConfig.KEY_CLASS); //assigns className to the text associated with class_name

                ClassInfo tempClass = new ClassInfo();
                tempClass.CourseName = CourseName;
                tempClass.ClassName = className;
                listCourses.get(index).Classes.add(tempClass);
                search_complete = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showURLJSON(String response,String uuid, String CourseName, int course_index, int class_index){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(APPConfig.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++)
            {
                String url = "";
                JSONObject linkData = result.getJSONObject(i);
                url = linkData.getString(APPConfig.WEB_URL); //assigns className to the text associated with class_name

                listCourses.get(course_index).Classes.get(class_index).URL = url;
                search_complete = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void loadData(ArrayList<CourseResult> mDatas) {

        //getData();
        //bleScanService bss = new bleScanService();
        //ArrayList<String> tempArrayList = new ArrayList(bss.getCourseNames());

/*      classNames.add("EE233");
        classNames.add("EE234");
        classNames.add("EE235");
        classNames.add("EE236");*/

        for (int i = 0; i < classNames.size(); i++) {
            String course = classNames.get(i);
            CourseResult courseResult = new CourseResult(course);
            mDatas.add(courseResult);
        }



        courseListView.refreshComplete();
        if (currentPage == 1){
            if (adapter == null){
                adapter = new CourseListAdapter(mContext, R.layout.item_course_layout,mDatas);
                adapter.setOnItemClickListener(onItemClickListener);
                courseListView.setIAdapter(adapter);
            }else {
                adapter.refreshDatas(mDatas);
            }
        }else {
            adapter.addDatas(mDatas);
        }
        classNames.clear();
        currentPage++;
    }



}
