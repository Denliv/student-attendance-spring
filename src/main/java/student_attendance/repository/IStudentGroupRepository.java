package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import student_attendance.entity.StudentGroup;

@Repository
public interface IStudentGroupRepository extends JpaRepository<StudentGroup, String> {
    @Transactional
    @Modifying
    @Query("UPDATE StudentGroup stg " +
            "SET stg.name = :name " +
            "WHERE stg.id = :id")
    void update(@Param(value = "name") String name,
                @Param(value = "id") String id);
}
