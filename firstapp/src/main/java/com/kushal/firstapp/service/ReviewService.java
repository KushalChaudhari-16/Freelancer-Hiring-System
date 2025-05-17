package com.kushal.firstapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kushal.firstapp.Model.Review;
import com.kushal.firstapp.Model.User;
import com.kushal.firstapp.Repository.ReviewRepository;
import com.kushal.firstapp.Repository.UserRepository;

import java.util.List;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
        // public Review submitReview(Review review) {
        //     return reviewRepository.save(review);
        // }
        public Review submitReview(Review review) {
            // Save the new review
            Review savedReview = reviewRepository.save(review);
        
            // Get the freelancer ID from saved review
            Long freelancerId = savedReview.getFreelancer().getUserId();  // or getUserId()
        
            // Fetch full freelancer from DB
            User fullFreelancer = userRepository.findById(freelancerId)
                    .orElseThrow(() -> new RuntimeException("Freelancer not found"));
        
            // Fetch all reviews for this freelancer
            List<Review> reviewsForFreelancer = reviewRepository.findByFreelancer(fullFreelancer);
        
            // Calculate average rating
            double avg = reviewsForFreelancer.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
        
            // Update user's review column
            fullFreelancer.setReview(avg);
            userRepository.save(fullFreelancer);
        
            return savedReview;
        }
        
    public List<Review> getReviewsForFreelancer(User freelancer) {
        return reviewRepository.findByFreelancer(freelancer);
    }

    public Review getClientReviewForFreelancer(Long clientId, Long freelancerId) {
        return reviewRepository.findByClientAndFreelancer(clientId, freelancerId);
    }
    
}
