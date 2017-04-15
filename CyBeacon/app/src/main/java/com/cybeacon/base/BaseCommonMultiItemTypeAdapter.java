package com.cybeacon.base;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author Ming
 */
public class BaseCommonMultiItemTypeAdapter<T> extends MultiItemTypeAdapter {
    private  List<T> mDatas ;
    public BaseCommonMultiItemTypeAdapter(Context context, List<T> datas) {
        super(context, datas);
        this.mDatas = datas;
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
