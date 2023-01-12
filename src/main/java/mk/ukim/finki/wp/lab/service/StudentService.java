package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> listAll();

    List<Student> searchByNameOrSurname(String name, String surname);

    Student save(String username, String password, String name, String surname) throws Exception;

    List<Student> getFilteredStudents(List<Student> filterList);

    Student getByUsername(String username);
}
