package com.soubarnika.goodreads.service.impl;

import com.soubarnika.goodreads.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private static final Logger userActionLogger = LoggerFactory.getLogger("com.soubarnika.goodreads");



    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("surenprathish16@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Scheduled(cron = "0 0 0 * * *") // Runs every minute
    public void sendScheduledEmailWithAttachment() {
        sendEmailWithAttachment("727822tucs226@skct.edu.in", "Application Logs", "Logs are attached below", "logs/user-actions.html");
    }


    public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom("surenprathish16@gmail.com");

            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment(file.getFilename(), file);

            userActionLogger.info("Mail sent successfully");
            mailSender.send(message);
            log.info("Mail sent successfully to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send email to {}", toEmail, e);
        }
    }



//    private final JavaMailSender mailSender;
//    private final HTMLLogger htmlLogger;
//    private final ReportService reportService;
//
//    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
//
//
//    @Autowired
//    public EmailServiceImpl(JavaMailSender mailSender, ReportService reportService, HTMLLogger htmlLogger) {
//        this.mailSender = mailSender;
//        this.reportService = reportService;
//        this.htmlLogger = htmlLogger;
//    }
//    @Scheduled(cron = "0 * * * * ?") // Runs every minute for testing
//    public void sendDailyReport() {
//        logger.info("Attempting to send daily report email...");
//        log.info("Sending email");
//
//        try {
//            String htmlReport = reportService.generateHTMLReport();
//            sendSimpleEmail("haezy1485@gmail.com", "Daily Report", htmlReport);
//            logger.info("Daily report email sent successfully.");
//            log.info("Sent email");
//        } catch (Exception e) {
//            logger.error("Failed to send daily report email: " + e.getMessage(), e);
//        }
//    }
//
//    @Override
//    public void sendSimpleEmail(String toEmail, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("surenprathish16@gmail.com");
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//    }
//
////    @Scheduled(cron = "0 * * * * ?") // Runs every minute for testing
////    public void sendDailyHTMLReport() {
////        try {
////
////            String htmlReport = reportService.generateHTMLReport();
////
////            String htmlLogs = htmlLogger.getHTMLLogs();
////            htmlLogger.clearLogs(); // Clear logs after fetching them
////
////            // Send email with HTML report and attached logs
////            sendEmailWithAttachments("soubarnika07@gmail.com", "Daily HTML Report with Logs", htmlReport, "logs.html", htmlLogs);
////        } catch (Exception e) {
////            // Handle exceptions appropriately
////        }
////    }
//
////    private void sendEmailWithAttachments(String to, String subject, String body, String attachmentName, String attachmentContent) {
////        try {
////            logger.info("Attempting to send HTML report email...");
////            log.info("Sending HTML");
////            MimeMessage message = mailSender.createMimeMessage();
////            MimeMessageHelper helper = new MimeMessageHelper(message, true);
////
////            message.setFrom("surenprathish16@gmail.com");
////            helper.setTo(to);
////            helper.setSubject(subject);
////            helper.setText(body);
////
////            // Attach HTML logs as a byte array
////            helper.addAttachment(attachmentName, new ByteArrayResource(Base64.getEncoder().encode(attachmentContent.getBytes())));
////            log.info("SENT HTML");
////            // Send the email
////            mailSender.send(message);
////
////        } catch (Exception e) {
////            log.error(e.getMessage());
////        }
////    }
//
//    @Scheduled(cron = "0 * * * * ?") // Runs every minute for testing
//    public void sendLogFilesInEmail() {
//        try {
//            // Read application log file content
//            String path = "logs/application.html";
//            String user_path = "logs/user-actions.log";
//            String applicationLogs = readFileContent("logs/application.html");
//            log.info("content read");
//
//            // Read user actions log file content
//            String userActionsLogs = readFileContent("logs/user-actions.log");
//            log.info("content read" + userActionsLogs);
//
//            // Generate HTML content for email
//            String emailBody = "<html><body>" +
//                    "<h1>Log Files</h1>" +
//                    "<h2>Application Logs</h2>" +
//                    "<pre>" + applicationLogs + "</pre>" +
//                    "<h2>User Actions Logs</h2>" +
//                    "<pre>" + userActionsLogs + "</pre>" +
//                    "<h2>Additional Logs from Application</h2>" +
//                    htmlLogger.getHTMLLogs() +
//                    "</body></html>";
//            log.info("html sent");
//            // Send email with HTML content
//            sendHtmlEmail("haezy1485@gmail.com", "Logs from Application", emailBody,path);
//            sendHtmlEmail("haezy1485@gmail.com", "Logs from Application", emailBody,user_path);
//        } catch (IOException | MessagingException e) {
//            e.printStackTrace(); // Handle exceptions as needed
//        }
//    }
//
//    private String readFileContent(String filePath) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                contentBuilder.append(line).append("\n");
//            }
//        }
//        return contentBuilder.toString();
//    }
//
//    private void sendHtmlEmail(String to, String subject, String htmlBody, String pathToAttachment) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setFrom("surenprathish16@gmail.com");
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true);
//
//        // Attach the HTML report file
//        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//        helper.addAttachment("Report.html", file);
//
//        mailSender.send(message);
//    }
//
//    public void sendEmailWithAttachment(String to, String subject, String text, String pathToAttachment) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom("surenprathish16@gmail.com");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//
//            // Attach HTML file
//            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//            helper.addAttachment("YourHTMLFile.html", file);
//
//            mailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace(); // Handle exception appropriately
//        }
//    }
}
