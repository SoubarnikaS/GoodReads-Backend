package com.soubarnika.goodreads.service.impl;

import com.soubarnika.goodreads.dto.UserDTO;
import com.soubarnika.goodreads.entity.Users;
import com.soubarnika.goodreads.exception.GoodReadsException;
import com.soubarnika.goodreads.repository.UsersRepository;
import com.soubarnika.goodreads.service.EmailService;
import com.soubarnika.goodreads.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    Users user;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    UsersRepository usersRepository;

    @Override
    public boolean register(Users user) throws GoodReadsException {
        try{
            usersRepository.save(user);
            sendTaskAssignmentEmail(user.getEmail(), user.getName());
        }
        catch(DataIntegrityViolationException e){
            throw new GoodReadsException("UniqueError", e);
        }
        return true;
    }

    @Override
    public void sendTaskAssignmentEmail(String toEmail, String taskDescription) {
        String subject = "Welcome Back" + taskDescription;
        String body = "Dear Student,\n\nA new task has been assigned to you:\n\n" + taskDescription + "\n\nPlease review and complete the task.\n\nRegards,\nDevVortex";
        emailService.sendSimpleEmail(toEmail, subject, body);
    }

    @Override
    public boolean login(UserDTO userDTO) {
        user = usersRepository.findByEmail(userDTO.getEmail());
        if (user == null) {
            return false;
        }
        else {
            if(!user.getPassword().equals(userDTO.getPassword())){
                return false;
            }
        }
        sendTaskAssignmentEmail(userDTO.getEmail(), user.getName());
        return true;
    }

    @Override
    public Boolean updatePassword(String email, String password) {
        user = usersRepository.findByEmail(email);
        if(email == null)
            return false;
        else{
            user.setPassword(password);
            usersRepository.save(user);
            return true;
        }
    }
}
