package com.company.timesheet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "login_trials")
public class LoginTrials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "trial_time", nullable = false)
    private LocalDateTime trialTime;

    @Column(name = "expiration-time", nullable = false)
    private LocalDateTime expirationTime;

    public LoginTrials(){

    }
    public LoginTrials(Long userId, LocalDateTime trialTime, LocalDateTime expirationTime) {
        this.userId = userId;
        this.trialTime = trialTime;
        this.expirationTime = expirationTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(LocalDateTime trialTime) {
        this.trialTime = trialTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
