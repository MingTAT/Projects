package com.cybeacon.course.presenter;

import android.content.Context;
import android.os.Handler;

import com.cybeacon.base.BasePresenter;
import com.cybeacon.course.ClassInfo;
import com.cybeacon.course.model.CourseResult;
import com.cybeacon.course.view.CourseListView;
import com.cybeacon.course.view.DetailListView;

import java.util.ArrayList;

/**
 * Created by Sam on 3/30/2017.
 */
public class DetailListPresenter extends BasePresenter<DetailListView> {


    private Handler handler = new Handler();

    public DetailListPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleError(int errorCode, String message) {

    }

    public void loadCourse(final int currentPage) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //    CourseListActivity courselistactivity = new CourseListActivity();

                ArrayList<ClassInfo> mList = new ArrayList<>();

//                for (int i=0; i<courselistactivity.classNames.size(); i++){
//                    String course = courselistactivity.classNames.get(i);
//                    CourseResult courseResult = new CourseResult(course);
//                    mList.add(courseResult);
//                }
                view.loadData(mList);
            }
        },1000);

    }
}
