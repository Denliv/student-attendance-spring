package student_attendance.response.student_group_response;

import lombok.Data;
import student_attendance.entity.StudentGroup;

import java.beans.ConstructorProperties;

@Data
public class GetStudentGroupByIdResponse {
    private final String id;
    private final String name;

    @ConstructorProperties({"id", "name"})
    public GetStudentGroupByIdResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GetStudentGroupByIdResponse(StudentGroup group) {
        this(group.getId(), group.getName());
    }
}
