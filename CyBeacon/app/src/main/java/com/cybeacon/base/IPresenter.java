package com.cybeacon.base;

import android.content.Context;

/**
 * @author Ming
 */

public interface IPresenter<V> {

    /**
     * 这里由presenter决定如何处理错误信息
     * @param errorCode  错误编码
     * @param message  错误信息
     * @param exception
     */
    void onError(int errorCode, String message, Exception exception);

    /**
     * 设置View
     * @param view  和用户交互的view
     */
    void setView(V view);

    /**
     * 分离view 资源释放
     */
    void detachView();

}
