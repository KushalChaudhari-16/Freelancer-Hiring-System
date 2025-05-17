package com.kushal.firstapp.service;

import com.kushal.firstapp.Model.Job;
import com.kushal.firstapp.Model.Proposal;
import com.kushal.firstapp.Repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProposalService {
    
    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal submitProposal(Proposal proposal) {
        return proposalRepository.save(proposal);
    }

    // public List<Proposal> getProposalsForJob(Job job) {
    //     return proposalRepository.findByJob(job);
    // }
    public List<Proposal> getProposalsForJob(Job job) {
        List<Proposal> proposals = proposalRepository.findByJob(job);
        
        // Freelancer details ensure karna
        for (Proposal proposal : proposals) {
            proposal.getFreelancer().getName(); // Lazy loading issue avoid karne ke liye
        }
    
        return proposals;
    }
    
    public Proposal acceptProposal(Long proposalId) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new RuntimeException("Proposal not found"));
        
        // Proposal ko ACCEPTED karna
        proposal.setStatus(Proposal.Status.ACCEPTED);
        proposalRepository.save(proposal);
    
        // Baaki sab proposals REJECTED karna
        List<Proposal> allProposals = proposalRepository.findByJob(proposal.getJob());
        for (Proposal p : allProposals) {
            if (!p.getProposalId().equals(proposalId)) {
                p.setStatus(Proposal.Status.REJECTED);
                proposalRepository.save(p);
            }
        }
        
        return proposal;
    }
    

    public boolean hasFreelancerApplied(Long jobId, Long freelancerId) {
        return proposalRepository.hasFreelancerApplied(jobId, freelancerId);
    }


    public List<Proposal> getProposalsByFreelancer(Long freelancerId) {
        return proposalRepository.findByFreelancerUserId(freelancerId);
    }
    
}
