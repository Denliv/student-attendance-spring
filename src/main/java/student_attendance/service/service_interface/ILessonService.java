package student_attendance.service.service_interface;

import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.lesson_request.*;
import student_attendance.response.lesson_response.AddLessonResponse;
import student_attendance.response.lesson_response.GetLessonByIdResponse;

import java.util.List;

public interface ILessonService {
    AddLessonResponse add(AddLessonRequest request) throws ServiceException;

    void delete(DeleteLessonRequest request) throws NotFoundService;

    void edit(EditLessonRequest request) throws ServiceException;

    GetLessonByIdResponse getById(GetLessonByIdRequest request) throws NotFoundService;

    List<GetLessonByIdResponse> getByGroup(GetLessonsByGroupRequest request) throws ServiceException;

    List<GetLessonByIdResponse> getByTeacher(GetLessonsByTeacherRequest request) throws ServiceException;
}
