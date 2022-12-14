package mk.ukim.finki.wp.lab.repository.in_memory;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
    public static final List<Student> studentList = new ArrayList<>();

    @PostConstruct
    public void init() {
        studentList.add(new Student("darknet", "123321", "Nikola", "Krezeski"));
        studentList.add(new Student("circles", "123321", "Ana", "De Armas"));
        studentList.add(new Student("dicaprio", "123321", "Leonardo", "DiCaprio"));
        studentList.add(new Student("hardy123", "123321", "Tom", "Hardy"));
        studentList.add(new Student("murphy", "123321", "Cylian", "Murphy"));
    }

    public List<Student> findAllStudents() {
        return studentList;
    }

    public List<Student> findAllByNameOrSurname(String query) {
        return studentList
                .stream()
                .filter(student -> student.getName().contains(query) || student.getSurname().contains(query))
                .collect(Collectors.toList());
    }

    public Student addStudent(Student student) {
        studentList.add(student);

        return student;
    }

    public Student findByUsername(String username) {
        return studentList.stream().filter(student -> student.getUsername().equals(username)).findFirst().orElse(null);
    }
}
