package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.utils.RepoUtils;

import java.util.List;

@Data
public class Course {
    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;

    public Course(String name, String description, List<Student> students) {
        this.courseId = RepoUtils.generatePrimaryKey();
        this.name = name;
        this.description = description;
        this.students = students;
    }

    public Course(String name, String description, List<Student> students, Teacher teacher) {
        this.courseId = RepoUtils.generatePrimaryKey();
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
    }
}
