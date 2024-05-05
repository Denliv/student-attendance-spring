package student_attendance.service.service_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student_attendance.entity.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.*;
import student_attendance.request.lesson_request.*;
import student_attendance.response.lesson_response.AddLessonResponse;
import student_attendance.response.lesson_response.GetLessonByIdResponse;
import student_attendance.response.student_response.GetStudentByIdResponse;
import student_attendance.service.service_interface.ILessonService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private ILessonAttendanceRepository attendanceListRepository;
    @Autowired
    private IStudentGroupRepository studentGroupRepository;
    @Autowired
    private ITeacherRepository teacherRepository;
    @Autowired
    private ISubjectRepository subjectRepository;

    @Override
    public AddLessonResponse add(AddLessonRequest request) throws NotFoundService {
        studentGroupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        teacherRepository.findById(request.getTeacherId()).orElseThrow(() -> new NotFoundService("invalid teacher id"));
        subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new NotFoundService("invalid subject id"));
        Lesson lesson = new Lesson(
                null,
                new Subject(request.getSubjectId(), null),
                request.getDate(),
                request.getNumber(),
                new Teacher(request.getTeacherId(), null, null, null),
                new StudentGroup(request.getGroupId(), null));
        lessonRepository.save(lesson);
        List<LessonAttendance> lessonAttendance = new ArrayList<>(
                request.getAttendanceList().stream()
                        .map(o -> new LessonAttendance(null, lesson, new Student(o, null, null, null, null, null)))
                        .toList()
        );
        for (var i : lessonAttendance) {
            attendanceListRepository.save(i);
        }
//        attendanceListRepository.saveAll(lessonAttendance);
        return new AddLessonResponse(lesson);
    }

    @Override
    public void delete(DeleteLessonRequest request) throws NotFoundService {
        lessonRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid lesson id"));
        lessonRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditLessonRequest request) throws ServiceException {

    }

//    @Override
//    public GetLessonByIdResponse getById(GetLessonByIdRequest request) throws NotFoundService {
//        return new GetLessonByIdResponse(
//                lessonRepository
//                .findById(request.getId())
//                .orElseThrow(() -> new NotFoundService("invalid lesson id")),
//                attendanceListRepository.findById());
//    }

    @Override
    public List<GetLessonByIdResponse> getByGroup(GetLessonsByGroupRequest request) throws NotFoundService {
        return null;
    }

    @Override
    public List<GetStudentByIdResponse> getByTeacher(GetLessonsByTeacherRequest request) throws NotFoundService {
        return null;
    }
}
