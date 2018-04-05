package com.workspaceit.pos.helper;

import com.workspaceit.pos.config.Environment;
import com.workspaceit.pos.entity.ResetPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailHelper {

    private Environment environment;

    @Autowired
    public void setAppProperties(Environment appProperties) {
        this.environment = appProperties;
    }

    private static String from= "developer_beta@workspaceit.com";
    private  static String username = "developer_beta@workspaceit.com";
    private  static String password = "wsit9748!@";
    private static String link = "http://localhost:8080/";
    private static String appBaseUrl = "http://localhost:4200/";

    private Properties getProperties(){
        Properties properties = System.getProperties();
        // properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", environment.getMailHost());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user",environment.getMailUsername());
        properties.put("mail.smtp.password",environment.getMailPassword());
        properties.put("mail.smtp.port",environment.getMailPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        from = environment.getMailSenderEmail();
        username = environment.getMailSenderEmail();
        password = environment.getMailSenderPassword();
        link = environment.getMailServerLink();
        return properties;
    }



    public boolean sendPasswordResetMail(ResetPasswordToken resetPasswordToken) {
        String to = resetPasswordToken.getAuthCredential().getEmail();
        String token = resetPasswordToken.getToken();
        Properties properties = getProperties();
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        username, password);// Specify the Username and the PassWord
            }
        });
        String activationUrl = link + "reset-password-verify/" +token;
        String link = "<a href='" + activationUrl + "'>Click here</a>";
        String emailHtmlBody = "Hi,<br>Please click this link " + link + " to reset your password";
        try {
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Password Reset");
            message.setText(emailHtmlBody, null, "html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }



}