package student_attendance.response.student_response;

import lombok.Data;
import student_attendance.entity.Student;
import student_attendance.response.student_group_response.GetStudentGroupByIdResponse;

import java.beans.ConstructorProperties;

@Data
public class GetStudentByIdResponse {
    private final String id;
    private final String lastName;
    private final String firstName;
    private final String middleName;
    private final String status;
    private final GetStudentGroupByIdResponse group;

    @ConstructorProperties({"id", "lastName", "firstName", "middleName", "status", "group"})
    public GetStudentByIdResponse(String id, String lastName, String firstName, String middleName, String status, GetStudentGroupByIdResponse group) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.status = status;
        this.group = group;
    }

    public GetStudentByIdResponse(Student student) {
        this(student.getId(), student.getLastName(), student.getFirstName(), student.getMiddleName(), student.getStatus(), new GetStudentGroupByIdResponse(student.getGroup()));
    }
}
