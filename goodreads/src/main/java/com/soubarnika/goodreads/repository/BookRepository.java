package com.soubarnika.goodreads.repository;

import com.soubarnika.goodreads.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long> {
    public Books findByBookName(String bookName);

    void deleteByBookName(String bookName);

    @Query(value = "SELECT * FROM Books b", nativeQuery = true)
    List<Books> fetchAllBookDetails();
}
