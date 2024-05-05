package student_attendance.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.student_request.*;
import student_attendance.service.service_interface.IStudentService;

@RestController
public class StudentController {
    @Autowired
    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/addStudent")
    public ResponseEntity<?> addStudent(@Valid @RequestBody AddStudentRequest request) throws NotFoundService {
        return new ResponseEntity<>(studentService.add(request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudentById")
    public ResponseEntity<?> deleteStudentById(@Valid @RequestBody DeleteStudentRequest request) throws NotFoundService {
        studentService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/editStudent")
    public ResponseEntity<?> editStudent(@Valid @RequestBody EditStudentRequest request) throws ServiceException {
        studentService.edit(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getStudentById")
    public ResponseEntity<?> getStudentById(@Valid @RequestBody GetStudentByIdRequest request) throws NotFoundService {
        return new ResponseEntity<>(studentService.getById(request), HttpStatus.OK);
    }

    @GetMapping("/getStudentByGroupId")
    public ResponseEntity<?> getStudentByGroupId(@Valid @RequestBody GetStudentsByGroupIdRequest request) throws NotFoundService {
        return new ResponseEntity<>(studentService.getByGroupId(request), HttpStatus.OK);
    }
}
