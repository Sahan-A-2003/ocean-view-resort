package com.oceanviewresort;

import com.oceanviewresort.service.EmailService;

public class EmailTest {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        emailService.sendEmail(
                "sahan.asantha2003@gmail.com",
                "Test Email from Ocean View Resort",
                "Hello! This is a test email from Ocean View Resort."
        );
    }
}