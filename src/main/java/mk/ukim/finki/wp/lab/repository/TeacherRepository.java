package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeacherRepository {
    public static final List<Teacher> teacherList = new ArrayList<>();

    @PostConstruct
    public void init() {
        teacherList.add(new Teacher("Ana", "Madevska Bogdanova"));
        teacherList.add(new Teacher("Marjan", "Gusev"));
        teacherList.add(new Teacher("Marija", "Mihova"));
        teacherList.add(new Teacher("Igor", "Miskovski"));
        teacherList.add(new Teacher("Biljana", "Tojtovska"));

    }

    public List<Teacher> findAll() {
        return teacherList;
    }

    public Teacher findById(Long teacherId) {
        return teacherList.stream().filter(teacher -> teacher.getId().equals(teacherId)).findFirst().orElse(null);
    }
}
