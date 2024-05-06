package student_attendance.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.teacher_request.AddTeacherRequest;
import student_attendance.request.teacher_request.DeleteTeacherRequest;
import student_attendance.request.teacher_request.EditTeacherRequest;
import student_attendance.request.teacher_request.GetTeacherByIdRequest;
import student_attendance.service.service_interface.ITeacherService;

@RestController
public class TeacherController {
    @Autowired
    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping(value = "/addTeacher")
    public ResponseEntity<?> addTeacher(@Valid @RequestBody AddTeacherRequest request) throws NotFoundService {
        return new ResponseEntity<>(teacherService.add(request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteTeacherById")
    public ResponseEntity<?> deleteTeacherById(@Valid @RequestBody DeleteTeacherRequest request) throws NotFoundService {
        teacherService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/editTeacher")
    public ResponseEntity<?> editTeacher(@Valid @RequestBody EditTeacherRequest request) throws ServiceException {
        teacherService.edit(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getTeacherById")
    public ResponseEntity<?> getTeacherById(@Valid @RequestBody GetTeacherByIdRequest request) throws NotFoundService {
        return new ResponseEntity<>(teacherService.getById(request), HttpStatus.OK);
    }

    @GetMapping("/getTeachers")
    public ResponseEntity<?> getTeachers() {
        return new ResponseEntity<>(teacherService.getAll(), HttpStatus.OK);
    }
}
