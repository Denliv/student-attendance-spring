package student_attendance.response.subject_response;

import lombok.Data;
import student_attendance.entity.Subject;

import java.beans.ConstructorProperties;

@Data
public class GetSubjectByIdResponse {
    private final String id;
    private final String name;

    @ConstructorProperties({"id", "name"})
    public GetSubjectByIdResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GetSubjectByIdResponse(Subject subject) {
        this(subject.getId(), subject.getName());
    }
}
