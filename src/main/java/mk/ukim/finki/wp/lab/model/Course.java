package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.enums.Type;
import mk.ukim.finki.wp.lab.utils.RepoUtils;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students;
    @ManyToOne
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "course")
    List<Grade> grades;

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

    public Course() {

    }
}
