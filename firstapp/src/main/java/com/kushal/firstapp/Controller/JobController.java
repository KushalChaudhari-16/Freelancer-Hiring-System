package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kushal.firstapp.Model.Job;
import com.kushal.firstapp.Model.Job.Status;
import com.kushal.firstapp.dto.JobDTO;
import com.kushal.firstapp.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    
    @Autowired
    private JobService jobService;

    // @PostMapping("/post")
    // public Job postJob(@RequestBody Job job) {
    //     return jobService.postJob(job);
    // }

    @PostMapping("/post")
public ResponseEntity<Job> postJob(@RequestBody Job job) {
    if (job.getCategory() == null || job.getCategory().isEmpty()) {
        return ResponseEntity.badRequest().build();
    }
    Job savedJob = jobService.postJob(job);
    return ResponseEntity.ok(savedJob);
}

    @GetMapping("/open")
    public List<JobDTO> getOpenJobs(@RequestParam Long freelancerId) {
        return jobService.getOpenJobs(freelancerId);
    }
    

    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }


    @GetMapping("/client/{clientId}")
   
    public List<JobDTO> getClientJobs(@PathVariable Long clientId) {
        return jobService.getJobsByClient(clientId);
    }
    
    @PutMapping("/{jobId}/status")
public ResponseEntity<Job> updateJobStatus(@PathVariable Long jobId, @RequestParam Status status) {
    Job updatedJob = jobService.updateJobStatus(jobId, status);
    return ResponseEntity.ok(updatedJob);
}

    @GetMapping("/filterjobs")
    public ResponseEntity<List<JobDTO>> getFilteredJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
        
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) Double maxBudget,
            @RequestParam(required = false) String Role,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false, defaultValue = "newest") String sortBy
    ) {
        
        return ResponseEntity.ok(jobService.getFilteredJobs(search, category,  minBudget, maxBudget ,Role ,userId, sortBy));
    }
}


