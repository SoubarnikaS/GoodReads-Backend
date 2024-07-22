package com.soubarnika.goodreads.service.impl;

import com.soubarnika.goodreads.entity.Books;
import com.soubarnika.goodreads.entity.Review;
import com.soubarnika.goodreads.entity.Users;
import com.soubarnika.goodreads.repository.BookRepository;
import com.soubarnika.goodreads.repository.ReviewRepository;
import com.soubarnika.goodreads.repository.UsersRepository;
import com.soubarnika.goodreads.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public Boolean addReview(Long userId, Long bookId, Review reviewContent) {
        Users userOptional = usersRepository.findById(userId).orElse(null);
        Books bookOptional = bookRepository.findById(bookId).orElse(null);
//
//        if (userOptional.isPresent() && bookOptional.isPresent()) {
//            Review review = new Review();
//            review.setReviewContent(String.valueOf(reviewContent));
            reviewContent.setUser(userOptional);
            reviewContent.setBook(bookOptional);
//            log.info(reviewContent.toString());
            reviewRepository.save(reviewContent);
            return true;
//        }
//        else
//            return false;
    }

    @Override
    public List<Review> fetchReviewByUserId(Long userId) {
        return reviewRepository.findByUser_Id(userId);
    }

    @Override
    public List<Review> fetchAllReviews(int pageSize, int pageNumber, String field) {
        return reviewRepository.findAll(PageRequest.of(pageSize, pageNumber).withSort(Sort.by(Sort.Direction.ASC,field))).getContent();
    }




}
