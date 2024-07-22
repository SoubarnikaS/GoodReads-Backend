package com.soubarnika.goodreads.service;

import com.soubarnika.goodreads.dto.UserDTO;
import com.soubarnika.goodreads.entity.Books;
import com.soubarnika.goodreads.entity.Users;
import com.soubarnika.goodreads.exception.GoodReadsException;

public interface UserService {
    public boolean register(Users user) throws GoodReadsException;
    public boolean login(UserDTO userDTO);

    public void sendTaskAssignmentEmail(String toEmail, String taskDescription);
    Boolean updatePassword(String email, String password);
}