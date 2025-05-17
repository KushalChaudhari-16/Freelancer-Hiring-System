package com.kushal.firstapp.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "deliverables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deliverable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliverableId;

    @OneToOne
    @JoinColumn(name = "proposal_id", nullable = false)
    private Proposal proposal;  // Proposal linked to this deliverable

    @Column(nullable = false, length = 500)
    private String fileUrl; // GitHub link ya file ka URL

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(columnDefinition = "TEXT")
    private String feedback;  // Client feedback (if changes needed)

    public enum Status {
        SUBMITTED, CHANGES_REQUESTED, COMPLETED
    }
}
