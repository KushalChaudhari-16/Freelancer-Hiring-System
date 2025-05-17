package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kushal.firstapp.Model.Job;
import com.kushal.firstapp.Model.Proposal;
import com.kushal.firstapp.service.CloudinaryService;
import com.kushal.firstapp.service.ProposalService;

import io.jsonwebtoken.io.IOException;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;
    @Autowired
    private CloudinaryService cloudinaryService;

    // @PostMapping("/submit")
    // public Proposal submitProposal(@RequestBody Proposal proposal) {
    // return proposalService.submitProposal(proposal);
    // }

    @PostMapping("/submit")
    public Proposal submitProposal(
            @RequestPart("proposal") Proposal proposal,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileUrl = cloudinaryService.uploadFile(file, "proposals");
                proposal.setResumeUrl(fileUrl);
            }
            return proposalService.submitProposal(proposal);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
    

    @GetMapping("/job/{jobId}")
    public List<Proposal> getProposalsByJob(@PathVariable Long jobId) {
        Job job = new Job();
        job.setJobId(jobId);
        return proposalService.getProposalsForJob(job);
    }

    // @PutMapping("/accept/{proposalId}")
    // public Proposal acceptProposal(@PathVariable Long proposalId) {
    // return proposalService.acceptProposal(proposalId);
    // }

    @PutMapping("/{proposalId}/accept")
    public ResponseEntity<Proposal> acceptProposal(@PathVariable Long proposalId) {
        Proposal updatedProposal = proposalService.acceptProposal(proposalId);
        return ResponseEntity.ok(updatedProposal);
    }

    @GetMapping("/has-applied")
    public ResponseEntity<Boolean> hasFreelancerApplied(
            @RequestParam Long jobId, @RequestParam Long freelancerId) {
        boolean hasApplied = proposalService.hasFreelancerApplied(jobId, freelancerId);
        return ResponseEntity.ok(hasApplied);
    }

    @GetMapping("/freelancer/{freelancerId}")
    public List<Proposal> getFreelancerProposals(@PathVariable Long freelancerId) {
        return proposalService.getProposalsByFreelancer(freelancerId);
    }

    // cloudinary logic

}
