package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl (StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String name, String surname) {
        return this.studentRepository.findAllByNameOrSurname(name, surname);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        return this.studentRepository.save(new Student(username, password, name, surname));
    }

    @Override
    public List<Student> getFilteredStudents(List<Student> filterList) {
        return this.studentRepository.findAll().stream().filter(student -> filterList.stream().noneMatch(filterStudent -> filterStudent.getUsername().equals(student.getUsername()))).collect(Collectors.toList());
    }
}
