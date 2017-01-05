package com.healthy.healthyhelper.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.AppManager;
import com.healthy.healthyhelper.base.BaseFragmentActivity;
import com.healthy.healthyhelper.student.apply.ApplyForTeacherFragment;
import com.healthy.healthyhelper.student.test.PhysicalExaminationFragment;
import com.healthy.healthyhelper.student.notification.NotificationListFragment;
import com.healthy.healthyhelper.ui.ViewPagerIndicator;
import com.healthy.healthyhelper.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class StudentActivity extends BaseFragmentActivity {
    /**
     * 预加载页面数量
     */
    private static final int  DEFAULT_OFFSCREEN_PAGES = 2;
    //放置fragment
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    //导航条的title
    List<String> mDatas = Arrays.asList("申请老师", "通知列表","体质检测");
    private ViewPagerIndicator indicator ;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
    }

    @Override
    protected void initView() {
        viewPager.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGES);
        //1.加载三个页面
        mTabs.add(new ApplyForTeacherFragment());
        mTabs.add(new NotificationListFragment());
        mTabs.add(new PhysicalExaminationFragment());
        indicator.setTabItemTitles(mDatas);
        indicator.setViewPager(viewPager,0);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        });
    }

    private long timeMillis = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                ToastUtil.show(getApplicationContext(), "再按一次退出程序");
                timeMillis = System.currentTimeMillis();
            } else {
                AppManager.getInstance().AppExit(StudentActivity.this);
               // AppManager.getInstance().killAllActivity(StudentActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
