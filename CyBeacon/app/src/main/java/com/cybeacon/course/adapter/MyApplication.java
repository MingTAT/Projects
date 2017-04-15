package com.cybeacon.course.adapter;

import android.app.Application;

import com.cybeacon.course.ClassInfo;

import java.util.ArrayList;


public class MyApplication extends Application {

    private ArrayList<ClassInfo> ClassList;

    public ArrayList<ClassInfo> getClassList() {
        return ClassList;
    }

    public void setclassList(ArrayList<ClassInfo> list) {
        this.ClassList = list;
    }
}
