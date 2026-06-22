package com.user.jobapplicationtracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public List<JobApplication> getAll(Authentication authentication) {
        return service.getAllByUser(authentication.getName());
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
    public ResponseEntity<JobApplication> create (@RequestBody JobApplication application, Authentication authentication) {
        application.setUsername(authentication.getName());
        JobApplication created = service.create(application);
        return ResponseEntity.status(201).body(created); // Return 201 if created
    }

    // PUT update application
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update (@PathVariable Long id, @RequestBody JobApplication updated, Authentication authentication) {
        try {
            return ResponseEntity.ok(service.update(id, updated, authentication.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE remove application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id, Authentication authentication) {
        try {
            service.delete(id, authentication.getName());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
