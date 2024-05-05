package student_attendance.request.student_request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class GetStudentByIdRequest {
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String id;

    @ConstructorProperties({"id"})
    public GetStudentByIdRequest(String id) {
        this.id = id;
    }
}
