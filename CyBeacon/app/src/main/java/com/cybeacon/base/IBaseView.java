package com.cybeacon.base;

/**
 * @author Ming
 */

public interface IBaseView {
    /**
     * 显示错误信息(网络异常等，业务错误)
     * @param errorMessage  错误信息
     */
    void showErrorMessage(String errorMessage);
}
