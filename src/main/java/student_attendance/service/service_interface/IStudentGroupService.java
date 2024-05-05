package student_attendance.service.service_interface;

import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.student_group_request.AddStudentGroupRequest;
import student_attendance.request.student_group_request.DeleteStudentGroupRequest;
import student_attendance.request.student_group_request.EditStudentGroupRequest;
import student_attendance.request.student_group_request.GetStudentGroupByIdRequest;
import student_attendance.response.student_group_response.AddStudentGroupResponse;
import student_attendance.response.student_group_response.GetStudentGroupByIdResponse;

public interface IStudentGroupService {
    AddStudentGroupResponse add(AddStudentGroupRequest request);

    void delete(DeleteStudentGroupRequest request) throws ServiceException;

    void edit(EditStudentGroupRequest request) throws NotFoundService;

    GetStudentGroupByIdResponse getById(GetStudentGroupByIdRequest request) throws NotFoundService;
}
