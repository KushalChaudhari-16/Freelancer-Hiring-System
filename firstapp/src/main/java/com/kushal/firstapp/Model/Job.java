package com.kushal.firstapp.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;  // Client who posted the job

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Double budget;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String category;  // Added category field
    
   // Added budget field
    public enum Status {
        OPEN, CLOSED , COMPLETED
    }
}
