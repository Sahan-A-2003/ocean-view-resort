package com.oceanviewresort.service;

import com.oceanviewresort.observer.ReservationObserver;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService implements ReservationObserver {

    private final String senderEmail = "sahan.asantha2003@gmail.com";
    private final String senderPassword = "albd eiie biui ndov";

    @Override
    public void update(String guestEmail, String subject, String message) {
        sendEmail(guestEmail, subject, message);
    }

    public void sendEmail(String toEmail, String subject, String messageText) {

        // SMTP server configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject(subject);
            message.setContent(messageText, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}