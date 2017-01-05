package com.healthy.healthyhelper.base;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * author Ming
 */
public class CommonViewHolder
{
    
    
    /**
     * 加载的布�?���?
     */
    public View convertView;
    
    /**
     * 存储布局视图子控�?
     */
    private SparseArray<View> mViews;
    
    private int mPosiontion;
    
    private Context context;
    
    

    /**
     * <默认构�?函数>
     */
    public CommonViewHolder()
    {
        super();
    }
    
    /**
     * <默认构�?函数>
     */
    public CommonViewHolder(Context context, int layoutId, ViewGroup parent, int position)
    {        
        this.convertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        this.mPosiontion = position;
        this.context = context;
        convertView.setTag(this);
        mViews = new SparseArray<View>();
    }
    
    /** 
     * 获取�?��通用的CommonViewHolder
     * 
     * @param mContext
     * @param layoutId 要填充的item layoutId
     * @param position 适配器中的位�?
     * @param convertView  填充的view
     * @param parent  父控�?
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static CommonViewHolder getHolder (Context mContext , int layoutId, int position, View convertView, ViewGroup parent)
    {
    
        if (convertView == null)
        {
            return new CommonViewHolder(mContext,layoutId, parent, position);
        }
        else
        {
            CommonViewHolder holder = (CommonViewHolder)convertView.getTag();
            return holder;
        }
    }
    
    public int getPosiontion()
    {
        return mPosiontion;
    }
    
    public View getConvertView()
    {
        return convertView;
    }

    public CommonViewHolder setOnclickListener(int layoutId,View.OnClickListener listener){
        View view = getViewById(layoutId);
        if (view != null){
            view.setOnClickListener(listener);
        }
        return this;
    }

    /** 
     *  为textView设置字符串内�?
     * @param layoutId
     * @param text
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public  CommonViewHolder setText(int layoutId,String text)
    {
        TextView mTextView = (TextView)getViewById(layoutId);
            //(TextView)mViews.get(layoutId);
        if (mTextView!=null)
        {
            mTextView.setText(text);
        }
        
        return this;
    }

    /**
     *
     * 设置字体颜色
     *
     * @param layoutId
     * @param color
     * @return
     */
    public CommonViewHolder setTextColor(int layoutId,int color){
        TextView mTextView = (TextView) getViewById(layoutId);
        if (mTextView!=null){
            mTextView.setTextColor(context.getResources().getColor(color));
        }
        return this;
    }


    /**
     * 为textview设置下划线
     *
     * @param layoutId
     * @return
     */
    public CommonViewHolder setTextUnderLine(int layoutId){
        TextView mTextView = (TextView)getViewById(layoutId);
        //(TextView)mViews.get(layoutId);
        if (mTextView!=null)
        {
            mTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        return this;
    }


    public CommonViewHolder inflateViewStub(int resId){
        ViewStub stub = (ViewStub) getViewById(resId);
        stub.inflate();
        return this;
    }

    public CommonViewHolder setSelected(int resId,boolean isSelected){
        View view  = getViewById(resId);
        if (view!=null){
            view.setSelected(isSelected);
        }
        return this;
    }
    
    /** 
     *  为ImageView设置图片资源
     *  
     * @param layoutId
     * @param resId
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public CommonViewHolder setImageRes(int layoutId,int resId)
    {
        ImageView mImageView  =  (ImageView)getViewById(layoutId);
        if (mImageView!=null)
        {
            mImageView.setImageResource(resId);
        }
        
        return this ;
    }

    public  enum  DawableDrection  {
            top,bottom,left,right
    }



    public CommonViewHolder setTextDrawable(int resId,int drawableId,DawableDrection drection){
        if (getViewById(resId) instanceof  TextView){
            TextView textView = (TextView) getViewById(resId);
            if (drawableId == 0){
                textView.setCompoundDrawables(null,null,null,null);
            }else {
                Drawable drawable=context.getResources().getDrawable(drawableId);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                switch (drection){
                    case  bottom:
                        textView.setCompoundDrawables(null,null,null,drawable);
                        break;
                    case top:
                        textView.setCompoundDrawables(null,drawable,null,null);
                        break;
                    case left:
                        textView.setCompoundDrawables(drawable,null,null,null);
                        break;
                    case right:
                        textView.setCompoundDrawables(null,null,drawable,null);
                        break;
                }
            }

        }
         return this;
}


    
    public CommonViewHolder setBakRes(int layoutId,int resId)
    {
        ImageView mImageView  =  (ImageView)getViewById(layoutId);
        if (mImageView!=null)
        {
            mImageView.setBackgroundResource(resId);
        }
        
        return this ;
    }
    
    public CommonViewHolder setRating(int ratingBarId,float rating)
    {
    	RatingBar ratingBar = (RatingBar) getViewById(ratingBarId);
    	if (ratingBar!=null) {
    		ratingBar.setRating(rating);
		}
    	return this;
    }

    public CommonViewHolder setVisible(int resId,boolean visible){
        View view = getViewById(resId);
        if (view == null){
            return this;
        }
        if (visible){
            view .setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    
    /**
     * 根据id获取相应的view
     * 
     * @param id
     * @return
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public View getViewById(int id)
    {
        if (mViews.get(id) == null)
        {
            View mView = convertView.findViewById(id);
            mViews.put(id, mView);
            return mView;
        }
        return mViews.get(id);
    }
    
}
