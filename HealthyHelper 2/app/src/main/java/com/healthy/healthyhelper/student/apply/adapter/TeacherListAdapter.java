package com.healthy.healthyhelper.student.apply.adapter;

import android.content.Context;
import android.view.View;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.CommonBaseAdapter;
import com.healthy.healthyhelper.base.CommonViewHolder;
import com.healthy.healthyhelper.base.Config;
import com.healthy.healthyhelper.model.Teacher;
import com.healthy.healthyhelper.student.apply.ApplyForTeacherFragment;
import com.healthy.healthyhelper.student.apply.persenter.ApplyForTeacherPresenter;
import com.healthy.healthyhelper.ui.MsgDialog;
import com.healthy.healthyhelper.util.SPUtils;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class TeacherListAdapter extends CommonBaseAdapter<Teacher> {
    ApplyForTeacherPresenter presenter;
    /**
     * <默认构�?函数>
     *
     * @param context
     * @param mLayoutid
     * @param mDatas
     */
    public TeacherListAdapter(Context context, int mLayoutid, List<Teacher> mDatas) {
        super(context, mLayoutid, mDatas);
    }

    public TeacherListAdapter(Context context, ApplyForTeacherPresenter presenter, int mLayoutid, List<Teacher> mDatas) {
        super(context, mLayoutid, mDatas);
        this.presenter = presenter;
    }


    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        final Teacher teacher = getItem(position);
        mHolder.setText(R.id.nameTV,teacher.getName());
        mHolder.setOnclickListener(R.id.applyButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgDialog(getContext())
                        .title("确认申请吗?")
                        .message("确认申请"+teacher.getName()+"老师的训练课程吗")
                        .leftText("取消").rightText("确认")
                        .onClicklistener(new MsgDialog.DialogClickListener() {
                            @Override
                            public void leftButtonClick() {

                            }

                            @Override
                            public void rightButtonClick() {
                                presenter.applyForTeacher(teacher, (String) SPUtils.get(getContext(), Config.USERNAME_SHARED_PREF,"null"));
                            }
                        })
                        .show();
            }
        });
    }
}
