package mk.ukim.finki.wp.lab.repository;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CourseRepository {
    public static final List<Course> coursesList = new ArrayList<>();

    @PostConstruct
    public void init() {
        coursesList.add(new Course(1L, "Algorithms & Data structures", "Algorithms & Data structures", new ArrayList<Student>()));
        coursesList.add(new Course(2L, "Calculus 2", "Calculus 2", new ArrayList<Student>()));
        coursesList.add(new Course(3L, "Probability & Statistics", "Probability & Statistics", new ArrayList<Student>()));
        coursesList.add(new Course(4L, "Operating Systems", "Operating Systems", new ArrayList<Student>()));
        coursesList.add(new Course(5L, "Computer Networks & Security", "Computer Networks & Security", new ArrayList<Student>()));
    }

    public List<Course> findAllCourses() {
        return coursesList;
    }

    public Course findById(Long courseId) {
        return coursesList.stream().filter(course -> course.getCourseId().equals(courseId)).findFirst().orElse(null);
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
        return this.findById(courseId).getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
        if(!course.getStudents().contains(student))
            course.getStudents().add(student);
        if (this.findById(course.getCourseId()) == null)
            coursesList.add(course);
        return course;
    }
}
