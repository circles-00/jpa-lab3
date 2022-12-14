package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {
}
