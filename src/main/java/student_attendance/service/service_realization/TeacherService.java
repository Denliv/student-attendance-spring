package student_attendance.service.service_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import student_attendance.service.service_interface.ITeacherService;

import java.util.List;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private ITeacherRepository teacherRepository;

    @Override
    public AddTeacherResponse add(AddTeacherRequest request) {
        Teacher teacher = new Teacher(null,
                request.getLastName(),
                request.getFirstName(),
                request.getMiddleName());
        var save = teacherRepository.save(teacher);
        return new AddTeacherResponse(save.getId());
    }

    @Override
    public void delete(DeleteTeacherRequest request) throws NotFoundService {
        teacherRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid teacher id"));
        teacherRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditTeacherRequest request) throws ServiceException {
        teacherRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid teacher id"));
        teacherRepository.update(request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getId());
    }

    @Override
    public GetTeacherByIdResponse getById(GetTeacherByIdRequest request) throws NotFoundService {
        return new GetTeacherByIdResponse(teacherRepository
                .findById(request.getId())
                .orElseThrow(() -> new NotFoundService("invalid teacher id")));
    }

    @Override
    public List<GetTeacherByIdResponse> getAll() throws NotFoundService {
        return teacherRepository.findAll().stream()
                .map(o -> new GetTeacherByIdResponse(o.getId(), o.getLastName(), o.getFirstName(), o.getMiddleName()))
                .toList();
    }
}
