package com.cybeacon.base;

import android.content.Context;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * @author Ming
 */
public abstract  class BaseCommonAdapter<T> extends CommonAdapter<T> {

    public BaseCommonAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
    }

    public void addDatas(List<T> mDatas){
        if (this.mDatas != null && mDatas != null){
            int startPos = this.mDatas.size()>0?this.mDatas.size():0;
            this.mDatas.addAll(mDatas);
            notifyItemRangeChanged(startPos,mDatas.size());
        }
    }

    public void refreshDatas(List<T> mDatas) {
        if (this.mDatas!=null){
            this.mDatas.clear();
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();
    }
}
