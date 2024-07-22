package com.soubarnika.goodreads.service.impl;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HTMLLogger {

    private StringBuilder logBuilder = new StringBuilder();

    public void logInfo(String message) {
        appendLog("<p style='color:blue'><strong>INFO:</strong> " + message + "</p>");
    }

    public void logError(String message, Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        appendLog("<p style='color:red'><strong>ERROR:</strong> " + message + "</p>");
        appendLog("<pre>" + sw.toString() + "</pre>");
    }

    private void appendLog(String logMessage) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        logBuilder.append("<p>").append(timestamp).append(" ").append(logMessage).append("</p>");
    }

    public String getHTMLLogs() {
        return logBuilder.toString();
    }

    public void clearLogs() {
        logBuilder.setLength(0);
    }
}
