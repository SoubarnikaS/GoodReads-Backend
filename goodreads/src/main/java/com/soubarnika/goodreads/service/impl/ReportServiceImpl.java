package com.soubarnika.goodreads.service.impl;

import com.soubarnika.goodreads.service.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public String generateHTMLReport() {

        return "<html><body><h1>Sample Report</h1><p>Report content goes here...</p></body></html>";
    }

}
