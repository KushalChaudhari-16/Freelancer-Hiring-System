package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kushal.firstapp.Model.FreelancerDetails;
import com.kushal.firstapp.service.FreelancerDetailsService;


@RestController
@RequestMapping("/freelancer")
public class FreelancerDetailsController {

    @Autowired
    private FreelancerDetailsService freelancerDetailsService;

    @GetMapping("/{userId}")
    public FreelancerDetails getFreelancerProfile(@PathVariable Long userId) {
        return freelancerDetailsService.getFreelancerDetails(userId);
    }

    @PutMapping("/update")
    public FreelancerDetails updateFreelancerProfile(@RequestBody FreelancerDetails freelancerDetails) {
        return freelancerDetailsService.updateFreelancerDetails(freelancerDetails);
    }

    @PostMapping("/{userId}/add-skill")
    public FreelancerDetails addSkill(@PathVariable Long userId, @RequestParam String skill) {
        freelancerDetailsService.addSkill(userId, skill);
        return freelancerDetailsService.getFreelancerDetails(userId);
    }

    @DeleteMapping("/{userId}/remove-skill")
    public FreelancerDetails removeSkill(@PathVariable Long userId, @RequestParam String skill) {
        freelancerDetailsService.removeSkill(userId, skill);
        return freelancerDetailsService.getFreelancerDetails(userId);
    }
}
