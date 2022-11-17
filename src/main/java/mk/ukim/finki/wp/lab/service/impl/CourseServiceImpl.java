package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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

    @Override
    public void saveCourse(String courseName, String courseDescription, Long teacherId) throws Exception {
        Teacher teacher = teacherRepository.findById(teacherId);
        if(teacher == null) throw new Exception("Teacher cannot be found");
        List<Course> courses = this.courseRepository.findAllCourses();
        if(courses.stream().filter(courseFromDb -> courseFromDb.getName().equals(courseName)).findFirst().orElse(null) != null)
            throw new Exception("Cannot create course, course with name: " + courseName + " already exists!");

        Course newCourse = new Course(courseName, courseDescription, new ArrayList<>(), teacher);

        courseRepository.saveCourse(newCourse);
    }

    @Override
    public void deleteCourse(Long courseId) throws Exception {
        Course course = this.findById(courseId);
        if(course == null) throw new Exception("Course cannot be found");

        this.courseRepository.deleteCourse(course);
    }

    @Override
    public void editCourse(Long courseId, String courseName, String courseDescription, Long teacherId) throws Exception {
        Course course = this.findById(courseId);
        if(course == null) throw new Exception("Course cannot be found");
        Teacher teacher = teacherRepository.findById(teacherId);

        this.courseRepository.editCourse(course, teacher, courseName, courseDescription);
    }
}
