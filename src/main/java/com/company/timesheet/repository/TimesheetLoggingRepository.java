package com.company.timesheet.repository;

import com.company.timesheet.model.TimesheetLogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimesheetLoggingRepository extends JpaRepository<TimesheetLogging,Long> {
     Optional<List<TimesheetLogging>>findByUserID(Long id);
}
