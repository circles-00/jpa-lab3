package mk.ukim.finki.wp.lab.utils;

import mk.ukim.finki.wp.lab.model.Course;

import java.util.Comparator;
import java.util.List;

public class CommonUtils {
    public static List<Course> sortCourses(List<Course> courseList) {
        courseList.sort(Comparator.comparing(Course::getName));

        return courseList;
    }
}
