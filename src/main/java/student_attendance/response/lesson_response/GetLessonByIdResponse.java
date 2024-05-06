package student_attendance.response.lesson_response;

import lombok.Data;
import student_attendance.entity.LessonAttendance;
import student_attendance.entity.Lesson;
import student_attendance.entity.Student;
import student_attendance.response.student_group_response.GetStudentGroupByIdResponse;
import student_attendance.response.student_response.GetStudentByIdResponse;
import student_attendance.response.subject_response.GetSubjectByIdResponse;
import student_attendance.response.teacher_response.GetTeacherByIdResponse;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetLessonByIdResponse {
    private final String id;
    private final GetSubjectByIdResponse subject;
    private final String date;
    private final int number;
    private final GetTeacherByIdResponse teacher;
    private final GetStudentGroupByIdResponse group;
    private List<GetStudentByIdResponse> students = new ArrayList<>();

    @ConstructorProperties({"id", "subject", "date", "number", "teacher", "group", "list"})
    public GetLessonByIdResponse(String id,
                                 GetSubjectByIdResponse subject,
                                 String date,
                                 int number,
                                 GetTeacherByIdResponse teacher,
                                 GetStudentGroupByIdResponse group,
                                 List<GetStudentByIdResponse> list) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.number = number;
        this.teacher = teacher;
        this.group = group;
        if (list == null) {
            students = null;
        } else {
            students.addAll(list);
        }
    }

    public GetLessonByIdResponse(Lesson lesson, LessonAttendance lessonAttendance) {
        this(
                lesson.getId(),
                new GetSubjectByIdResponse(lesson.getSubject()),
                lesson.getDate(),
                lesson.getNumber(),
                new GetTeacherByIdResponse(lesson.getTeacher()),
                new GetStudentGroupByIdResponse(lesson.getGroup()),
                lessonAttendance == null ? null : lessonAttendance.getStudents().stream().map(GetStudentByIdResponse::new).toList()
        );
    }
}
