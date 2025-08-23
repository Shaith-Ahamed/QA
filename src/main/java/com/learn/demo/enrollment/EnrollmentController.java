
package com.learn.demo.enrollment;



import com.learn.demo.enrollment.EnrollmentRequest;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;





@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }


    @PostMapping("/doEnrollment")
    public ResponseEntity<String> doEnrollment(@RequestBody  EnrollmentRequest body) {
        var saved = service.enrollByIds(body.getUserId(), body.getCourseId());

        var id = saved.getEnrollmentId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Enrollment " + id + " created");
    }
}




