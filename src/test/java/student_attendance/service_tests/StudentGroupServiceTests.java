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
import student_attendance.request.student_group_request.AddStudentGroupRequest;
import student_attendance.request.student_group_request.DeleteStudentGroupRequest;
import student_attendance.request.student_group_request.EditStudentGroupRequest;
import student_attendance.request.student_group_request.GetStudentGroupByIdRequest;
import student_attendance.response.student_group_response.AddStudentGroupResponse;
import student_attendance.response.student_group_response.GetStudentGroupByIdResponse;
import student_attendance.service.service_realization.StudentGroupService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentGroupServiceTests {
    @Mock
    private IStudentGroupRepository groupRepository;
    @Mock
    private IStudentRepository studentRepository;
    @InjectMocks
    private StudentGroupService groupService;

    @Test
    void addTest() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String name = "name";
        StudentGroup studentGroup = new StudentGroup(id, name);
        when(groupRepository.save(any(StudentGroup.class))).thenReturn(studentGroup);
        AddStudentGroupRequest request = new AddStudentGroupRequest(name);

        //Act
        AddStudentGroupResponse response = groupService.add(request);

        //Assert
        assertNotNull(response);
        assertEquals(new AddStudentGroupResponse(id, name), response);
    }

    @Test
    void deleteTest() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        StudentGroup studentGroup = Mockito.mock(StudentGroup.class);
        when(groupRepository.findById(id)).thenReturn(Optional.of(studentGroup));
        when(studentRepository.findAllByGroupId(id)).thenReturn(List.of());
        willDoNothing().given(groupRepository).deleteById(id);
        DeleteStudentGroupRequest request = new DeleteStudentGroupRequest(id);

        //Act
        groupService.delete(request);

        //Assert
        verify(groupRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteTest_ThrowException_InvalidGroupId() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid studentGroup id");
        }).when(groupRepository).findById(id);
        DeleteStudentGroupRequest request = new DeleteStudentGroupRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> groupService.delete(request));
    }

    @Test
    void deleteTest_ThrowException_GroupContainsStudents() {
        //Arrange
        String id = UUID.randomUUID().toString();
        StudentGroup studentGroup = Mockito.mock(StudentGroup.class);
        Student student = Mockito.mock(Student.class);
        when(groupRepository.findById(id)).thenReturn(Optional.of(studentGroup));
        when(studentRepository.findAllByGroupId(id)).thenReturn(List.of(student));
        DeleteStudentGroupRequest request = new DeleteStudentGroupRequest(id);

        //Assert
        assertThrows(ServiceException.class, () -> groupService.delete(request));
    }

    @Test
    void editTest() throws ServiceException {
        //Arrange
        String id = UUID.randomUUID().toString();
        String name = "name";
        StudentGroup studentGroup = new StudentGroup(id, name);
        String newName = "newName";
        when(groupRepository.findById(id)).thenReturn(Optional.of(studentGroup));
        doAnswer(invocation -> {
            studentGroup.setName(newName);
            return 0;
        }).when(groupRepository).update(newName, id);
        EditStudentGroupRequest request = new EditStudentGroupRequest(id, newName);

        //Act
        groupService.edit(request);

        //Assert
        verify(groupRepository, times(1)).update(newName, id);
        assertEquals(new StudentGroup(id, newName), studentGroup);
    }

    @Test
    void editTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        String newName = "newName";
        doAnswer(invocation -> {
            throw new NotFoundService("invalid studentGroup id");
        }).when(groupRepository).findById(id);
        EditStudentGroupRequest request = new EditStudentGroupRequest(id, newName);

        //Assert
        assertThrows(NotFoundService.class, () -> groupService.edit(request));
    }

    @Test
    void getByIdTest() throws NotFoundService {
        //Arrange
        String id = UUID.randomUUID().toString();
        String name = "name";
        StudentGroup studentGroup = new StudentGroup(id, name);
        when(groupRepository.findById(id)).thenReturn(Optional.of(studentGroup));
        GetStudentGroupByIdRequest request = new GetStudentGroupByIdRequest(id);

        //Act
        GetStudentGroupByIdResponse response = groupService.getById(request);

        //Assert
        assertNotNull(response);
        assertEquals(new GetStudentGroupByIdResponse(id, name), response);
    }

    @Test
    void getByIdTest_ThrowException() {
        //Arrange
        String id = UUID.randomUUID().toString();
        doAnswer(invocation -> {
            throw new NotFoundService("invalid studentGroup id");
        }).when(groupRepository).findById(id);
        GetStudentGroupByIdRequest request = new GetStudentGroupByIdRequest(id);

        //Assert
        assertThrows(NotFoundService.class, () -> groupService.getById(request));
    }
}
