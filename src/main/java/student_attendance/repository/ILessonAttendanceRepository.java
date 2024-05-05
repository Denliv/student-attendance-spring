package student_attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student_attendance.entity.LessonAttendance;

public interface ILessonAttendanceRepository extends JpaRepository<LessonAttendance, String> {
}
