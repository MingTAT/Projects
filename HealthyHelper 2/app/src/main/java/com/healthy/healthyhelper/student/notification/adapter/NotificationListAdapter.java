package com.healthy.healthyhelper.student.notification.adapter;

import android.content.Context;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.CommonBaseAdapter;
import com.healthy.healthyhelper.base.CommonViewHolder;
import com.healthy.healthyhelper.model.Announcement;
import com.healthy.healthyhelper.model.Notification;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class NotificationListAdapter extends CommonBaseAdapter<Announcement>{
    /**
     * <默认构�?函数>
     *  @param context
     * @param mLayoutid
     * @param mDatas
     */
    public NotificationListAdapter(Context context, int mLayoutid, List<Announcement> mDatas) {
        super(context, mLayoutid, mDatas);
    }

    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        Announcement notification = getItem(position);
        mHolder.setText(R.id.notificationTV,notification.getText());
        mHolder.setText(R.id.userName,notification.getUsername());
        mHolder.setText(R.id.time,notification.getTime());
    }
}
