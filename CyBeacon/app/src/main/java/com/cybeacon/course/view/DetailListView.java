package com.cybeacon.course.view;

import com.cybeacon.base.IBaseView;
import com.cybeacon.course.ClassInfo;
import com.cybeacon.course.model.CourseResult;

import java.util.ArrayList;

/**
 * Created by Sam on 3/30/2017.
 */
public interface DetailListView extends IBaseView {
    void loadData(ArrayList<ClassInfo> classinfo);
}
