package student_attendance.service_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import student_attendance.entity.Teacher;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.ITeacherRepository;
import student_attendance.request.teacher_request.AddTeacherRequest;
import student_attendance.request.teacher_request.DeleteTeacherRequest;
import student_attendance.request.teacher_request.EditTeacherRequest;
import student_attendance.request.teacher_request.GetTeacherByIdRequest;
import student_attendance.response.teacher_response.AddTeacherResponse;
import student_attendance.response.teacher_response.GetTeacherByIdResponse;
import student_attendance.service.service_realization.TeacherService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTests {
    @Mock
    private ITeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService teacherService;

    @Test
    void addTest() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String lastName = "lastName";
        String firstName = "firstName";
        String middleName = "middleName";
        Teacher teacher = new Teacher(id, lastName, firstName, middleName);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        AddTeacherRequest request = new AddTeacherRequest(lastName, firstName, middleName);

        //Act
        AddTeacherResponse response = teacherService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddTeacherResponse(id), response);
    }

    @Test
    void deleteTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        Teacher teacher = Mockito.mock(Teacher.class);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        willDoNothing().given(teacherRepository).deleteById(id);
        DeleteTeacherRequest request = new DeleteTeacherRequest(id);

        //Act
        teacherService.delete(request);

        //Assert
        verify(teacherRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid teacher id");
        }).when(teacherRepository).findById(id);
        DeleteTeacherRequest request = new DeleteTeacherRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> teacherService.delete(request));
    }

    @Test
    void editTest() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        String lastName = "lastName";
        String firstName = "firstName";
        String middleName = "middleName";
        Teacher teacher = new Teacher(id, lastName, firstName, middleName);
        String newLastName = "newLastName";
        String newFirstName = "newFirstName";
        String newMiddleName = "newMiddleName";
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        doAnswer(invocation -> {
            teacher.setLastName(newLastName);
            teacher.setFirstName(newFirstName);
            teacher.setMiddleName(newMiddleName);
            return 0;
        }).when(teacherRepository).update(newLastName, newFirstName, newMiddleName, id);
        EditTeacherRequest request = new EditTeacherRequest(id, newLastName, newFirstName, newMiddleName);

        //Act
        teacherService.edit(request);

        //Assert
        verify(teacherRepository, times(1)).update(newLastName, newFirstName, newMiddleName, id);
        assertEquals(new Teacher(id, newLastName, newFirstName, newMiddleName), teacher);
    }

    @Test
    void editTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String newLastName = "newLastName";
        String newFirstName = "newFirstName";
        String newMiddleName = "newMiddleName";
        doAnswer(invocation -> {
            throw new NotFoundService("invalid teacher id");
        }).when(teacherRepository).findById(id);
        EditTeacherRequest request = new EditTeacherRequest(id, newLastName, newFirstName, newMiddleName);

        //Assert
        assertThrows(NotFoundService.class, () -> teacherService.edit(request));
    }

    @Test
    void getByIdTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        String lastName = "lastName";
        String firstName = "firstName";
        String middleName = "middleName";
        Teacher teacher = new Teacher(id, lastName, firstName, middleName);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        GetTeacherByIdRequest request = new GetTeacherByIdRequest(id);

        //Act
        GetTeacherByIdResponse response = teacherService.getById(request);

        //Assert
        assertNotNull(response);
        assertEquals(new GetTeacherByIdResponse(teacher), response);
    }

    @Test
    void getByIdTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid teacher id");
        }).when(teacherRepository).findById(id);
        GetTeacherByIdRequest request = new GetTeacherByIdRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> teacherService.getById(request));
    }

    @Test
    void getAllTest() {
        //Arrange
        String id_1 = UUID.randomUUID().toString();
        String lastName_1 = "lastName";
        String firstName_1 = "firstName";
        String middleName_1 = "middleName";
        Teacher teacher_1 = new Teacher(id_1, lastName_1, firstName_1, middleName_1);
        String id_2 = UUID.randomUUID().toString();
        String lastName_2 = "lastName";
        String firstName_2 = "firstName";
        String middleName_2 = "middleName";
        Teacher teacher_2 = new Teacher(id_2, lastName_2, firstName_2, middleName_2);
        when(teacherRepository.findAll()).thenReturn(List.of(teacher_1, teacher_2));

        //Act
        List<GetTeacherByIdResponse> response = teacherService.getAll();

        //Assert
        assertNotNull(response);
        assertEquals(List.of(new GetTeacherByIdResponse(teacher_1), new GetTeacherByIdResponse(teacher_2)), response);
    }

    @Test
    void getAllTest_EmptyList() {
        //Arrange
        when(teacherRepository.findAll()).thenReturn(List.of());

        //Act
        List<GetTeacherByIdResponse> response = teacherService.getAll();

        //Assert
        assertNotNull(response);
        assertEquals(List.of(), response);
    }
}
