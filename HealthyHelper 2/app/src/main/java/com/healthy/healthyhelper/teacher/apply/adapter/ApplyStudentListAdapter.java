package com.healthy.healthyhelper.teacher.apply.adapter;

import android.content.Context;
import android.view.View;

import com.healthy.healthyhelper.R;
import com.healthy.healthyhelper.base.CommonBaseAdapter;
import com.healthy.healthyhelper.base.CommonViewHolder;
import com.healthy.healthyhelper.model.Student;
import com.healthy.healthyhelper.model.StudentResponse;
import com.healthy.healthyhelper.teacher.apply.presenter.ApplyStudentPersenter;
import com.healthy.healthyhelper.ui.MsgDialog;

import java.util.List;

/**
 * Created by Ming on 2016/4/3.
 */
public class ApplyStudentListAdapter extends CommonBaseAdapter<StudentResponse.ResultBean> {

    /**
     *
     */
    ApplyStudentPersenter studentPersenter;
    /**
     * <默认构�?函数>
     *
     * @param context
     * @param mLayoutid
     * @param mDatas
     */
    public ApplyStudentListAdapter(Context context, int mLayoutid, List<StudentResponse.ResultBean> mDatas) {
        super(context, mLayoutid, mDatas);
    }
    public ApplyStudentListAdapter(Context context, ApplyStudentPersenter studentPersenter, int mLayoutid, List<StudentResponse.ResultBean> mDatas) {
        super(context, mLayoutid, mDatas);
        this.studentPersenter = studentPersenter;
    }

    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        final StudentResponse.ResultBean student = getItem(position);
        mHolder.setText(R.id.nameTV,student.getStudent());
        mHolder.setOnclickListener(R.id.refuseBT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgDialog(getContext()).message("确认拒绝"+student.getStudent()+"的申请吗?")
                        .onClicklistener(new MsgDialog.DialogClickListener() {
                            @Override
                            public void leftButtonClick() {

                            }

                            @Override
                            public void rightButtonClick() {
                                studentPersenter.auditReject(student);
                            }
                        }).leftText("取消").rightText("确认")
                        .show();
            }
        });
        mHolder.setOnclickListener(R.id.passedBT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgDialog(getContext()).message("确认通过"+student.getStudent()+"的申请吗?")
                        .onClicklistener(new MsgDialog.DialogClickListener() {
                            @Override
                            public void leftButtonClick() {

                            }

                            @Override
                            public void rightButtonClick() {
                                studentPersenter.auditPass(student);
                            }
                        }).leftText("取消").rightText("确认")
                        .show();
            }
        });
    }

}
