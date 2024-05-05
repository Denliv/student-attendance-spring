package student_attendance.entity;

public enum StudentStatus {
    STUDYING("STUDYING"),
    ACADEMIC_LEAVE("ACADEMIC_LEAVE"),
    EXPELLED("EXPELLED");
    private final String name;

    StudentStatus(String name) {
        this.name = name;
    }
}
