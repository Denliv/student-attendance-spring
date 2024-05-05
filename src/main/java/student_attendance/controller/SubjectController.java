package student_attendance.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student_attendance.exception.service_exception.NotFoundService;
import student_attendance.exception.service_exception.ServiceException;
import student_attendance.request.subject_request.AddSubjectRequest;
import student_attendance.request.subject_request.DeleteSubjectRequest;
import student_attendance.request.subject_request.EditSubjectRequest;
import student_attendance.request.subject_request.GetSubjectByIdRequest;
import student_attendance.service.service_interface.ISubjectService;

@RestController
public class SubjectController {
    @Autowired
    private final ISubjectService subjectService;

    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(value = "/addSubject")
    public ResponseEntity<?> addSubject(@Valid @RequestBody AddSubjectRequest request) throws NotFoundService {
        return new ResponseEntity<>(subjectService.add(request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubjectById")
    public ResponseEntity<?> deleteSubjectById(@Valid @RequestBody DeleteSubjectRequest request) throws NotFoundService {
        subjectService.delete(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/editSubject")
    public ResponseEntity<?> editSubject(@Valid @RequestBody EditSubjectRequest request) throws ServiceException {
        subjectService.edit(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getSubjectById")
    public ResponseEntity<?> getSubjectById(@Valid @RequestBody GetSubjectByIdRequest request) throws NotFoundService {
        return new ResponseEntity<>(subjectService.getById(request), HttpStatus.OK);
    }

    @GetMapping("/getSubjects")
    public ResponseEntity<?> getSubjects() throws NotFoundService {
        return new ResponseEntity<>(subjectService.getAll(), HttpStatus.OK);
    }
}
