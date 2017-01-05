package com.healthy.healthyhelper.student.test.inf;

import com.healthy.healthyhelper.base.Config;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

/**
 * Created by Ming on 2016/4/3.
 */
public class InfoInfImpl implements InfoInf {
    @Override
    public void loadFoodPlan(String day, String planName, StringCallback callback) {
        OkHttpUtils.get().url(Config.Food_URL).addParams("plan_name", planName).addParams("day",day).build().execute(callback);
    }

    @Override
    public void loadTrainPlan(String day, String planName, StringCallback callback) {
        OkHttpUtils.get().url(Config.Train_URL).addParams("plan_name", planName).addParams("day",day).build().execute(callback);
    }
}
