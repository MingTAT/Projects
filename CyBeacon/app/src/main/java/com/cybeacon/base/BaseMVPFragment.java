package com.cybeacon.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybeacon.R;
import com.cybeacon.constants.ErrorCode;

/**
 * @author Ming
 */

public abstract class BaseMVPFragment<P extends IPresenter<V>,V extends IBaseView> extends Fragment {

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
     * 上下文
     */
    public Context mContext;

    /**
     * 宿主Activity
     * 内存不足的情况会导致许多问题，其中之一就是Fragment调用getActivity()的地方却返回null，
     * 报了空指针异常。解决办法就是在Fragment基类里设置一个Activity mActivity的全局变量，
     * 在onAttach(Activity activity)（使用onAttach(Context context)）里赋值，
     * 使用mActivity代替getActivity()
     */
    public Activity mActivity;

    /**
     *  fragment 的 view是否被创建
     */
    public boolean isViewCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = mActivity;
        //设置 view 和 presenter
        view = attachView();
        presenter = attachPresenter();
        if (presenter != null){
            presenter.setView(view);
        }
        try{
            //设置布局内容
            if (rootView == null){
                rootView = inflater.inflate(getLayoutResId(),null,false);
            }
            //视图创建完毕
            isViewCreated = true;
            //绑定UI控件
            bindUI(rootView);
            //绑定事件
            bindEvent();
            //业务逻辑代码
            businessCode();
        }catch (Exception e){
            e.printStackTrace();
            if (presenter != null)
            presenter.onError(ErrorCode.CODE_ERROR,getString(R.string.error_message_tip),e);
        }
        return rootView;
    }

    /**
     * 业务代码
     */
    protected abstract void businessCode();

    /**
     * 当前fragment绘制完内容视图
     * @param rootView  根布局
     */
    abstract protected void bindUI(View rootView);

    /**
     * 绑定事件
     */
    abstract protected void bindEvent();

    /**
     * 返回当前fragment布局文件的id
     * @return  int  layout resource id
     */
    abstract protected int getLayoutResId();

    /**
     * 通过该方法附加P层，一定要实现
     * @return 当前 P
     */
    protected abstract P attachPresenter();

    /**
     * 通过该方法附加V层，一定要实现
     * @return 当前V
     */
    protected abstract V attachView();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }
}
