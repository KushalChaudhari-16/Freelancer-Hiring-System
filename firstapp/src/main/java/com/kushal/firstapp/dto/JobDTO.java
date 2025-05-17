package com.kushal.firstapp.dto;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private Long jobId;
    private String title;
    private double budget;
    private String clientName;
    private boolean applied; // New field to track application status
    private String Status;
    private String category;
    private String description;
    private int applicationCount;

    public JobDTO(Long jobId, String title, double budget, String clientName, int applicationCount, String status) {
        this.jobId = jobId;
        this.title = title;
        this.budget = budget;
        this.clientName = clientName;
        Status = status;
        this.applicationCount = applicationCount;
       
    }

    public JobDTO(Long jobId, String title, double budget, String clientName,boolean applied,String category,String description) {
        this.jobId = jobId;
        this.title = title;
        this.budget = budget;
        this.clientName = clientName;
       this.applied=applied;
       this.description=description;
       this.category=category;
    }

}
