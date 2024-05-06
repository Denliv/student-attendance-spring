package student_attendance.service.service_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student_attendance.entity.Student;
import student_attendance.entity.StudentGroup;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.repository.IStudentGroupRepository;
import student_attendance.repository.IStudentRepository;
import student_attendance.request.student_request.*;
import student_attendance.response.student_response.AddStudentResponse;
import student_attendance.response.student_response.GetStudentByIdResponse;
import student_attendance.service.service_interface.IStudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private IStudentGroupRepository studentGroupRepository;

    @Override
    public AddStudentResponse add(AddStudentRequest request) throws NotFoundService {
        studentGroupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        Student student = new Student(null,
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getStatus(),
                new StudentGroup(request.getGroupId(), null));
        var save = studentRepository.save(student);
        return new AddStudentResponse(save.getId());
    }

    @Override
    public void delete(DeleteStudentRequest request) throws NotFoundService {
        studentRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid user id"));
        studentRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditStudentRequest request) throws ServiceException {
        studentRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid user id"));
        studentGroupRepository.findById(request.getGroupId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        studentRepository.update(request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getStatus(),
                request.getGroupId(),
                request.getId());
    }

    @Override
    public GetStudentByIdResponse getById(GetStudentByIdRequest request) throws NotFoundService {
        return new GetStudentByIdResponse(studentRepository
                .findById(request.getId())
                .orElseThrow(() -> new NotFoundService("invalid user id")));
    }

    @Override
    public List<GetStudentByIdResponse> getByGroupId(GetStudentsByGroupIdRequest request) throws NotFoundService {
        studentGroupRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        return studentRepository.findAllByGroupId(request.getId())
                .stream().map(GetStudentByIdResponse::new)
                .collect(Collectors.toList());
    }
}
