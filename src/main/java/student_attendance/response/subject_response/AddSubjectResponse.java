package student_attendance.response.subject_response;

import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class AddSubjectResponse {
    private final String id;

    @ConstructorProperties({"id"})
    public AddSubjectResponse(String id) {
        this.id = id;
    }
}
