package student_attendance.response.student_response;

import lombok.Data;
import student_attendance.entity.Student;

import java.beans.ConstructorProperties;

@Data
public class AddStudentResponse {
    private final String id;

    @ConstructorProperties({"id"})
    public AddStudentResponse(String id) {
        this.id = id;
    }

    public AddStudentResponse(Student student) {
        this(student.getId());
    }
}
