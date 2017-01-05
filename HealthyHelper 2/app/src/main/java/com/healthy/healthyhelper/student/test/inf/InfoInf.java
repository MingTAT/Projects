package com.healthy.healthyhelper.student.test.inf;

import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public interface InfoInf  {

    /**
     *
     * loadFoodPlan
     *
     * @param day
     * @param planName
     * @param callback
     */
    void loadFoodPlan(String day,String planName,StringCallback callback);

    /**
     *
     * loadTrainPlan
     *
     * @param day
     * @param planName
     * @param callback
     */
    void loadTrainPlan(String day,String planName,StringCallback callback);

}
