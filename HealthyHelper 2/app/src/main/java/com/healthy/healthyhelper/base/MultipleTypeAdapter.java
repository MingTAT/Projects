package com.healthy.healthyhelper.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public abstract class MultipleTypeAdapter<T> extends BaseAdapter
{

    private List<T> mDatas;

    private SparseArray<Integer> mLayoutIds;

    private Context mContext;

    /**
     * <默认构�?函数>
     *
     *
     */
    public MultipleTypeAdapter(Context context, SparseArray<Integer> mLayoutIds, List<T> mDatas)
    {
        this.mLayoutIds = mLayoutIds;
        this.mContext = context;
        this.mDatas = mDatas;
    }
    
    public abstract void convert(int position, CommonViewHolder mHolder);
    public abstract int getMultipleTypeCount();
    public abstract int getItemType(int position);

    /**
     * 重载方法
     * 
     * @return
     */
    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    public void delItem(int arg0){
        mDatas.remove(arg0);
        this.notifyDataSetChanged();
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @return
     */
    @Override
    public T getItem(int arg0)
    {
        return mDatas.get(arg0);
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @return
     */
    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     */
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2)
    {
        //获取类型
        int type = getItemType(arg0);
        if (type >mLayoutIds.size()-1||type<0){
            type = 0;
        }
        int mlayoutId = mLayoutIds.get(type);
        CommonViewHolder mHolder = CommonViewHolder.getHolder(mContext, mlayoutId, arg0, arg1, arg2);
        // 用户自己定制
        convert(arg0, mHolder);

        return mHolder.getConvertView();
    }

    public void refreshDatas(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        this.notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }

    public void addDatas(List<T> mDatas){
        if (mDatas!=null&&mDatas.size()>0){
            this.mDatas.addAll(mDatas);
            this.notifyDataSetChanged();
        }
    }

    public void addItem(T t){
        this.mDatas.add(t);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        return getItemType(position);
    }

    @Override
    public int getViewTypeCount() {
        return getMultipleTypeCount();
    }
}
