package mk.ukim.finki.wp.lab.repository;

import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
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
        coursesList.add(new Course("Algorithms & Data structures", "Algorithms & Data structures", new ArrayList<>()));
        coursesList.add(new Course("Calculus 2", "Calculus 2", new ArrayList<>()));
        coursesList.add(new Course("Probability & Statistics", "Probability & Statistics", new ArrayList<>()));
        coursesList.add(new Course("Operating Systems", "Operating Systems", new ArrayList<>()));
        coursesList.add(new Course("Computer Networks & Security", "Computer Networks & Security", new ArrayList<>()));
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
        if (!course.getStudents().contains(student))
            course.getStudents().add(student);
        if (this.findById(course.getCourseId()) == null)
            coursesList.add(course);
        return course;
    }

    public void saveCourse(Course course) {
        coursesList.add(course);
    }

    public void deleteCourse(Course course) {
        coursesList.remove(course);
//        coursesList.removeIf(courseFromDb -> course.getCourseId().equals(courseFromDb.getCourseId()));
    }

    public void editCourse(Course course, Teacher teacher, String courseName, String courseDescription) {
        course.setName(courseName);
        course.setDescription(courseDescription);
        course.setTeacher(teacher);
    }
}
