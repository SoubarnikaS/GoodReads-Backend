package com.soubarnika.goodreads.service;

import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    String generateHTMLReport();
}
