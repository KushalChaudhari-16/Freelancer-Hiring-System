package com.kushal.firstapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kushal.firstapp.Model.Deliverable;
import com.kushal.firstapp.Model.Proposal;
import com.kushal.firstapp.Model.Deliverable.Status;
import com.kushal.firstapp.Repository.DeliverableRepository;
import com.kushal.firstapp.Repository.ProposalRepository;

import jakarta.transaction.Transactional;


@Service
public class DeliverableService {
    
    @Autowired
    private DeliverableRepository deliverableRepository;
    @Autowired
    private JobService jobservice;

    @Autowired
    private ProposalRepository proposalRepository;

    // public Deliverable submitWork(Deliverable deliverable) {
    //     deliverable.setStatus(Deliverable.Status.SUBMITTED);
    //     return deliverableRepository.save(deliverable);
    // }

 @Transactional
public Deliverable approveWork(Long deliverableId) {
    Deliverable deliverable = deliverableRepository.findById(deliverableId)
            .orElseThrow(() -> new RuntimeException("Deliverable not found"));
    deliverable.setStatus(Deliverable.Status.COMPLETED);
    
    // 🚀 Jab Deliverable COMPLETED ho, tab Job bhi COMPLETE ho jaaye
    jobservice.markJobAsCompleted(deliverable.getProposal().getJob().getJobId());
    
    return deliverableRepository.save(deliverable);
}


    public Deliverable requestChanges(Long deliverableId, String feedback) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId).orElseThrow(
                () -> new RuntimeException("Deliverable not found")
        );
        deliverable.setStatus(Deliverable.Status.CHANGES_REQUESTED);
        deliverable.setFeedback(feedback);
        return deliverableRepository.save(deliverable);
    }


//     public Deliverable resubmitDeliverable(Long deliverableId, String fileUrl, String message) {
//         Deliverable deliverable = deliverableRepository.findById(deliverableId)
//                 .orElseThrow(() -> new RuntimeException("Deliverable not found"));
    
//         if (!deliverable.getStatus().equals(Deliverable.Status.CHANGES_REQUESTED)) {
//             throw new RuntimeException("Resubmission is only allowed if changes are requested");
//         }
    
//         deliverable.setFileUrl(fileUrl);
//         deliverable.setMessage(message);
//         deliverable.setStatus(Deliverable.Status.SUBMITTED);
//         deliverable.setFeedback(null); // Reset feedback on resubmission
    
//         return deliverableRepository.save(deliverable);
// }






 
    @Autowired
    private CloudinaryService cloudinaryService; // Using your Cloudinary Service

    // ✅ Submit Deliverable (File Upload + DB Save)
    public Deliverable submitWork(Deliverable deliverable, MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadFile2(file);
            deliverable.setFileUrl(fileUrl);
             deliverable.setStatus(Deliverable.Status.SUBMITTED);
            return deliverableRepository.save(deliverable);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed!", e);
        }
    }

    // ✅ Resubmit Deliverable (Only if changes requested)
    public Deliverable resubmitDeliverable(Long deliverableId, MultipartFile file, String message) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new RuntimeException("Deliverable not found"));

        if (!deliverable.getStatus().equals(Deliverable.Status.CHANGES_REQUESTED)) {
            throw new RuntimeException("Cannot resubmit unless changes are requested.");
        }

        try {
            String newFileUrl = cloudinaryService.uploadFile2(file); // Upload new file
            deliverable.setFileUrl(newFileUrl);
            deliverable.setMessage(message);
            deliverable.setStatus(Deliverable.Status.SUBMITTED);
            deliverable.setFeedback(null); // Clear previous feedback
            return deliverableRepository.save(deliverable);
        } catch (Exception e) {
            throw new RuntimeException("Resubmission failed!", e);
        }
    }




  public List<Deliverable> getOngoingDeliverables(Long freelancerId) {
    List<Long> proposalIds = proposalRepository.findByFreelancerUserId(freelancerId)
                                               .stream()
                                               .map(Proposal::getProposalId)
                                               .collect(Collectors.toList());

    return deliverableRepository.findByProposal_ProposalIdInAndStatusNot(proposalIds, Status.COMPLETED);
}

public List<Deliverable> getCompletedDeliverables(Long freelancerId) {
    List<Long> proposalIds = proposalRepository.findByFreelancerUserId(freelancerId)
                                               .stream()
                                               .map(Proposal::getProposalId)
                                               .collect(Collectors.toList());

    return deliverableRepository.findByProposal_ProposalIdInAndStatus(proposalIds, Status.COMPLETED);
}

    

}
