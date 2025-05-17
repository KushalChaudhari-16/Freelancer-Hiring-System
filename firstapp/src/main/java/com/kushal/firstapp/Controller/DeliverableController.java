package com.kushal.firstapp.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kushal.firstapp.Model.Deliverable;
import com.kushal.firstapp.Repository.DeliverableRepository;
import com.kushal.firstapp.service.DeliverableService;

@RestController
@RequestMapping("/deliverables")
public class DeliverableController {
    
    @Autowired
    private DeliverableService deliverableService;
    @Autowired
    private DeliverableRepository deliverableRepository;

    // @PostMapping("/submit")
    // public Deliverable submitWork(@RequestBody Deliverable deliverable) {
    //     return deliverableService.submitWork(deliverable);
    // }

    @PutMapping("/approve/{deliverableId}")
    public Deliverable approveWork(@PathVariable Long deliverableId) {
        return deliverableService.approveWork(deliverableId);
    }

    @PutMapping("/request-changes/{deliverableId}")
    public Deliverable requestChanges(@PathVariable Long deliverableId, @RequestParam String feedback) {
        return deliverableService.requestChanges(deliverableId, feedback);
    }

@GetMapping("/proposal/{proposalId}")
public List<Deliverable> getDeliverablesByProposal(@PathVariable Long proposalId) {
    return deliverableRepository.findByProposal_ProposalId(proposalId);
}


// @PutMapping("/{deliverableId}/resubmit")
// public ResponseEntity<Deliverable> resubmitDeliverable(
//         @PathVariable Long deliverableId,
//         @RequestParam String fileUrl,
//         @RequestParam(required = false) String message) {
    
//     return ResponseEntity.ok(deliverableService.resubmitDeliverable(deliverableId, fileUrl, message));
// }




    
    @PostMapping("/submit")
    public Deliverable submitWork(@RequestPart("deliverable") Deliverable deliverable,
                                  @RequestPart("file") MultipartFile file) {
        return deliverableService.submitWork(deliverable, file);
    }

  
    @PutMapping("/{deliverableId}/resubmit")
    public Deliverable resubmitDeliverable(@PathVariable Long deliverableId,
                                           @RequestPart("file") MultipartFile file,
                                           @RequestParam(required = false) String message) {
        return deliverableService.resubmitDeliverable(deliverableId, file, message);
    }

}
