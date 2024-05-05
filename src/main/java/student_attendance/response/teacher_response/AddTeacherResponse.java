package student_attendance.response.teacher_response;

import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class AddTeacherResponse {
    private final String id;

    @ConstructorProperties({"id"})
    public AddTeacherResponse(String id) {
        this.id = id;
    }
}
