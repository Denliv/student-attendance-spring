package student_attendance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;
    @Column(name = "date", nullable = false)
    private String date;
    @Column(name = "number", nullable = false)
    private int number;
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
    @ManyToOne
    @JoinColumn(name = "student_group_id", referencedColumnName = "id")
    private StudentGroup group;

    public Lesson() {
    }

    public Lesson(String id, Subject subject, String date, int number, Teacher teacher, StudentGroup group) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.number = number;
        this.teacher = teacher;
        this.group = group;
    }

    public Lesson(Lesson lesson) {
        this(lesson.getId(), lesson.getSubject(), lesson.getDate(), lesson.getNumber(), lesson.getTeacher(), lesson.getGroup());
    }
}
