package student_attendance.service.service_interface;

import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.student_request.*;
import student_attendance.response.student_response.AddStudentResponse;
import student_attendance.response.student_response.GetStudentByIdResponse;

import java.util.List;

public interface IStudentService {
    AddStudentResponse add(AddStudentRequest request) throws NotFoundService;

    void delete(DeleteStudentRequest request) throws NotFoundService;

    void edit(EditStudentRequest request) throws ServiceException;

    GetStudentByIdResponse getById(GetStudentByIdRequest request) throws NotFoundService;

    List<GetStudentByIdResponse> getByGroupId(GetStudentsByGroupIdRequest request) throws NotFoundService;
}
