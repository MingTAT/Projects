package com.cybeacon.course.adapter;

/**
 * Created by Sam on 3/30/2017.
 */
import android.app.Application;
import android.content.Context;

import com.cybeacon.R;
import com.cybeacon.base.BaseCommonAdapter;
import com.cybeacon.course.ClassInfo;
import com.cybeacon.course.Courses;
import com.cybeacon.course.model.CourseResult;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author Sam
 */
public class courseDetailListAdapter extends BaseCommonAdapter<CourseResult> {
    public courseDetailListAdapter(Context context, int layoutId, List<CourseResult> courseresult) {
        super(context, layoutId, courseresult);
    }

    @Override
    protected void convert(ViewHolder holder, CourseResult courseResult, int position) {
        holder.setText(R.id.classNameText, courseResult.getCourseName());
    }
}
