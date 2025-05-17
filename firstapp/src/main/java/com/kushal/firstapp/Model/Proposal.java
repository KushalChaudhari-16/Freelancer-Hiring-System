package com.kushal.firstapp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proposals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposalId;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;  // Job for which proposal is submitted

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;  // Freelancer who applied

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "resume_url")
    private String resumeUrl;
    

    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }
}
