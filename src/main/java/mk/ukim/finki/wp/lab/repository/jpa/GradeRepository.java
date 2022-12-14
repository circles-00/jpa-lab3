package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    @Query(value = "SELECT g FROM Grade g WHERE g.student.username = ?1 AND g.course.courseId = ?2")
    Grade findByStudentAndCourse(String studentUsername, Long courseId);
}
