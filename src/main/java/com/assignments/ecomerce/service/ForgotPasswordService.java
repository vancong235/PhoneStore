package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.ForgotPasswordToken;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
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
        helper.setFrom("congkhpro291002@gmail.com","Shop Shoes Support");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }

    //Send confirm order mail
    public void sendEmailConfirmOrder(String to, String subject, String userName, List<OrderDetail> orderDetails) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #008080;'>Hello " + userName + ",</h2>"
                + "<p>Thank you for your order. We're excited to have you as our valued customer!</p>"
                + "<table style='border-collapse: collapse; width: 100%; margin-bottom: 20px;'>"
                + "<tr>"
                + "<th style='padding: 10px; border: 1px solid #dddddd;'>Product</th>"
                + "<th style='padding: 10px; border: 1px solid #dddddd;'>Category</th>"
                + "<th style='padding: 10px; border: 1px solid #dddddd;'>Size</th>"
                + "<th style='padding: 10px; border: 1px solid #dddddd;'>Quantity</th>"
                + "<th style='padding: 10px; border: 1px solid #dddddd;'>Price</th>"
                + "</tr>";

        for (OrderDetail orderDetail: orderDetails) {
            emailContent += "<tr>"
                    + "<td style='padding: 10px; border: 1px solid #dddddd;'>" + orderDetail.getProduct().getName() + "</td>"
                    + "<td style='padding: 10px; border: 1px solid #dddddd;'>" + orderDetail.getProduct().getCategory().getName() + "</td>"
                    + "<td style='padding: 10px; border: 1px solid #dddddd;'>" + orderDetail.getProduct().getSize() + "</td>"
                    + "<td style='padding: 10px; border: 1px solid #dddddd;'>" + orderDetail.getQuantity() + "</td>"
                    + "<td style='padding: 10px; border: 1px solid #dddddd;'>" + orderDetail.getUnitPrice()+ "</td>"
                    + "</tr>";
        }

        emailContent += "</table>"
                + "<p>If you have any questions or need further assistance, please don't hesitate to contact us. We're here to help!</p>"
                + "<p>Thank you for choosing us!</p>"
                + "</body></html>";
        helper.setText(emailContent, true);
        helper.setFrom("congkhpro291002@gmail.com","Shop Shoes Support");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);

    }


    public void sendEmailCancelOrder(String to, String subject,String userName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #008080;'>Hello " + userName + ",</h2>"
                + "<p>We regret to inform you that your order has been canceled.</p>"
                + "<p>If you have any questions or need further assistance, please feel free to contact us. We apologize for any inconvenience caused.</p>"
                + "<p>Thank you.</p>"
                + "</body></html>";
        helper.setText(emailContent, true);
        helper.setFrom("congkhpro291002@gmail.com","Shop Shoes Support");
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
