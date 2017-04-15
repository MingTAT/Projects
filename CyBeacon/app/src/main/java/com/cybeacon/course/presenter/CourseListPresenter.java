package com.cybeacon.course.presenter;

import android.content.Context;
import android.os.Handler;

import com.cybeacon.base.BasePresenter;
import com.cybeacon.course.model.CourseResult;
import com.cybeacon.course.view.CourseListView;

import java.util.ArrayList;


/**
 * @author Ming
 */
//communicate
public class CourseListPresenter extends BasePresenter<CourseListView> {


    private Handler handler = new Handler();

    public CourseListPresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleError(int errorCode, String message) {

    }

    /**
     * load course data list
     * @param currentPage  page
     */



//originally Ming's code
    public void loadCourse(final int currentPage) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
           //    CourseListActivity courselistactivity = new CourseListActivity();

                ArrayList<CourseResult> mList = new ArrayList<>();

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
