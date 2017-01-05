package com.healthy.healthyhelper.student.test.presenter;

/**
 * Created by Ming on 2016/4/3.
 */
public interface InfoPresenter {
    /**
     *
     * load plan data
     *
     * @param id  food or train plan
     * @param planName    A\B\C
     * @param day
     */
    void loadData(int id,String planName,String day);
}
