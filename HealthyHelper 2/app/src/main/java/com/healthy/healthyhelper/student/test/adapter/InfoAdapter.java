package com.healthy.healthyhelper.student.test.adapter;

import android.content.Context;
import android.util.SparseArray;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.CommonViewHolder;
import com.healthy.healthyhelper.base.MultipleTypeAdapter;
import com.healthy.healthyhelper.model.FoodPlan;
import com.healthy.healthyhelper.model.TrainPlan;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class InfoAdapter extends MultipleTypeAdapter {

    private List mDatas;
    /**
     * <默认构�?函数>
     *
     * @param context
     * @param mLayoutIds
     * @param mDatas
     */
    public InfoAdapter(Context context, SparseArray<Integer> mLayoutIds, List mDatas) {
        super(context, mLayoutIds, mDatas);
        this.mDatas = mDatas;
    }

    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        int type = getItemType(position);

        if (type == 0){//Train
           TrainPlan.ResultBean trainBean = (TrainPlan.ResultBean) getItem(position);
            mHolder.setVisible(R.id.trainTitle,trainBean.isHasTitle());
            mHolder.setText(R.id.exe_type, trainBean.getExe_type());
            mHolder.setText(R.id.exe_name,trainBean.getExe_name());
            mHolder.setText(R.id.num_set,trainBean.getNum_set());
            mHolder.setText(R.id.num_repeat,trainBean.getNum_repeat());

        }else {//Food
            FoodPlan.ResultBean foodBean = (FoodPlan.ResultBean) getItem(position);
            mHolder.setVisible(R.id.foodTitle,foodBean.isHasTitle());
            mHolder.setText(R.id.breakfast,foodBean.getBreakfast());
            mHolder.setText(R.id.lunch,foodBean.getLunch());
            mHolder.setText(R.id.dinner,foodBean.getDinner());
        }
    }

    @Override
    public int getMultipleTypeCount() {
        return 2;
    }

    @Override
    public int getItemType(int position) {
        if (mDatas.get(position) instanceof  TrainPlan.ResultBean){
            return 0 ;
        }
        return 1;
    }

}
