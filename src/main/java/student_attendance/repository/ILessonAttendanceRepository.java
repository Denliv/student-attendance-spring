package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student_attendance.entity.LessonAttendance;
import student_attendance.entity.Student;

import java.util.List;

public interface ILessonAttendanceRepository extends JpaRepository<LessonAttendance, String> {
    @Transactional
    @Query("SELECT la FROM LessonAttendance la WHERE la.lesson.id = :lessonId")
    LessonAttendance findAttendanceByLessonId(@Param(value = "lessonId") String lessonId);
}
