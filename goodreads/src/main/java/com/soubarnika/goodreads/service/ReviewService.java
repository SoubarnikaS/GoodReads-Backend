package com.soubarnika.goodreads.service;

import com.soubarnika.goodreads.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {

    List<Review> fetchReviewByUserId(Long userId);

    List<Review> fetchAllReviews(int pageSize, int pageNumber, String field);

    Boolean addReview( Long userId, Long bookId, Review review);
}