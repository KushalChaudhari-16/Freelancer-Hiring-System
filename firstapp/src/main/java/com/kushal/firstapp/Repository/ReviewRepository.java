package com.kushal.firstapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kushal.firstapp.Model.Review;
import com.kushal.firstapp.Model.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFreelancer(User freelancer);

    @Query("SELECT r FROM Review r WHERE r.client.userId = :clientId AND r.freelancer.userId = :freelancerId")
Review findByClientAndFreelancer(@Param("clientId") Long clientId, @Param("freelancerId") Long freelancerId);

}