package student_attendance.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.student_group_request.AddStudentGroupRequest;
import student_attendance.request.student_group_request.DeleteStudentGroupRequest;
import student_attendance.request.student_group_request.EditStudentGroupRequest;
import student_attendance.request.student_group_request.GetStudentGroupByIdRequest;
import student_attendance.service.service_interface.IStudentGroupService;

@RestController
public class StudentGroupController {
    @Autowired
    private final IStudentGroupService studentGroupService;

    public StudentGroupController(IStudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @PostMapping(value = "/addStudentGroup")
    public ResponseEntity<?> addStudentGroup(@Valid @RequestBody AddStudentGroupRequest request) throws ServiceException {
        try {
            return new ResponseEntity<>(studentGroupService.add(request), HttpStatus.OK);
        } catch (Exception e) {
            throw new ServiceException("this group already exists");
        }
    }

    @DeleteMapping("/deleteStudentGroupById")
    public ResponseEntity<?> deleteStudentGroup(@Valid @RequestBody DeleteStudentGroupRequest request) throws ServiceException {
        studentGroupService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/editStudentGroup")
    public ResponseEntity<?> editStudentGroup(@Valid @RequestBody EditStudentGroupRequest request) throws NotFoundService {
        studentGroupService.edit(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getStudentGroupById")
    public ResponseEntity<?> getStudentGroupById(@Valid @RequestBody GetStudentGroupByIdRequest request) throws NotFoundService {
        return new ResponseEntity<>(studentGroupService.getById(request), HttpStatus.OK);
    }
}
