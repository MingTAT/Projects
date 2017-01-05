package com.healthy.healthyhelper.teacher.studentlist.adapter;

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
public class StudentListAdapter extends CommonBaseAdapter<StudentResponse.ResultBean> {


    /**
     * <默认构�?函数>
     *
     * @param context
     * @param mLayoutid
     * @param mDatas
     */
    public StudentListAdapter(Context context, int mLayoutid, List<StudentResponse.ResultBean> mDatas) {
        super(context, mLayoutid, mDatas);
    }

    @Override
    public void convert(int position, CommonViewHolder mHolder) {
        StudentResponse.ResultBean student = getItem(position);
        mHolder.setText(R.id.nameTV,student.getStudent());
    }

}
