package student_attendance.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student_attendance.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Transactional
    @Query("SELECT us FROM User us WHERE us.name = :userName")
    Optional<User> findByName(@Param(value = "userName") String username);
}
