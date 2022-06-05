package com.az.assignment.dto;

import lombok.Getter;

public enum ActivityStatus {

    PENDING("Pending"),
    DONE("Done");
    @Getter
    private String status;
    ActivityStatus(String status) {
        this.status = status;
    }
}