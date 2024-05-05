package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student_attendance.entity.Student;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, String> {
    @Transactional
    @Modifying
    @Query("UPDATE Student st " +
            "SET st.lastName = :lastName, " +
            "st.firstName = :firstName, " +
            "st.middleName = :middleName, " +
            "st.status = :status, " +
            "st.group.id = :groupId " +
            "WHERE st.id = :id")
    void update(@Param(value = "lastName") String lastName,
                @Param(value = "firstName") String firstName,
                @Param(value = "middleName") String middleName,
                @Param(value = "status") String status,
                @Param(value = "groupId") String groupId,
                @Param(value = "id") String id);

    @Transactional
    @Modifying
    @Query("SELECT st FROM Student st WHERE st.group.id = :groupId")
    List<Student> findAllByGroupId(@Param(value = "groupId") String groupId);
}