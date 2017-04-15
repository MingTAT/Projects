package com.cybeacon.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.cybeacon.APPConfig;
import com.cybeacon.R;
import com.cybeacon.constants.ErrorCode;
import com.cybeacon.manager.SystemBarTintManager;


/**
 * @author Ming
 */

public abstract class BaseMVPActivity<P extends IPresenter<V>, V extends IBaseView> extends AppCompatActivity {

    /**
     * presenter 层用来处理业务逻辑，它和数据层，视图层交互
     */
    public P presenter;

    /**
     * view 层用来做页面展示，和用户交互
     */
    public V view;

    /**
     * 根布局
     */
    public View rootView;

    /**
     * 根布局是否延伸至屏幕顶部
     */
    public boolean fitsSystemWindows = true;

    public Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
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
    }

    /**
     * 在这里写业务逻辑代码
     */
    protected abstract void businessCode();

    /**
     * 在这个方法里初始化UI控件
     *
     * @param rootView 根布局
     */
    protected abstract void bindUI(View rootView);

    /**
     * 在这个方法里绑定控件的事件
     */
    protected abstract void bindEvent();

    /**
     * 设置布局文件资源id
     *
     * @return 布局文件资源id
     */
    protected abstract int getLayoutResId();

    /**
     * 通过该方法附加P层，一定要实现
     *
     * @return 当前 P
     */
    protected abstract P attachPresenter();

    /**
     * 通过该方法附加V层，一定要实现
     *
     * @return 当前V
     */
    protected abstract V attachView();

    public void setFitsSystemWindows(boolean fitsSystemWindows) {
        this.fitsSystemWindows = fitsSystemWindows;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //分离view，资源回收
        if (presenter != null)
            presenter.detachView();
    }

    /**
     * 跳转到指定的Activity
     *
     * @param flags          intent flags
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(int flags, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent(this, targetActivity);
        intent.setFlags(flags);
        startActivity(new Intent(this, targetActivity));
    }
    
}
