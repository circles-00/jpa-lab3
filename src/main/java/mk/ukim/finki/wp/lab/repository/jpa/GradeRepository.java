package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    @Query(value = "SELECT g FROM Grade g WHERE g.student.username = ?1 AND g.course.courseId = ?2")
    Grade findByStudentAndCourse(String studentUsername, Long courseId);

    @Query(value = "SELECT g FROM Grade g WHERE g.student.username = ?1 AND g.course.courseId = ?2 AND g.timestamp >= ?3 AND g.timestamp <= ?4")
    Grade findGradeByTimestampBetween(String studentUsername, Long courseId, LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT fs FROM Grade fs WHERE fs.grade < 6")
    List<Grade> findFailedStudents();
}
