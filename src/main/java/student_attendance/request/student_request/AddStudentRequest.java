package student_attendance.request.student_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import student_attendance.annotation.IsEnum;
import student_attendance.entity.StudentStatus;

import java.beans.ConstructorProperties;

@Data
public class AddStudentRequest {
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
    @IsEnum(enumClass = StudentStatus.class)
    private final String status;
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String groupId;

    @ConstructorProperties({"lastName", "firstName", "middleName", "status", "groupId"})
    public AddStudentRequest(String lastName, String firstName, String middleName, String status, String groupId) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.status = status;
        this.groupId = groupId;
    }
}
