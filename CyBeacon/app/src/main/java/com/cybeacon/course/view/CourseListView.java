package com.cybeacon.course.view;

import com.cybeacon.base.IBaseView;
import com.cybeacon.course.model.CourseResult;

import java.util.ArrayList;

public interface CourseListView extends IBaseView {
    /**
     * @param mDatas
     */
    void loadData(ArrayList<CourseResult> mDatas);
}
