package com.soubarnika.goodreads.repository;

import com.soubarnika.goodreads.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO Review (RATING,REVIEW_CONTENT,BOOK_BOOK_ID,USER_ID) VALUES(:rating, :content, :bookid, :userid)", nativeQuery = true)
//    int addReview(Long rating, String content, Long bookid, Long userid);
    List<Review> findByUser_Id(Long userId);
}