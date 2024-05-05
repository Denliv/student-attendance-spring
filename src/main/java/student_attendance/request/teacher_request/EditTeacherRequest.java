package student_attendance.request.teacher_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.beans.ConstructorProperties;

@Data
public class EditTeacherRequest {
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String id;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String lastName;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String firstName;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    private final String middleName;

    @ConstructorProperties({"id", "lastName", "firstName", "middleName"})
    public EditTeacherRequest(String id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }
}
