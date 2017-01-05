package com.healthy.healthyhelper.teacher.sendnotification.adapter;

import android.content.Context;
import android.view.View;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.CommonBaseAdapter;
import com.healthy.healthyhelper.base.CommonViewHolder;
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.model.Group;
import com.healthy.healthyhelper.teacher.sendnotification.presenter.SendNotificationPresenter;
import com.healthy.healthyhelper.ui.SendMsgDialog;
import com.healthy.healthyhelper.util.SPUtils;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class GroupListAdapter extends CommonBaseAdapter<Group>{
    /**
     * <默认构�?函数>
     *
     * @param context
     * @param mLayoutid
     * @param mDatas
     */
    public GroupListAdapter(Context context, int mLayoutid, List<Group> mDatas) {
        super(context, mLayoutid, mDatas);
    }
    SendNotificationPresenter presenter;
    public GroupListAdapter(Context context,SendNotificationPresenter presenter, int mLayoutid, List<Group> mDatas) {
        super(context, mLayoutid, mDatas);
        this.presenter = presenter;
    }
    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        final Group group = getItem(position);
        mHolder.setText(R.id.nameTV,group.getName());
        mHolder.setOnclickListener(R.id.sendBT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendMsgDialog(getContext()).leftText(null).rightText("发送")
                        .onClicklistener(new SendMsgDialog.DialogClickListener() {
                            @Override
                            public void leftButtonClick() {

                            }

                            @Override
                            public void rightButtonClick(String content) {
                                presenter.sendNotificationg((String) SPUtils.get(getContext(), Config.USERNAME_SHARED_PREF, "null"),content);
                            }
                        }).show();

            }
        });
    }
}
