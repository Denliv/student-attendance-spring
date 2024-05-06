package student_attendance.service.service_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import student_attendance.service.service_interface.ISubjectService;

import java.util.List;

@Service
public class SubjectService implements ISubjectService {
    @Autowired
    private ISubjectRepository subjectRepository;

    @Override
    public AddSubjectResponse add(AddSubjectRequest request) throws NotFoundService {
        Subject subject = new Subject(null,
                request.getName());
        var save = subjectRepository.save(subject);
        return new AddSubjectResponse(save.getId());
    }

    @Override
    public void delete(DeleteSubjectRequest request) throws NotFoundService {
        subjectRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid subject id"));
        subjectRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditSubjectRequest request) throws ServiceException {
        subjectRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid subject id"));
        subjectRepository.update(request.getName(), request.getId());
    }

    @Override
    public GetSubjectByIdResponse getById(GetSubjectByIdRequest request) throws NotFoundService {
        return new GetSubjectByIdResponse(subjectRepository
                .findById(request.getId())
                .orElseThrow(() -> new NotFoundService("invalid subject id")));
    }

    @Override
    public List<GetSubjectByIdResponse> getAll() {
        return subjectRepository.findAll().stream()
                .map(o -> new GetSubjectByIdResponse(o.getId(), o.getName()))
                .toList();
    }
}
