package com.kushal.firstapp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;  // Client who gives review

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;  // Freelancer who gets review

    @Column(nullable = false)
    private Double rating; // Rating between 1 to 5

    @Column(columnDefinition = "TEXT")
    private String comment; // Feedback message
}

