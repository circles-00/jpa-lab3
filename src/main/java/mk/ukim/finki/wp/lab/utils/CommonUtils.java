package mk.ukim.finki.wp.lab.utils;

import mk.ukim.finki.wp.lab.model.Course;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommonUtils {
    private static String sortingOrder = "ascending";
    public static List<Course> sortCourses(List<Course> courseList, String sortOrder) {

        if(sortOrder.equals("ascending")) {
            courseList.sort(Comparator.comparing(Course::getName));
        } else {
            Collections.reverse(courseList);
        }
        sortingOrder = sortOrder;
        return courseList;
    }

    public static String getSortOrder() {
        return sortingOrder;
    }

    public static String getNextSortOrder () {
        return CommonUtils.getSortOrder().equals("ascending") ? "descending" : "ascending";
    }
}
