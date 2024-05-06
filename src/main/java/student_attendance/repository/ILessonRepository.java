package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student_attendance.entity.Lesson;
import student_attendance.entity.LessonAttendance;

import java.util.List;

public interface ILessonRepository extends JpaRepository<Lesson, String> {
    @Transactional
    @Query("SELECT ls FROM Lesson ls WHERE ls.group.id = :groupId AND ls.date >= :startDate AND ls.date <= :endDate")
    List<Lesson> findLessonByGroupId(@Param(value = "groupId") String groupId,
                                     @Param(value = "startDate") String startDate,
                                     @Param(value = "endDate") String endDate);

    @Transactional
    @Query("SELECT ls FROM Lesson ls WHERE ls.teacher.id = :teacherId AND ls.date >= :startDate AND ls.date <= :endDate")
    List<Lesson> findLessonByTeacherId(@Param(value = "teacherId") String teacherId,
                                       @Param(value = "startDate") String startDate,
                                       @Param(value = "endDate") String endDate);

    @Transactional
    @Modifying
    @Query("UPDATE Lesson ls " +
            "SET ls.subject.id = :subjectId, " +
            "ls.date = :date, " +
            "ls.number = :number, " +
            "ls.teacher.id = :teacherId, " +
            "ls.group.id = :groupId " +
            "WHERE ls.id = :id")
    void update(@Param(value = "subjectId") String subjectId,
                @Param(value = "date") String date,
                @Param(value = "number") int number,
                @Param(value = "teacherId") String teacherId,
                @Param(value = "groupId") String groupId,
                @Param(value = "id") String id);
}
