package com.user.jobapplicationtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.jobapplicationtracker.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByUsername(String username);

}
