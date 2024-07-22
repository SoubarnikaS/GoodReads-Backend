package com.soubarnika.goodreads.service.impl;

import com.soubarnika.goodreads.entity.Books;
import com.soubarnika.goodreads.repository.BookRepository;
import com.soubarnika.goodreads.service.BookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    Books book;

    @Override
    public boolean addBookDetails(Books book) {
        bookRepository.save(book);
        return true;
    }

    @Override
    public List<Books> fetchBookDetails(int pageNumber, int pageSize, String field) {

//        return bookRepository.findAll();
        return bookRepository.findAll(PageRequest.of(pageNumber, pageSize).withSort(Sort.by(Sort.Direction.ASC,field))).getContent();

    }

    @Override
    public Boolean deleteBook(String bookName) {
         book = bookRepository.findByBookName(bookName);
        if(book == null)
            return false;
        else{
            bookRepository.deleteByBookName(bookName);
            return true;
        }
    }

    @Override
    public Boolean editBookDetails(String bookName, Books book) {
        Books exsitingbook = bookRepository.findByBookName(bookName);
        if(exsitingbook != null){
            exsitingbook.setBookDescription(book.getBookDescription());
            exsitingbook.setBookName(book.getBookName());
            exsitingbook.setBookImage(book.getBookImage());
            exsitingbook.setBookRating(book.getBookRating());
            exsitingbook.setBookGenre(book.getBookGenre());
            bookRepository.save(exsitingbook);
            return true;
        }
        return false;
    }

    @Override
    public Books fetchBookDetailsByName(String bookName) {
        return bookRepository.findByBookName(bookName);
    }

    public List<Books> fetchAllBookDetails() {
        return bookRepository.fetchAllBookDetails();
    }


}
