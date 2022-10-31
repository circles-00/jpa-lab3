package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Course> listAll() {
        return this.courseRepository.findAllCourses();
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return this.courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course courseFromDb = this.findById(courseId);
        Student studentFromDb = this.studentRepository.findByUsername(username);

        if(courseFromDb == null || studentFromDb == null) return null;

        return this.courseRepository.addStudentToCourse(studentFromDb, courseFromDb);
    }

    @Override
    public Course findById(Long courseId) {
        return this.courseRepository.findById(courseId);
    }
}
