package student_attendance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import student_attendance.entity.Subject;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.ISubjectRepository;
import student_attendance.request.subject_request.AddSubjectRequest;
import student_attendance.request.subject_request.DeleteSubjectRequest;
import student_attendance.request.subject_request.EditSubjectRequest;
import student_attendance.request.subject_request.GetSubjectByIdRequest;
import student_attendance.response.subject_response.AddSubjectResponse;
import student_attendance.response.subject_response.GetSubjectByIdResponse;
import student_attendance.service.service_realization.SubjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTests {
	@Mock
	private ISubjectRepository subjectRepository;
	@InjectMocks
	private SubjectService subjectService;

	@Test
	void addTest() throws NotFoundService {
		String id = UUID.randomUUID().toString();
		String name = "Random_Name";
		Subject subject = new Subject(id, name);
		when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

		AddSubjectRequest request = new AddSubjectRequest(name);
		AddSubjectResponse response = subjectService.add(request);

		assertNotNull(response);
		assertEquals(new AddSubjectResponse(id), response);
	}

	@Test
	void deleteTest() throws NotFoundService {
		String id = UUID.randomUUID().toString();
		String name = "Random_Name";
		Subject subject = new Subject(id, name);
		when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));
		willDoNothing().given(subjectRepository).deleteById(id);

		DeleteSubjectRequest request = new DeleteSubjectRequest(id);
		subjectService.delete(request);

		Mockito.verify(subjectRepository, times(1)).deleteById(id);
	}

	@Test
	void deleteTest_ThrowException() {
		String id = UUID.randomUUID().toString();
		doAnswer(invocation -> {throw new NotFoundService("invalid subject id");}).when(subjectRepository).findById(id);

		DeleteSubjectRequest request = new DeleteSubjectRequest(id);
		assertThrows(NotFoundService.class, () -> subjectService.delete(request));
	}

	@Test
	void editTest() throws ServiceException {
		String id = UUID.randomUUID().toString();
		String name = "Random_Name";
		String newName = "New_Name";
		Subject subject = new Subject(id, name);
		when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));
		doAnswer(invocation -> {subject.setName(newName); return 0;}).when(subjectRepository).update(newName, id);

		EditSubjectRequest request = new EditSubjectRequest(id, newName);
		subjectService.edit(request);

		Mockito.verify(subjectRepository, times(1)).update(newName, id);
		assertEquals(new Subject(id, newName), subject);
	}

	@Test
	void editTest_ThrowException() {
		String id = UUID.randomUUID().toString();
		String newName = "New_Name";
		doAnswer(invocation -> {throw new NotFoundService("invalid subject id");}).when(subjectRepository).findById(id);

		EditSubjectRequest request = new EditSubjectRequest(id, newName);
		assertThrows(NotFoundService.class, () -> subjectService.edit(request));
	}

	@Test
	void getByIdTest() throws NotFoundService {
		String id = UUID.randomUUID().toString();
		String name = "Random_Name";
		Subject subject = new Subject(id, name);
		when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

		GetSubjectByIdRequest request = new GetSubjectByIdRequest(id);
		GetSubjectByIdResponse response = subjectService.getById(request);

		assertNotNull(response);
		assertEquals(new GetSubjectByIdResponse(id, name), response);
	}

	@Test
	void getByIdTest_ThrowException() throws NotFoundService {
		String id = UUID.randomUUID().toString();
		doAnswer(invocation -> {throw new NotFoundService("invalid subject id");}).when(subjectRepository).findById(id);

		GetSubjectByIdRequest request = new GetSubjectByIdRequest(id);
		assertThrows(NotFoundService.class, () -> subjectService.getById(request));
	}

	@Test
	void getAllTest() throws NotFoundService {
		String id_1 = UUID.randomUUID().toString();
		String name_1 = "Random_Name";
		Subject subject_1 = new Subject(id_1, name_1);
		String id_2 = UUID.randomUUID().toString();
		String name_2 = "Random_Name";
		Subject subject_2 = new Subject(id_2, name_2);
		when(subjectRepository.findAll()).thenReturn(List.of(subject_1, subject_2));

		List<GetSubjectByIdResponse> response = subjectService.getAll();

		assertNotNull(response);
		assertEquals(List.of(new GetSubjectByIdResponse(subject_1), new GetSubjectByIdResponse(subject_2)), response);
	}

	@Test
	void getAllTest_EmptyList() throws NotFoundService {
		when(subjectRepository.findAll()).thenReturn(List.of());

		List<GetSubjectByIdResponse> response = subjectService.getAll();

		assertNotNull(response);
		assertEquals(List.of(), response);
	}
}
