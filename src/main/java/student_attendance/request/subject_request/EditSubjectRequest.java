package student_attendance.request.subject_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.beans.ConstructorProperties;

@Data
public class EditSubjectRequest {
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String id;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String name;

    @ConstructorProperties({"id", "name"})
    public EditSubjectRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
