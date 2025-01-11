package com.example.service;

import com.example.entity.Review;
import com.example.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review review) {
        if (reviewRepository.existsById(id)) {
            review.setId(id);
            return reviewRepository.save(review);
        } else {
            return null; // or throw an exception
        }
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
