package com.soubarnika.goodreads.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleEmail(String toEmail, String subject, String body);
//    void sendLogFilesInEmail();
}
