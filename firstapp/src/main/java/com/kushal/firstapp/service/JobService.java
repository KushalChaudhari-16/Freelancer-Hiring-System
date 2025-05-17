package com.kushal.firstapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kushal.firstapp.Model.Job;
import com.kushal.firstapp.Repository.JobRepository;
import com.kushal.firstapp.Repository.ProposalRepository;
import com.kushal.firstapp.dto.JobDTO;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ProposalRepository proposalRepository;

    public Job postJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllOpenJobs() {
        return jobRepository.findByStatus(Job.Status.OPEN);
    }
    // public List<Job> getJobsByClient(Long clientId) {
    // return jobRepository.findByClientUserId(clientId);
    // }

    public List<JobDTO> getJobsByClient(Long clientId) {
        List<Job> jobs = jobRepository.findByClientUserId(clientId);
        List<JobDTO> jobDTOs = new ArrayList<>();

        for (Job job : jobs) {
            int applicationCount = proposalRepository.countByJobJobId(job.getJobId());
            jobDTOs.add(new JobDTO(job.getJobId(), job.getTitle(), job.getBudget(),
                    job.getClient().getName(), applicationCount, job.getStatus().toString()));
        }
        return jobDTOs;
    }

    public Job updateJobStatus(Long jobId, Job.Status newStatus) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(newStatus);
        return jobRepository.save(job);
    }

    public List<JobDTO> getOpenJobs(Long freelancerId) {
        List<Job> jobs = jobRepository.findAll(); // Fetch all jobs
        List<JobDTO> jobDTOs = new ArrayList<>();

        for (Job job : jobs) {
            boolean hasApplied = proposalRepository.hasFreelancerApplied(job.getJobId(), freelancerId);
            jobDTOs.add(
                    new JobDTO(job.getJobId(), job.getTitle(), job.getBudget(), job.getClient().getName(), hasApplied,
                            job.getCategory(), job.getDescription()));
        }
        return jobDTOs;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // mark job as completed when deliverable approved...
    @Transactional
    public void markJobAsCompleted(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(Job.Status.COMPLETED);
        jobRepository.save(job);
    }

    public List<JobDTO> getFilteredJobs(String search, String category, Double minBudget, Double maxBudget, String Role,
            Long userId, String sortBy) {
        List<Job> jobs = jobRepository.findJobsWithFilters(search, category, minBudget, maxBudget, sortBy);
        List<JobDTO> jobDTOs = new ArrayList<>();
        for (Job job : jobs) {
            boolean hasApplied = false;

            System.out.println("Role is " + Role);
            System.out.println("user id is " + userId);

            if (Role.equals("FREELANCER")) {
                System.out.println("hello mic check");
                System.out.println("job is is " + job.getJobId());
                hasApplied = proposalRepository.hasFreelancerApplied(job.getJobId(), userId);

            }
            jobDTOs.add(new JobDTO(job.getJobId(), job.getTitle(), job.getBudget(), job.getClient().getName(),
                    hasApplied, job.getDescription(), job.getCategory()));
        }
        return jobDTOs;
    }

}