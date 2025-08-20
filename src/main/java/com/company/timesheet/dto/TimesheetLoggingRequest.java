package com.company.timesheet.dto;

import jakarta.persistence.Column;

import java.util.Date;


public class TimesheetLoggingRequest {

    private Date loginTime;
    private Date logoutTime;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public TimesheetLoggingRequest(Date loginTime, Date logoutTime) {
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
}
