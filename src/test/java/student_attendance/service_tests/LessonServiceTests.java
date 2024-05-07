package student_attendance.service_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import student_attendance.entity.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.*;
import student_attendance.request.lesson_request.*;
import student_attendance.response.lesson_response.AddLessonResponse;
import student_attendance.response.lesson_response.GetLessonByIdResponse;
import student_attendance.service.service_realization.LessonService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTests {
    @Mock
    private ILessonRepository lessonRepository;
    @Mock
    private ILessonAttendanceRepository lessonAttendanceRepository;
    @Mock
    private IStudentGroupRepository groupRepository;
    @Mock
    private IStudentRepository studentRepository;
    @Mock
    private ITeacherRepository teacherRepository;
    @Mock
    private ISubjectRepository subjectRepository;
    @InjectMocks
    private LessonService lessonService;

    @Test
    void addTest_NotEmptyAttendanceList() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        Lesson lesson = Mockito.mock(Lesson.class);
        Student student_1 = Mockito.mock(Student.class);
        Student student_2 = Mockito.mock(Student.class);
        LessonAttendance attendance = new LessonAttendance(
                null,
                lesson,
                List.of(
                        new Student(studentId_1, null, null, null, null, null),
                        new Student(studentId_2, null, null, null, null, null)
                ));
        when(student_1.getGroup()).thenReturn(new StudentGroup(groupId, ""));
        when(student_2.getGroup()).thenReturn(new StudentGroup(groupId, ""));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(studentRepository.findById(studentId_1)).thenReturn(Optional.of(student_1));
        when(studentRepository.findById(studentId_2)).thenReturn(Optional.of(student_2));
        when(lesson.getId()).thenReturn(id);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(lessonAttendanceRepository.save(any(LessonAttendance.class))).thenReturn(attendance);
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Act
        AddLessonResponse response = lessonService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddLessonResponse(id), response);
        verify(lessonAttendanceRepository, times(1)).save(attendance);
    }

    @Test
    void addTest_EmptyAttendanceList() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        Lesson lesson = new Lesson(id, new Subject(), "", 1, new Teacher(), new StudentGroup());
        LessonAttendance attendance = new LessonAttendance(null, lesson, List.of());
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        when(lessonAttendanceRepository.save(any(LessonAttendance.class))).thenReturn(attendance);
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", "", List.of());

        //Act
        AddLessonResponse response = lessonService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddLessonResponse(id), response);
        verify(lessonAttendanceRepository, times(1)).save(attendance);
    }

    @Test
    void addTest_NullAttendanceList() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        Lesson lesson = Mockito.mock(Lesson.class);
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(lesson.getId()).thenReturn(id);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", "", null);

        //Act
        AddLessonResponse response = lessonService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddLessonResponse(id), response);
    }

    @Test
    void addTest_ThrowException_InvalidGroupId() {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid group id");
        }).when(groupRepository).findById(groupId);
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", groupId, null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.add(request));
    }

    @Test
    void addTest_ThrowException_InvalidTeacherId() {
        //Arrange
        String teacherId = UUID.randomUUID().toString();
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid teacher id");
        }).when(teacherRepository).findById(teacherId);
        AddLessonRequest request = new AddLessonRequest("", "", 1, teacherId, "", null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.add(request));
    }

    @Test
    void addTest_ThrowException_InvalidSubjectId() {
        //Arrange
        String subjectId = UUID.randomUUID().toString();
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid subject id");
        }).when(subjectRepository).findById(subjectId);
        AddLessonRequest request = new AddLessonRequest(subjectId, "", 1, "", "", null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.add(request));
    }

    @Test
    void addTest_ThrowException_InvalidStudentId() {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid student id");
        }).when(studentRepository).findById(studentId_1);
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.add(request));
    }

    @Test
    void addTest_ThrowException_StudentsNotFromTheLessonGroup() {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        Student student_1 = Mockito.mock(Student.class);
        when(student_1.getGroup()).thenReturn(new StudentGroup(UUID.randomUUID().toString(), ""));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(studentRepository.findById(studentId_1)).thenReturn(Optional.of(student_1));
        AddLessonRequest request = new AddLessonRequest("", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.add(request));
    }

    @Test
    void deleteTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        when(lessonRepository.findById(id)).thenReturn(Optional.of(new Lesson()));
        willDoNothing().given(lessonAttendanceRepository).deleteAttendanceByLessonId(id);
        willDoNothing().given(lessonRepository).deleteById(id);
        DeleteLessonRequest request = new DeleteLessonRequest(id);

        //Act
        lessonService.delete(request);

        //Assert
        verify(lessonRepository, times(1)).deleteById(id);
        verify(lessonAttendanceRepository, times(1)).deleteAttendanceByLessonId(id);
    }

    @Test
    void deleteTest_ThrowException() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid lesson id");
        }).when(lessonRepository).findById(id);
        DeleteLessonRequest request = new DeleteLessonRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.delete(request));
    }

    @Test
    void editTest_NotEmptyAttendanceList() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        Lesson lesson = new Lesson(id, new Subject(), "", 1, new Teacher(), new StudentGroup());
        Student student_1 = Mockito.mock(Student.class);
        Student student_2 = Mockito.mock(Student.class);
        LessonAttendance attendance = new LessonAttendance(
                null,
                lesson,
                List.of(
                        new Student(studentId_1, null, null, null, null, null),
                        new Student(studentId_2, null, null, null, null, null)
                ));
        when(student_1.getGroup()).thenReturn(new StudentGroup(groupId, ""));
        when(student_2.getGroup()).thenReturn(new StudentGroup(groupId, ""));
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(lesson));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(studentRepository.findById(studentId_1)).thenReturn(Optional.of(student_1));
        when(studentRepository.findById(studentId_2)).thenReturn(Optional.of(student_2));
        doAnswer(invocation -> {
            lesson.setSubject(new Subject());
            lesson.setDate("");
            lesson.setNumber(1);
            lesson.setTeacher(new Teacher());
            lesson.setGroup(new StudentGroup(groupId, ""));
            return 0;
        }).when(lessonRepository).update("", "", 1, "", groupId, id);
        when(lessonAttendanceRepository.findAttendanceByLessonId(id)).thenReturn(new LessonAttendance());
        willDoNothing().given(lessonAttendanceRepository).deleteAttendanceByLessonId(id);
        when(lessonAttendanceRepository.save(any())).thenReturn(attendance);
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Act
        lessonService.edit(request);

        //Assert
        assertEquals(new Lesson(id, new Subject(), "", 1, new Teacher(), new StudentGroup(groupId, "")), lesson);
        verify(lessonAttendanceRepository, times(1)).save(attendance);
    }

    @Test
    void editTest_ThrowException_InvalidLessonId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid lesson id");
        }).when(lessonRepository).findById(id);
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, "", "", null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.edit(request));
    }

    @Test
    void editTest_ThrowException_InvalidGroupId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(new Lesson()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid group id");
        }).when(groupRepository).findById(groupId);
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, "", groupId, null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.edit(request));
    }

    @Test
    void editTest_ThrowException_InvalidTeacherId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String teacherId = UUID.randomUUID().toString();
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(new Lesson()));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid teacher id");
        }).when(teacherRepository).findById(teacherId);
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, teacherId, "", null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.edit(request));
    }

    @Test
    void editTest_ThrowException_InvalidSubjectId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String subjectId = UUID.randomUUID().toString();
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(new Lesson()));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid subject id");
        }).when(subjectRepository).findById(subjectId);
        EditLessonRequest request = new EditLessonRequest(id, subjectId, "", 1, "", "", null);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.edit(request));
    }

    @Test
    void editTest_ThrowException_InvalidStudentId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(new Lesson()));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        doAnswer(invocation -> {
            throw new NotFoundService("invalid student id");
        }).when(studentRepository).findById(studentId_1);
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.edit(request));
    }

    @Test
    void editTest_ThrowException_StudentsNotFromTheLessonGroup() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String groupId = UUID.randomUUID().toString();
        String studentId_1 = UUID.randomUUID().toString();
        String studentId_2 = UUID.randomUUID().toString();
        Student student_1 = Mockito.mock(Student.class);
        when(student_1.getGroup()).thenReturn(new StudentGroup(UUID.randomUUID().toString(), ""));
        when(lessonRepository.findById(anyString())).thenReturn(Optional.of(new Lesson()));
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        when(teacherRepository.findById(anyString())).thenReturn(Optional.of(new Teacher()));
        when(subjectRepository.findById(anyString())).thenReturn(Optional.of(new Subject()));
        when(studentRepository.findById(studentId_1)).thenReturn(Optional.of(student_1));
        EditLessonRequest request = new EditLessonRequest(id, "", "", 1, "", groupId, List.of(studentId_1, studentId_2));

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.edit(request));
    }

    @Test
    void getByIdTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        Lesson lesson = new Lesson(id, new Subject(), "", 1, new Teacher(), new StudentGroup());
        when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
        when(lessonAttendanceRepository.findAttendanceByLessonId(id)).thenReturn(new LessonAttendance());
        GetLessonByIdRequest request = new GetLessonByIdRequest(id);

        //Act
        GetLessonByIdResponse response = lessonService.getById(request);

        //Assert
        assertNotNull(response);
        assertEquals(new GetLessonByIdResponse(lesson, new LessonAttendance()), response);
    }

    @Test
    void getByIdTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid lesson id");
        }).when(lessonRepository).findById(id);
        GetLessonByIdRequest request = new GetLessonByIdRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> lessonService.getById(request));
    }

    @Test
    void getByGroupTest_EmptyList() throws ServiceException {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(new StudentGroup()));
        when(lessonRepository.findLessonByGroupId(groupId, "", "")).thenReturn(List.of());
        GetLessonsByGroupRequest request = new GetLessonsByGroupRequest("", "", groupId);

        //Act
        List<GetLessonByIdResponse> response = lessonService.getByGroup(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(), response);
    }

    @Test
    void getByGroupTest_NotEmptyList() throws ServiceException {
        //Arrange
        String lessonId_1 = UUID.randomUUID().toString();
        String lessonId_2 = UUID.randomUUID().toString();
        Lesson lesson_1 = new Lesson(lessonId_1, new Subject(), "", 1, new Teacher(), new StudentGroup());
        Lesson lesson_2 = new Lesson(lessonId_2, new Subject(), "", 1, new Teacher(), new StudentGroup());
        String groupId = UUID.randomUUID().toString();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(new StudentGroup()));
        when(lessonRepository.findLessonByGroupId(groupId, "", "")).thenReturn(List.of(lesson_1, lesson_2));
        when(lessonAttendanceRepository.findAttendanceByLessonId(lessonId_1)).thenReturn(new LessonAttendance());
        when(lessonAttendanceRepository.findAttendanceByLessonId(lessonId_2)).thenReturn(new LessonAttendance());
        GetLessonsByGroupRequest request = new GetLessonsByGroupRequest("", "", groupId);

        //Act
        List<GetLessonByIdResponse> response = lessonService.getByGroup(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(
                        new GetLessonByIdResponse(lesson_1, new LessonAttendance()),
                        new GetLessonByIdResponse(lesson_2, new LessonAttendance()))
                , response);
    }

    @Test
    void getByGroupTest_ThrowException() {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new ServiceException("invalid group id");
        }).when(groupRepository).findById(groupId);
        GetLessonsByGroupRequest request = new GetLessonsByGroupRequest("", "", groupId);

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.getByGroup(request));
    }

    @Test
    void getByTeacherTest_EmptyList() throws ServiceException {
        //Arrange
        String teacherId = UUID.randomUUID().toString();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(new Teacher()));
        when(lessonRepository.findLessonByTeacherId(teacherId, "", "")).thenReturn(List.of());
        GetLessonsByTeacherRequest request = new GetLessonsByTeacherRequest("", "", teacherId);

        //Act
        List<GetLessonByIdResponse> response = lessonService.getByTeacher(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(), response);
    }

    @Test
    void getByTeacherTest_NotEmptyList() throws ServiceException {
        //Arrange
        String lessonId_1 = UUID.randomUUID().toString();
        String lessonId_2 = UUID.randomUUID().toString();
        Lesson lesson_1 = new Lesson(lessonId_1, new Subject(), "", 1, new Teacher(), new StudentGroup());
        Lesson lesson_2 = new Lesson(lessonId_2, new Subject(), "", 1, new Teacher(), new StudentGroup());
        String teacherId = UUID.randomUUID().toString();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(new Teacher()));
        when(lessonRepository.findLessonByTeacherId(teacherId, "", "")).thenReturn(List.of(lesson_1, lesson_2));
        when(lessonAttendanceRepository.findAttendanceByLessonId(lessonId_1)).thenReturn(new LessonAttendance());
        when(lessonAttendanceRepository.findAttendanceByLessonId(lessonId_2)).thenReturn(new LessonAttendance());
        GetLessonsByTeacherRequest request = new GetLessonsByTeacherRequest("", "", teacherId);

        //Act
        List<GetLessonByIdResponse> response = lessonService.getByTeacher(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(
                        new GetLessonByIdResponse(lesson_1, new LessonAttendance()),
                        new GetLessonByIdResponse(lesson_2, new LessonAttendance()))
                , response);
    }

    @Test
    void getByTeacherTest_ThrowException() {
        //Arrange
        String teacherId = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new ServiceException("invalid teacher id");
        }).when(teacherRepository).findById(teacherId);
        GetLessonsByTeacherRequest request = new GetLessonsByTeacherRequest("", "", teacherId);

        //Assert
        assertThrows(ServiceException.class, () -> lessonService.getByTeacher(request));
    }
}
