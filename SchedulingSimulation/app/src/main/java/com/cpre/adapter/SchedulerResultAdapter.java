package com.cpre.adapter;

import android.content.Context;

import com.cpre.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 *
 * author Ming
 */

public class SchedulerResultAdapter extends CommonAdapter<Integer> {
    public SchedulerResultAdapter(Context context, int layoutId, List<Integer> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Integer integer, int position) {
        holder.setText(R.id.indexText,position+"");
        if (integer == 0){
            holder.setText(R.id.valueText,"idle");
        }else {
            holder.setText(R.id.valueText,"T"+integer);
        }
    }
}
