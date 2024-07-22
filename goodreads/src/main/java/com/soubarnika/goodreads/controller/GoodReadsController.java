package com.soubarnika.goodreads.controller;

import com.soubarnika.goodreads.dto.UserDTO;
import com.soubarnika.goodreads.entity.Books;
import com.soubarnika.goodreads.entity.Review;
import com.soubarnika.goodreads.entity.Users;
import com.soubarnika.goodreads.exception.GoodReadsException;
import com.soubarnika.goodreads.service.BookService;
import com.soubarnika.goodreads.service.ReviewService;
import com.soubarnika.goodreads.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class GoodReadsController {

    private static final Logger userActionLogger = LoggerFactory.getLogger("com.example.goodReads");
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("checkService")
    public String checkService() {
        return "Service is up and Running!!!!";
    }

    @PostMapping("registerUser")
    public ResponseEntity<Boolean> registerUser(@RequestBody Users user) {
        try {
            userService.register(user);
            userActionLogger.info("User " + user.getName() + " registered");
        } catch (GoodReadsException e) {
            if (e.getMessage().equals("UniqueError")) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("loginUser")
    public ResponseEntity<Boolean> loginUser(@RequestBody UserDTO userDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);

        userActionLogger.info("User " + userDTO.getEmail() + " logged in at " + currentTime);
        return ResponseEntity.ok(userService.login(userDTO));
    }


    @PostMapping("addBookDetails")
    public ResponseEntity<Boolean> addBookDetails(@RequestBody Books book){
        log.info(book.toString());
        return ResponseEntity.ok(bookService.addBookDetails(book));
    }

    @GetMapping("fetchAllBookDetails")
    public ResponseEntity<List <Books> > fetchAllBookDetails(){
        try{
            return ResponseEntity.ok(bookService.fetchAllBookDetails());
        }catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("fetchBookDetailsByName/{bookName}")
    public ResponseEntity<Books> fetchBookDetailsByName(@PathVariable String bookName){
            return ResponseEntity.ok(bookService.fetchBookDetailsByName(bookName));

    }

    @GetMapping("fetchBookDetails/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<List <Books> > fetchBookDetails(@PathVariable int pageNumber, @PathVariable int pageSize, @PathVariable String field){
            return ResponseEntity.ok(bookService.fetchBookDetails(pageNumber, pageSize, field));

    }

    @PutMapping("updateBookDetails/{bookName}")
    public ResponseEntity<Boolean> editBookDetails(@PathVariable String bookName, @RequestBody Books book){
        log.info(book.toString());
        return ResponseEntity.ok(bookService.editBookDetails(bookName,book));
    }

    @PatchMapping("updatePassword/{email}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable String email,@RequestParam String password){
        try{
            return ResponseEntity.ok(userService.updatePassword(email,password));
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @DeleteMapping("deleteBookDetails/{bookName}")
    public ResponseEntity<Boolean> deleteBookDetails(@PathVariable String bookName){
        try{
            return ResponseEntity.ok(bookService.deleteBook(bookName));
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }

    @PostMapping("postReview/{userId}/{bookId}")
    public ResponseEntity<Boolean> postReview(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Review review){
        try{
            log.info(String.valueOf(review));
            return ResponseEntity.ok(reviewService.addReview(userId, bookId, review));
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("fetchAllReview/{pageSize}/{pageNumber}/{field}")
    public ResponseEntity<List <Review>> fetchAllR(@PathVariable int pageSize,@PathVariable  int pageNumber,@PathVariable  String field){
        try{
            return ResponseEntity.ok(reviewService.fetchAllReviews(pageSize, pageNumber,field));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
    }

    @GetMapping("fetchReview/{id}")
    public ResponseEntity<List<Review>> fetchReview(@PathVariable Long id){
        try{
            List<Review> reviews = reviewService.fetchReviewByUserId(id);
            return ResponseEntity.ok(reviews);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }




}
