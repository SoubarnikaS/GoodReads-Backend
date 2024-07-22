package com.soubarnika.goodreads.service;

import com.soubarnika.goodreads.entity.Books;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService{

    public boolean addBookDetails(Books book);
    public List<Books> fetchBookDetails(int pageNumber, int pageSize, String field);

    Boolean deleteBook(String bookName);

    Boolean editBookDetails(String bookName, Books book);

    Books fetchBookDetailsByName(String bookName);

    List<Books> fetchAllBookDetails();
}