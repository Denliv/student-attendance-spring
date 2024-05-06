package student_attendance.response.lesson_response;

import lombok.Data;
import student_attendance.entity.Lesson;

import java.beans.ConstructorProperties;

@Data
public class AddLessonResponse {
    private final String id;

    @ConstructorProperties({"id"})
    public AddLessonResponse(String id) {
        this.id = id;
    }

    public AddLessonResponse(Lesson lesson) {
        this(lesson.getId());
    }
}
