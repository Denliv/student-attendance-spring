package student_attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student_attendance.entity.Lesson;

public interface ILessonRepository extends JpaRepository<Lesson, String> {
}
