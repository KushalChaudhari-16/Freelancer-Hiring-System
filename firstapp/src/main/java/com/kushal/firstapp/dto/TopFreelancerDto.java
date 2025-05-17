package com.kushal.firstapp.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
 public class TopFreelancerDto {
    private Long userId;
    private String name;
    private String photo_url;
    private Double averageRating;
    private Long totalProjects;

   


}

