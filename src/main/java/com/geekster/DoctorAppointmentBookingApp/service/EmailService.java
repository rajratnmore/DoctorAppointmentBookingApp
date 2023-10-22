package com.geekster.DoctorAppointmentBookingApp.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static final String EMAIL_USERNAME = "rajmorecsn@gmail.com";
    public static final String EMAIL_PASSWORD = "kxwsnagftlnoazvw";

    public static boolean sendMail(String toEmail, String subject, String body){

        // Properties
        Properties systemPropertiesMap = System.getProperties();

        systemPropertiesMap.put("mail.smtp.host","smtp.gmail.com");
        systemPropertiesMap.put("mail.smtp.port","465");
        systemPropertiesMap.put("mail.smtp.ssl.enable","true");
        systemPropertiesMap.put("mail.smtp.auth","true");

        // Session
        Session mailSession = Session.getInstance(systemPropertiesMap, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        // Build the Mail
        // Mime message
        MimeMessage mailMessage = new MimeMessage(mailSession);

        try {

            mailMessage.setFrom(EMAIL_USERNAME);
            mailMessage.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(toEmail)));
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            Transport.send(mailMessage);
            System.out.println("OTP sent successfully to "+ toEmail);
            return true;

        }catch (Exception ex){

            System.out.println("Some errors while preparing mail body");
            System.out.println(ex.getMessage());
            return false;
        }

    }
}
