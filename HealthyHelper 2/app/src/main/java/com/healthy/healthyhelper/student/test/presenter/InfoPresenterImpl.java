package com.healthy.healthyhelper.student.test.presenter;

import com.google.gson.Gson;
import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.model.FoodPlan;
import com.healthy.healthyhelper.model.TrainPlan;
import com.healthy.healthyhelper.student.test.inf.InfoInf;
import com.healthy.healthyhelper.student.test.inf.InfoInfImpl;
import com.healthy.healthyhelper.student.test.view.InfoView;
import com.healthy.healthyhelper.util.StringUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Ming on 2016/4/3.
 */
public class InfoPresenterImpl implements InfoPresenter {

    private InfoView infoView;

    private InfoInf infoInf;

    public InfoPresenterImpl(InfoView infoView) {
        this.infoView = infoView;
        this.infoInf = new InfoInfImpl();
    }

    @Override
    public void loadData(int id, String planName, String day) {
        if (id == R.id.foodRadio) {
            //模拟数据
            String response = "{\"result\":[{\"day\":\"Tu\",\"breakfast\":\"Fat-free milk(200ml) + 1 Egg yolk + 2 Egg white + Grain gruel(250g) + 1 Serving fruit\",\"lunch\":\"Fish(150g) + Grain rice(150g) + Vegetable(unlimited) + Shrimp(150g) + Vegetable(unlimited)\",\"dinner\":\"Shrimp(150g) + Vegetable(unlimited)\"}]}";
            FoodPlan foodPlan = new Gson().fromJson(response, FoodPlan.class);
            if (foodPlan == null) {
                infoView.loadError("parse json error:" + response);
            } else {
                //设置标题
                if (foodPlan.getResult() != null && foodPlan.getResult().size() > 0) {
                    foodPlan.getResult().get(0).setHasTitle(true);
                }
                infoView.loadFoodPlan(foodPlan.getResult());
            }
            /*infoInf.loadFoodPlan(day, planName, new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    infoView.loadError("load data error"+e==null?"":e.getMessage());
                }

                @Override
                public void onResponse(String response) {
                    if (!StringUtil.isEmpty(response)){
                        FoodPlan foodPlan = new Gson().fromJson(response,FoodPlan.class);
                        if (foodPlan ==null){
                            infoView.loadError("parse json error:"+response);
                        }else {
                         //设置标题
                            if (foodPlan.getResult()!=null && foodPlan.getResult().size()>0){
                                foodPlan.getResult().get(0).setHasTitle(true);
                            }
                            infoView.loadFoodPlan(foodPlan.getResult());
                        }
                    }else {
                        infoView.loadError("server response is null,please check:"+response);
                    }

                }
            });*/
        } else {
            //模拟数据
            String response = "{\"result\":[{\"day\":\"Mo\",\"exe_type\":\"thigh\",\"exe_name\":\"Lunge\",\"num_set\":\"4\",\"num_repeat\":\"20\"},{\"day\":\"Mo\",\"exe_type\":\"thigh\",\"exe_name\":\"Deep squat\",\"num_set\":\"4\",\"num_repeat\":\"20\"},{\"day\":\"Mo\",\"exe_type\":\"thigh\",\"exe_name\":\"Kick backward\",\"num_set\":\"4\",\"num_repeat\":\"15\\/side\"},{\"day\":\"Mo\",\"exe_type\":\"aerobic\",\"exe_name\":\"Jogging\",\"num_set\":\"1\",\"num_repeat\":\"45min\"}]}";
            TrainPlan trainPlan = new Gson().fromJson(response, TrainPlan.class);
            if (trainPlan != null) {
                //设置标题
                if (trainPlan.getResult()!=null && trainPlan.getResult().size()>0){
                    trainPlan.getResult().get(0).setHasTitle(true);
                }
                infoView.loadTrainPlan(trainPlan.getResult());
            } else {
                infoView.loadError("parse json error:" + response);
            }

            /*infoInf.loadTrainPlan(day, planName, new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    infoView.loadError("load data error" + e == null ? "" : e.getMessage());
                }

                @Override
                public void onResponse(String response) {
                    if (!StringUtil.isEmpty(response)) {
                        TrainPlan trainPlan = new Gson().fromJson(response, TrainPlan.class);
                        if (trainPlan != null) {
                         //设置标题
                            if (trainPlan.getResult()!=null && trainPlan.getResult().size()>0){
                                trainPlan.getResult().get(0).setHasTitle(true);
                            }
                            infoView.loadTrainPlan(trainPlan.getResult());
                        } else {
                            infoView.loadError("parse json error:"+response);
                        }
                    } else {
                        infoView.loadError("server response is null,please check:"+response);
                    }
                }
            });*/
        }

    }
}
