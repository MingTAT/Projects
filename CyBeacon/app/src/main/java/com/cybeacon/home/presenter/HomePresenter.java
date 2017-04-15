package com.cybeacon.home.presenter;

import android.content.Context;
import android.content.Intent;

import com.cybeacon.base.BasePresenter;
import com.cybeacon.base.IPresenter;
import com.cybeacon.constants.IntentExtras;
import com.cybeacon.course.CourseListActivity;
import com.cybeacon.home.HomeActivity;
import com.cybeacon.home.view.HomeView;
import com.cybeacon.login.LoginActivity;

/**
 * @author Ming
 */
public class HomePresenter extends BasePresenter<HomeView> {

    public HomePresenter(Context mContext) {
        super(mContext);
    }

    @Override
    protected void handleError(int errorCode, String message) {

    }

    public void forwardLoginView() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    public void forwardCourseListView() {
        Intent intent = new Intent(mContext, CourseListActivity.class);
        intent.putExtra(IntentExtras.UserType, HomeActivity.UserType.STUDENT);
        mContext.startActivity(intent);
    }
}
