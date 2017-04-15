package com.cybeacon.base;


import android.content.Context;

import com.cybeacon.constants.ErrorCode;

/**
 * @author Ming
 */

public abstract class BasePresenter<V extends IBaseView> implements IPresenter<V> {
    public V view;

    public Context mContext;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onError(int errorCode, String message, Exception exception) {
        if (errorCode == ErrorCode.CODE_ERROR){
            // TODO: 2016/12/30 异常上报
        }
        handleError(errorCode,message);
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null)
            view = null;
    }

    protected abstract void handleError(int errorCode, String message);

}
