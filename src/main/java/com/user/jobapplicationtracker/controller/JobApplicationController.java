package com.user.jobapplicationtracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.jobapplicationtracker.models.JobApplication;
import com.user.jobapplicationtracker.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }
    // GET all applications
    @GetMapping
    public List<JobApplication> getAll() {
        return service.getAll();
    }

    // GET application by id
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        Optional<JobApplication> result = service.getById(id);
        // 200
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
        // 404
            return ResponseEntity.notFound().build();
        }
    }

    // POST create new application
    @PostMapping
    public JobApplication create (@RequestBody JobApplication application) {
        return service.create(application);
    }

    // PUT update application
    @PutMapping("/{id}")
    public JobApplication update (@PathVariable Long id, @RequestBody JobApplication updated) {
        return service.update(id, updated);
    }

    // DELETE remove application 
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id) {
        service.delete(id);
    }
}
