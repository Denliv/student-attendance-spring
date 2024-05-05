package student_attendance.response.lesson_response;

import lombok.Data;
import student_attendance.entity.Lesson;
import student_attendance.response.student_group_response.GetStudentGroupByIdResponse;
import student_attendance.response.subject_response.GetSubjectByIdResponse;
import student_attendance.response.teacher_response.GetTeacherByIdResponse;

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
