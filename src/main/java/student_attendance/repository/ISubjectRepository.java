package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student_attendance.entity.Subject;

public interface ISubjectRepository extends JpaRepository<Subject, String> {
    @Transactional
    @Modifying
    @Query("UPDATE Subject sb " +
            "SET sb.name = :name " +
            "WHERE sb.id = :id")
    void update(@Param(value = "name") String name,
                @Param(value = "id") String id);
}
