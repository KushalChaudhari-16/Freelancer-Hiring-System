package com.kushal.firstapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kushal.firstapp.Model.Job;
import org.springframework.data.repository.query.Param;
import com.kushal.firstapp.Model.Proposal;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByJob(Job job);

    @Query("SELECT p.freelancer.userId, COUNT(p) FROM Proposal p WHERE p.status = 'ACCEPTED' GROUP BY p.freelancer.userId")
 
    List<Object[]> countProjectsPerFreelancer();
  
;
         /*above query : these is sql:
            SELECT freelancer_id, COUNT(*) AS total_projects 
        FROM proposals 
        WHERE status = 'ACCEPTED' 
        GROUP BY freelancer_id; */ 
    
    @Query("SELECT COUNT(p) > 0 FROM Proposal p WHERE p.job.jobId = :jobId AND p.freelancer.userId = :freelancerId")
    boolean hasFreelancerApplied(@Param("jobId") Long jobId, @Param("freelancerId") Long userId);
    

    @Query("SELECT COUNT(p) FROM Proposal p WHERE p.job.jobId = :jobId")
    int countByJobJobId(@Param("jobId") Long jobId);
    
    List<Proposal> findByFreelancerUserId(Long freelancerId); // Freelancer ke applied jobs fetch karna


}