package com.user.jobapplicationtracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.jobapplicationtracker.models.JobApplication;

// find 
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUsername(String username);
    Optional<JobApplication> findByIdAndUsername(Long id, String username);
}
