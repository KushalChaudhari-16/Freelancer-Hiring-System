package com.kushal.firstapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kushal.firstapp.Model.FreelancerDetails;

import java.util.Optional;

public interface FreelancerDetailsRepository extends JpaRepository<FreelancerDetails, Long> {
    Optional<FreelancerDetails> findByUserUserId(Long userId);
}
