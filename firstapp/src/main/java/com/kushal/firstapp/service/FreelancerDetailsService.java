package com.kushal.firstapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kushal.firstapp.Model.FreelancerDetails;
import com.kushal.firstapp.Repository.FreelancerDetailsRepository;

import java.util.List;


@Service
public class FreelancerDetailsService {

    @Autowired
    private FreelancerDetailsRepository freelancerDetailsRepository;

    public FreelancerDetails getFreelancerDetails(Long userId) {
        return freelancerDetailsRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Freelancer details not found!"));
    }

    public FreelancerDetails updateFreelancerDetails(FreelancerDetails details) {
        return freelancerDetailsRepository.save(details);
    }

    public void addSkill(Long userId, String skill) {
        FreelancerDetails freelancer = getFreelancerDetails(userId);
        List<String> currentSkills = freelancer.getSkillsList();
        
        if (!currentSkills.contains(skill)) {
            currentSkills.add(skill);
            freelancer.setSkillsList(currentSkills);
            freelancerDetailsRepository.save(freelancer);
        }
    }

    public void removeSkill(Long userId, String skill) {
        FreelancerDetails freelancer = getFreelancerDetails(userId);
        List<String> currentSkills = freelancer.getSkillsList();

        if (currentSkills.contains(skill)) {
            currentSkills.remove(skill);
            freelancer.setSkillsList(currentSkills);
            freelancerDetailsRepository.save(freelancer);
        }
    }
}
