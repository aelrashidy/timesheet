package com.company.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "timesheet_logging")
public class TimesheetLogging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Column(name = "login", nullable = false)
    private LocalDateTime loginTime;

    @Column(name = "logout", nullable = false)
    private LocalDateTime logoutTime;

    public TimesheetLogging(LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
    public TimesheetLogging(Long userID, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.userID = userID;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
    public TimesheetLogging(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }
}
