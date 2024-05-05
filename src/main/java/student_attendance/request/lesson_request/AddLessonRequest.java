package student_attendance.request.lesson_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Data
public class AddLessonRequest {
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String subjectId;
    @NotNull
    @NotBlank
    @Length(min = 1, max = 100)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final String date;
    @NotNull
    @PositiveOrZero
    private final int number;
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String teacherId;
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private final String groupId;
    private final List<@Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$") String> attendanceList;

    @ConstructorProperties({"subjectId", "date", "number", "teacherId", "groupId", "attendanceList"})
    public AddLessonRequest(String subjectId, String date, int number, String teacherId, String groupId, List<String> attendanceList) {
        this.subjectId = subjectId;
        this.date = date;
        this.number = number;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.attendanceList = new ArrayList<>(attendanceList.size());
        this.attendanceList.addAll(attendanceList);
    }
}
