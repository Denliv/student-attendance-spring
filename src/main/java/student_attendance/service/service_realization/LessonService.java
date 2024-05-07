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
import student_attendance.service.service_interface.ILessonService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonService implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private ILessonAttendanceRepository lessonAttendanceRepository;
    @Autowired
    private IStudentGroupRepository groupRepository;
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private ITeacherRepository teacherRepository;
    @Autowired
    private ISubjectRepository subjectRepository;

    @Override
    public AddLessonResponse add(AddLessonRequest request) throws ServiceException {
        groupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        teacherRepository.findById(request.getTeacherId()).orElseThrow(() -> new NotFoundService("invalid teacher id"));
        subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new NotFoundService("invalid subject id"));
        if (request.getAttendanceList() != null) {
            List<String> attendanceList = request.getAttendanceList();
            for (var id : attendanceList) {
                Student student = studentRepository.findById(id).orElseThrow(() -> new ServiceException("invalid student id"));
                String groupId = student.getGroup().getId();
                if (!groupId.equals(request.getGroupId())) {
                    throw new ServiceException("all students from attendance list should be from group with id " + request.getGroupId());
                }
            }
        }
        Lesson lesson = new Lesson(
                null,
                new Subject(request.getSubjectId(), null),
                request.getDate(),
                request.getNumber(),
                new Teacher(request.getTeacherId(), null, null, null),
                new StudentGroup(request.getGroupId(), null));
        var lessonSave = lessonRepository.save(lesson);
        if (request.getAttendanceList() != null) {
            LessonAttendance lessonAttendance = new LessonAttendance(
                    null, lessonSave, request.getAttendanceList().isEmpty()
                    ? new ArrayList<>()
                    : request.getAttendanceList().stream()
                    .map(o -> new Student(o, null, null, null, null, null))
                    .toList()
            );
            lessonAttendanceRepository.save(lessonAttendance);
        }
        return new AddLessonResponse(lessonSave);
    }

    @Override
    public void delete(DeleteLessonRequest request) throws NotFoundService {
        lessonRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid lesson id"));
        lessonAttendanceRepository.deleteAttendanceByLessonId(request.getId());
        lessonRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditLessonRequest request) throws ServiceException {
        lessonRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid lesson id"));
        groupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        teacherRepository.findById(request.getTeacherId()).orElseThrow(() -> new NotFoundService("invalid teacher id"));
        subjectRepository.findById(request.getSubjectId()).orElseThrow(() -> new NotFoundService("invalid subject id"));
        if (request.getAttendanceList() != null) {
            List<String> attendanceList = request.getAttendanceList();
            for (var id : attendanceList) {
                Student student = studentRepository.findById(id).orElseThrow(() -> new ServiceException("invalid student id"));
                String groupId = student.getGroup().getId();
                if (!groupId.equals(request.getGroupId())) {
                    throw new ServiceException("all students from attendance list should be from group with id " + request.getGroupId());
                }
            }
        }
        lessonRepository.update(
                request.getSubjectId(),
                request.getDate(),
                request.getNumber(),
                request.getTeacherId(),
                request.getGroupId(),
                request.getId()
        );
        Lesson lesson = lessonRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid lesson id"));
        if (lessonAttendanceRepository.findAttendanceByLessonId(request.getId()) != null) {
            lessonAttendanceRepository.deleteAttendanceByLessonId(request.getId());
        }
        if (request.getAttendanceList() != null) {
            LessonAttendance lessonAttendance = new LessonAttendance(
                    null,
                    lesson,
                    request.getAttendanceList().isEmpty()
                            ? new ArrayList<>()
                            : request.getAttendanceList().stream()
                            .map(o -> new Student(o, null, null, null, null, null))
                            .toList()
            );
            lessonAttendanceRepository.save(lessonAttendance);
        }
    }

    @Override
    public GetLessonByIdResponse getById(GetLessonByIdRequest request) throws NotFoundService {
        return new GetLessonByIdResponse(
                lessonRepository
                        .findById(request.getId())
                        .orElseThrow(() -> new NotFoundService("invalid lesson id")),
                lessonAttendanceRepository.findAttendanceByLessonId(request.getId()));
    }

    @Override
    public List<GetLessonByIdResponse> getByGroup(GetLessonsByGroupRequest request) throws ServiceException {
        groupRepository.findById(request.getGroupId()).orElseThrow(() -> new ServiceException("invalid group id"));
        return lessonRepository.findLessonByGroupId(request.getGroupId(), request.getStartDate(), request.getEndDate()).stream()
                .map(o -> new GetLessonByIdResponse(o, lessonAttendanceRepository.findAttendanceByLessonId(o.getId())))
                .toList();
    }

    @Override
    public List<GetLessonByIdResponse> getByTeacher(GetLessonsByTeacherRequest request) throws ServiceException {
        teacherRepository.findById(request.getTeacherId()).orElseThrow(() -> new ServiceException("invalid teacher id"));
        return lessonRepository.findLessonByTeacherId(request.getTeacherId(), request.getStartDate(), request.getEndDate()).stream()
                .map(o -> new GetLessonByIdResponse(o, lessonAttendanceRepository.findAttendanceByLessonId(o.getId())))
                .toList();
    }
}
