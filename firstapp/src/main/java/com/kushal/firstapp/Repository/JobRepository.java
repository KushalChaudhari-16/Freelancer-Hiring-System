package com.kushal.firstapp.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kushal.firstapp.Model.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<com.kushal.firstapp.Model.Job> findByStatus(Job.Status status);

  

    
    List<Job> findByClientUserId(Long clientId);


    @Query("SELECT j FROM Job j WHERE " +
    " j.status = 'OPEN' AND " +

    "(:search IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +

    "AND (:category IS NULL OR :category = '' OR j.category = :category) " +
    "AND (:minBudget IS NULL OR j.budget >= :minBudget) " +
    "AND (:maxBudget IS NULL OR j.budget <= :maxBudget) " +
    "ORDER BY " +
    "CASE WHEN :sortBy = 'newest' THEN j.jobId END DESC, " +
    "CASE WHEN :sortBy = 'highestBudget' THEN j.budget END DESC"
    
    )
List<Job> findJobsWithFilters(
     @Param("search") String search,
     @Param("category") String category,
     @Param("minBudget") Double minBudget,
     @Param("maxBudget") Double maxBudget,
     @Param("sortBy") String sortBy
);


}
