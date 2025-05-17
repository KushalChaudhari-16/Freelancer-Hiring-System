package com.kushal.firstapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kushal.firstapp.Model.Review;
import com.kushal.firstapp.Model.User;
import com.kushal.firstapp.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public Review submitReview(@RequestBody Review review) {
        return reviewService.submitReview(review);
    }

    @GetMapping("/{freelancerId}")
    public List<Review> getReviewsForFreelancer(@PathVariable Long freelancerId) {
       User freelancer = new User();
        freelancer.setUserId(freelancerId);
        return reviewService.getReviewsForFreelancer(freelancer);
    }

    @GetMapping("/client/{clientId}/freelancer/{freelancerId}")
public Review getClientReviewForFreelancer(@PathVariable Long clientId, @PathVariable Long freelancerId) {
    return reviewService.getClientReviewForFreelancer(clientId, freelancerId);
}
    
}
