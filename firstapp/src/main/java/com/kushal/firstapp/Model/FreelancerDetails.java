package com.kushal.firstapp.Model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "freelancer_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // Linked to User table

    private String title;  // Freelancer Tagline
    private Integer experience;  
    private String portfolioUrl; 
    private String imageUrl; // Profile Image URL
    private Double averageRating; // Calculated from reviews

    private String skills; // Storing skills as a comma-separated string

    public List<String> getSkillsList() {
        return skills != null ? Arrays.asList(skills.split(",")) : null;
    }

    public void setSkillsList(List<String> skillsList) {
        this.skills = String.join(",", skillsList);
    }
}
