package com.healthy.healthyhelper.student.test.view;

import com.healthy.healthyhelper.model.FoodPlan;
import com.healthy.healthyhelper.model.TrainPlan;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public interface InfoView {

    /**
     * load food plan data
     * @param result
     */
    void loadFoodPlan(List<FoodPlan.ResultBean> result);

    /**
     * load train plan data
     */
    void loadTrainPlan(List<TrainPlan.ResultBean> trains);

    /**
     *
     * load data error
     *
     * @param msg  error message
     */
    void loadError(String msg);

}
