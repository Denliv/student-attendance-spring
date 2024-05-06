package student_attendance.service.service_interface;

import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.subject_request.AddSubjectRequest;
import student_attendance.request.subject_request.DeleteSubjectRequest;
import student_attendance.request.subject_request.EditSubjectRequest;
import student_attendance.request.subject_request.GetSubjectByIdRequest;
import student_attendance.response.subject_response.AddSubjectResponse;
import student_attendance.response.subject_response.GetSubjectByIdResponse;

import java.util.List;

public interface ISubjectService {
    AddSubjectResponse add(AddSubjectRequest request) throws NotFoundService;

    void delete(DeleteSubjectRequest request) throws NotFoundService;

    void edit(EditSubjectRequest request) throws ServiceException;

    GetSubjectByIdResponse getById(GetSubjectByIdRequest request) throws NotFoundService;

    List<GetSubjectByIdResponse> getAll();
}
