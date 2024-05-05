package student_attendance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_group")
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public StudentGroup() {
    }

    public StudentGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
