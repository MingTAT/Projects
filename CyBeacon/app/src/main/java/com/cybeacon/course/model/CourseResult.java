package com.cybeacon.course.model;

/**
 * @author Ming
 */
//store data and get data
public class CourseResult {

    private String courseName;

    public CourseResult(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
