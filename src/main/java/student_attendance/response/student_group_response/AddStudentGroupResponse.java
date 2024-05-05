package student_attendance.response.student_group_response;

import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class AddStudentGroupResponse {
    private final String id;
    private final String name;

    @ConstructorProperties({"id", "name"})
    public AddStudentGroupResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
