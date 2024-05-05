package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student_attendance.entity.Teacher;

public interface ITeacherRepository extends JpaRepository<Teacher, String> {
    @Transactional
    @Modifying
    @Query("UPDATE Teacher th " +
            "SET th.lastName = :lastName, " +
            "th.firstName = :firstName, " +
            "th.middleName = :middleName " +
            "WHERE th.id = :id")
    void update(@Param(value = "lastName") String lastName,
                @Param(value = "firstName") String firstName,
                @Param(value = "middleName") String middleName,
                @Param(value = "id") String id);
}
