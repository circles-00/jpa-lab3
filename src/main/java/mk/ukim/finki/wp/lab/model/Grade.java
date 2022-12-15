package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grade;
    @ManyToOne
    private Student student;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Course course;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public Grade(int grade, LocalDateTime timestamp, Student student, Course course) {
        this.grade = grade;
        this.timestamp = timestamp;
        this.student = student;
        this.course = course;
    }

    public Grade() {

    }
}
