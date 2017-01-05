package com.healthy.healthyhelper.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * author Ming
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter
{
    
    private List<T> mDatas;
    
    private int mlayoutId;
    
    private Context mContext;
    
    /**
     * <默认构�?函数>
     * 
     *
     */
    public CommonBaseAdapter(Context context, int mLayoutid, List<T> mDatas)
    {
        this.mlayoutId = mLayoutid;
        this.mContext = context;
        this.mDatas = mDatas;
    }
    
    public abstract void convert(int position, CommonViewHolder mHolder);
    
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
        CommonViewHolder mHolder = CommonViewHolder.getHolder(mContext, mlayoutId, arg0, arg1, arg2);
        // 用户自己定制
        convert(arg0, mHolder);

        return mHolder.getConvertView();
    }


    public void refreshDatas(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
       // this.mDatas = mDatas;
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



}
