package student_attendance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "attendance_list")
public class LessonAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @OneToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id", unique = true)
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    public LessonAttendance() {
    }

    public LessonAttendance(String id, Lesson lesson, Student student) {
        this.id = id;
        this.lesson = lesson;
        this.student = student;
    }
}
