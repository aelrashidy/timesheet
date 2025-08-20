package com.company.timesheet.repository;

import com.company.timesheet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This interface extends JpaRepository, which provides CRUD operations for User entities
    Boolean existsByEmail(String email);
}
