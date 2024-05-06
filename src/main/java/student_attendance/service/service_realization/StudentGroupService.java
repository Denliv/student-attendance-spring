package student_attendance.service.service_realization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import student_attendance.service.service_interface.IStudentGroupService;

@Service
public class StudentGroupService implements IStudentGroupService {
    @Autowired
    private IStudentGroupRepository groupRepository;
    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public AddStudentGroupResponse add(AddStudentGroupRequest request) {
        StudentGroup group = new StudentGroup(null, request.getName());
        groupRepository.save(group);
        return new AddStudentGroupResponse(group.getId(), group.getName());
    }

    @Override
    public void delete(DeleteStudentGroupRequest request) throws ServiceException {
        groupRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        if (studentRepository.findAllByGroupId(request.getId()).isEmpty()) {
            throw new ServiceException("can not delete group with students");
        }
        groupRepository.deleteById(request.getId());
    }

    @Override
    public void edit(EditStudentGroupRequest request) throws NotFoundService {
        groupRepository.findById(request.getId()).orElseThrow(() -> new NotFoundService("invalid group id"));
        groupRepository.update(request.getName(), request.getId());
    }

    @Override
    public GetStudentGroupByIdResponse getById(GetStudentGroupByIdRequest request) throws NotFoundService {
        return new GetStudentGroupByIdResponse(groupRepository
                .findById(request.getId())
                .orElseThrow(() -> new NotFoundService("invalid group id")));
    }
}
