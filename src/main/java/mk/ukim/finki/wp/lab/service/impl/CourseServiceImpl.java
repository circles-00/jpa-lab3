package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryCourseRepository;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryStudentRepository;
import mk.ukim.finki.wp.lab.repository.in_memory.InMemoryTeacherRepository;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return this.courseRepository.findAll();
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Optional<Course> course = this.courseRepository.findById(courseId);

        Course presentCourse = course.orElse(null);

        if(presentCourse != null) return presentCourse.getStudents();
        return new ArrayList<>();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course courseFromDb = this.findById(courseId);
        Student studentFromDb = this.studentRepository.findByUsername(username);

        if(courseFromDb == null || studentFromDb == null) return null;

        List<Student> studentsInCourse = courseFromDb.getStudents();
        studentsInCourse.add(studentFromDb);
        courseFromDb.setStudents(studentsInCourse);

        return this.courseRepository.save(courseFromDb);
    }

    @Override
    public Course findById(Long courseId) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        return course.orElse(null);
    }

    @Override
    public void saveCourse(String courseName, String courseDescription, Long teacherId) throws Exception {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        if(teacher.isEmpty()) throw new Exception("Teacher cannot be found");
        List<Course> courses = this.courseRepository.findAll();
        if(courses.stream().filter(courseFromDb -> courseFromDb.getName().equals(courseName)).findFirst().orElse(null) != null)
            throw new Exception("Cannot create course, course with name: " + courseName + " already exists!");

        Course newCourse = new Course(courseName, courseDescription, new ArrayList<>(), teacher.get());

        courseRepository.save(newCourse);
    }

    @Override
    public void deleteCourse(Long courseId) throws Exception {
        Course course = this.findById(courseId);
        if(course == null) throw new Exception("Course cannot be found");

        this.courseRepository.delete(course);
    }

    @Override
    public void editCourse(Long courseId, String courseName, String courseDescription, Long teacherId) throws Exception {
        Course course = this.findById(courseId);
        if(course == null) throw new Exception("Course cannot be found");
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        course.setTeacher(teacher.orElse(null));
        course.setName(courseName);
        course.setDescription(courseDescription);

        this.courseRepository.save(course);
    }
}
