package com.user.jobapplicationtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.jobapplicationtracker.models.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

}
