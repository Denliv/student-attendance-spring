package student_attendance.service.service_interface;

import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.teacher_request.AddTeacherRequest;
import student_attendance.request.teacher_request.DeleteTeacherRequest;
import student_attendance.request.teacher_request.EditTeacherRequest;
import student_attendance.request.teacher_request.GetTeacherByIdRequest;
import student_attendance.response.teacher_response.AddTeacherResponse;
import student_attendance.response.teacher_response.GetTeacherByIdResponse;

import java.util.List;

public interface ITeacherService {
    AddTeacherResponse add(AddTeacherRequest request);

    void delete(DeleteTeacherRequest request) throws NotFoundService;

    void edit(EditTeacherRequest request) throws ServiceException;

    GetTeacherByIdResponse getById(GetTeacherByIdRequest request) throws NotFoundService;

    List<GetTeacherByIdResponse> getAll() throws NotFoundService;
}
