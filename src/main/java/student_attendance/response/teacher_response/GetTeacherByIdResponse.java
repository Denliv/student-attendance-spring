package student_attendance.response.teacher_response;

import lombok.Data;
import student_attendance.entity.Teacher;

import java.beans.ConstructorProperties;

@Data
public class GetTeacherByIdResponse {
    private final String id;
    private final String lastName;
    private final String firstName;
    private final String middleName;

    @ConstructorProperties({"id", "lastName", "firstName", "middleName"})
    public GetTeacherByIdResponse(String id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public GetTeacherByIdResponse(Teacher teacher) {
        this(teacher.getId(), teacher.getLastName(), teacher.getFirstName(), teacher.getMiddleName());
    }
}
