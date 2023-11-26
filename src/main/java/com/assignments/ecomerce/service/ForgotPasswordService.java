package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.ForgotPasswordToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    JavaMailSender javaMailSender;
    private final int MINUTES = 10;
    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    public LocalDateTime expireTimeRange(){
        return LocalDateTime.now().plusMinutes(MINUTES);
    }

    public void sendEmail(String to, String subject, String emailLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello,</p>"
                + "<p>Click the link below to reset your password:</p>"
                + "<p><a href=\"" + emailLink + "\">Change My Password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you did not request a password reset.</p>";
        helper.setText(emailContent, true);
        helper.setFrom("congkhpro291002@gmail.com","Shop Tree Support");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }

    public boolean isExpired(ForgotPasswordToken forgotPasswordToken){
        return LocalDateTime.now().isAfter(forgotPasswordToken.getExpireTime());
    }

    public String checkValidity(ForgotPasswordToken forgotPasswordToken, Model model){
        System.out.println(forgotPasswordToken);
        if(forgotPasswordToken == null){
            model.addAttribute("error","Invalid Token !");
            return "Error-page";
        } else if (forgotPasswordToken.getIsuUsed()) {
            model.addAttribute("error","The token is already used !");
            return "Error-page";
        }
        else {
            return "reset-password";
        }

    }
}
