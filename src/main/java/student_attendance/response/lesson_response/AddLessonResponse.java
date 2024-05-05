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
    private final GetSubjectByIdResponse subject;
    private final String date;
    private final int number;
    private final GetTeacherByIdResponse teacher;
    private final GetStudentGroupByIdResponse group;

    @ConstructorProperties({"id", "subject", "date", "number", "teacher", "group"})
    public AddLessonResponse(String id,
                             GetSubjectByIdResponse subject,
                             String date,
                             int number,
                             GetTeacherByIdResponse teacher,
                             GetStudentGroupByIdResponse group) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.number = number;
        this.teacher = teacher;
        this.group = group;
    }

    public AddLessonResponse(Lesson lesson) {
        this(
                lesson.getId(),
                new GetSubjectByIdResponse(lesson.getSubject()),
                lesson.getDate(),
                lesson.getNumber(),
                new GetTeacherByIdResponse(lesson.getTeacher()),
                new GetStudentGroupByIdResponse(lesson.getGroup())
        );
    }
}
