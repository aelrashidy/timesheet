package com.company.timesheet.repository;

import com.company.timesheet.model.LoginTrials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginTrialsRepository extends JpaRepository<LoginTrials, Long> {

    boolean existsByUserId(long id);
    Optional<LoginTrials> findByUserId(long id);
}
