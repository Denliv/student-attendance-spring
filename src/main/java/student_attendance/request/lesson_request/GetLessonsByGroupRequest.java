package student_attendance.request.lesson_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.ConstructorProperties;

@Data
public class GetLessonsByGroupRequest {
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String startDate;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String endDate;
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String groupId;

    @ConstructorProperties({"startDate", "endDate", "groupId"})
    public GetLessonsByGroupRequest(String startDate, String endDate, String groupId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupId = groupId;
    }
}
