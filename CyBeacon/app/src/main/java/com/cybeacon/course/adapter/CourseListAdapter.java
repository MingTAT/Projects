package com.cybeacon.course.adapter;

import android.content.Context;

import com.cybeacon.R;
import com.cybeacon.base.BaseCommonAdapter;
import com.cybeacon.course.model.CourseResult;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Ming
 */
public class CourseListAdapter extends BaseCommonAdapter<CourseResult> {
    public CourseListAdapter(Context context, int layoutId, List<CourseResult> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CourseResult courseResult, int position) {
        holder.setText(R.id.courseNameText,courseResult.getCourseName());
    }
}
