package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kushal.firstapp.Model.Deliverable;
import com.kushal.firstapp.Repository.FreelancerRepository;
import com.kushal.firstapp.dto.TopFreelancerDto;
import com.kushal.firstapp.service.DeliverableService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/freelancers")
public class FreelancerController {
    
    @Autowired
    private FreelancerRepository freelancerRepository;
    @Autowired
    private DeliverableService deliverableService;

    @GetMapping("/top")
    public ResponseEntity<List<TopFreelancerDto>> getTopFreelancers() {
        return ResponseEntity.ok(freelancerRepository.getTopFreelancers());
    }


      @GetMapping("/dashboard/{freelancerId}")
    public ResponseEntity<?> getFreelancerDashboardData(@PathVariable Long freelancerId) {
   
        List<Deliverable> ongoingDeliverables = deliverableService.getOngoingDeliverables(freelancerId);
        List<Deliverable> completedDeliverables = deliverableService.getCompletedDeliverables(freelancerId);

        Map<String, Object> response = new HashMap<>();
      
        response.put("ongoing", ongoingDeliverables);
        response.put("completed", completedDeliverables);

        return ResponseEntity.ok(response);
    }
}
