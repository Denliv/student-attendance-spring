package student_attendance.service_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import student_attendance.entity.Student;
import student_attendance.entity.StudentGroup;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.IStudentGroupRepository;
import student_attendance.repository.IStudentRepository;
import student_attendance.request.student_request.*;
import student_attendance.response.student_response.AddStudentResponse;
import student_attendance.response.student_response.GetStudentByIdResponse;
import student_attendance.service.service_realization.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    private IStudentRepository studentRepository;
    @Mock
    private IStudentGroupRepository groupRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void addTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        Student student = Mockito.mock(Student.class);
        when(student.getId()).thenReturn(id);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(groupRepository.findById(anyString())).thenReturn(Optional.of(new StudentGroup()));
        AddStudentRequest request = new AddStudentRequest("", "", "", "", "");

        //Act
        AddStudentResponse response = studentService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddStudentResponse(id), response);
    }

    @Test
    void addTest_ThrowException() throws NotFoundService {
        //Arrange
        doAnswer(invocation -> {
            throw new NotFoundService("invalid studentGroup id");
        }).when(groupRepository).findById(anyString());
        AddStudentRequest request = new AddStudentRequest("", "", "", "", "");

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.add(request));
    }

    @Test
    void deleteTest() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        when(studentRepository.findById(id)).thenReturn(Optional.of(new Student()));
        DeleteStudentRequest request = new DeleteStudentRequest(id);

        //Act
        studentService.delete(request);

        //Assert
        verify(studentRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid student id");
        }).when(studentRepository).findById(id);
        DeleteStudentRequest request = new DeleteStudentRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.delete(request));
    }

    @Test
    void editTest() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        String lastName = "lastName";
        String firstName = "firstName";
        String middleName = "middleName";
        String status = "status";
        StudentGroup group = Mockito.mock(StudentGroup.class);
        Student student = new Student(id, lastName, firstName, middleName, status, group);
        String newLastName = "newLastName";
        String newFirstName = "newFirstName";
        String newMiddleName = "newMiddleName";
        String newStatus = "newStatus";
        String newGroupId = UUID.randomUUID().toString();
        StudentGroup newGroup = Mockito.mock(StudentGroup.class);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(groupRepository.findById(newGroupId)).thenReturn(Optional.of(new StudentGroup()));
        doAnswer(invocation -> {
            student.setLastName(newLastName);
            student.setFirstName(newFirstName);
            student.setMiddleName(newMiddleName);
            student.setStatus(newStatus);
            student.setGroup(newGroup);
            return 0;
        }).when(studentRepository).update(newLastName, newFirstName, newMiddleName, newStatus, newGroupId, id);
        EditStudentRequest request = new EditStudentRequest(id, newLastName, newFirstName, newMiddleName, newStatus, newGroupId);

        //Act
        studentService.edit(request);

        //Assert
        verify(studentRepository, times(1)).update(newLastName, newFirstName, newMiddleName, newStatus, newGroupId, id);
        assertEquals(new Student(id, newLastName, newFirstName, newMiddleName, newStatus, newGroup), student);
    }

    @Test
    void editTest_ThrowException_InvalidStudentId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid student id");
        }).when(studentRepository).findById(id);
        EditStudentRequest request = new EditStudentRequest(id, "", "", "", "", "");

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.edit(request));
    }

    @Test
    void editTest_ThrowException_InvalidGroupId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid group id");
        }).when(studentRepository).findById(id);
        EditStudentRequest request = new EditStudentRequest(id, "", "", "", "", "");

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.edit(request));
    }

    @Test
    void getByIdTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        Student student = new Student(id, "", "", "", "", new StudentGroup());
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        GetStudentByIdRequest request = new GetStudentByIdRequest(id);

        //Act
        GetStudentByIdResponse response = studentService.getById(request);

        //Assert
        assertNotNull(response);
        assertEquals(new GetStudentByIdResponse(student), response);
    }

    @Test
    void getByIdTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid student id");
        }).when(studentRepository).findById(id);
        GetStudentByIdRequest request = new GetStudentByIdRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.getById(request));
    }

    @Test
    void getByGroupIdTest() throws NotFoundService {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        Student student_1 = new Student(
                UUID.randomUUID().toString(),
                "", "", "", "",
                new StudentGroup(groupId, ""));
        Student student_2 = new Student(
                UUID.randomUUID().toString(),
                "", "", "", "",
                new StudentGroup(UUID.randomUUID().toString(), ""));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(new StudentGroup()));
        when(studentRepository.findAllByGroupId(groupId)).thenReturn(List.of(student_1));
        GetStudentsByGroupIdRequest request = new GetStudentsByGroupIdRequest(groupId);

        //Act
        List<GetStudentByIdResponse> response = studentService.getByGroupId(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(new GetStudentByIdResponse(student_1)), response);
    }

    @Test
    void getByGroupIdTest_EmptyList() throws NotFoundService {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        Student student_1 = new Student(
                UUID.randomUUID().toString(),
                "", "", "", "",
                new StudentGroup(UUID.randomUUID().toString(), ""));
        Student student_2 = new Student(
                UUID.randomUUID().toString(),
                "", "", "", "",
                new StudentGroup(UUID.randomUUID().toString(), ""));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(new StudentGroup()));
        when(studentRepository.findAllByGroupId(groupId)).thenReturn(List.of());
        GetStudentsByGroupIdRequest request = new GetStudentsByGroupIdRequest(groupId);

        //Act
        List<GetStudentByIdResponse> response = studentService.getByGroupId(request);

        //Assert
        assertNotNull(response);
        assertEquals(List.of(), response);
    }

    @Test
    void getByGroupIdTest_ThrowException() throws NotFoundService {
        //Arrange
        String groupId = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid group id");
        }).when(groupRepository).findById(groupId);
        GetStudentsByGroupIdRequest request = new GetStudentsByGroupIdRequest(groupId);

        //Assert
        assertThrows(NotFoundService.class, () -> studentService.getByGroupId(request));
    }
}
