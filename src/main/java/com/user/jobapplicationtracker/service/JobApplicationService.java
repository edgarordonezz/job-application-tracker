package com.user.jobapplicationtracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.user.jobapplicationtracker.models.JobApplication;
import com.user.jobapplicationtracker.repository.JobApplicationRepository;

@Service
public class JobApplicationService {
    private final JobApplicationRepository repository;

    // Inject repository into the service
    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public List<JobApplication> getAllByUser(String username) {
        return repository.findByUsername(username);
    }

    // Get job application by id
    public Optional<JobApplication> getById(Long id) {
        return repository.findById(id);
    }

    // Create new job application record
    public JobApplication create (JobApplication newApplication) {
        return repository.save(newApplication);
    }

    // Update job application
    public JobApplication update(Long id, JobApplication updated, String username) {
        return repository.findById(id)
                .map(existing -> {
                    if (!existing.getUsername().equals(username)) {
                        throw new RuntimeException("Forbidden");
                    }
                    existing.setCompany(updated.getCompany());
                    existing.setPosition(updated.getPosition());
                    existing.setStatus(updated.getStatus());
                    existing.setAppliedDate(updated.getAppliedDate());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Job application not found"));
    }

    // Delete job application by id
    public void delete(Long id, String username) {
        JobApplication app = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job application not found with id: " + id));
        if (!app.getUsername().equals(username)) {
            throw new RuntimeException("Forbidden");
        }
        repository.deleteById(id);
    }
}
