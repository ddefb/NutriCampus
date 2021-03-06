package com.nutricampus.app.utils.services;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMail {

    private static final String TAG = "GMail";

    private static final String EMAIL_PORT = "587";// gmail's smtp port
    private static final String SMTP_AUTH = "true";
    private static final String STARTTLS = "true";
    private static final String EMAIL_HOST = "smtp.gmail.com";

    private String fromEmail;
    private String fromPassword;
    private List toEmailList;
    private String emailSubject;
    private String emailBody;

    private Properties emailProperties;
    private Session mailSession;
    private MimeMessage emailMessage;

    public GMail(String fromEmail, String fromPassword,
                 List toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", EMAIL_PORT);
        emailProperties.put("mail.smtp.auth", SMTP_AUTH);
        emailProperties.put("mail.smtp.STARTTLS.enable", STARTTLS);
        Log.i(TAG, "Mail server properties set.");
    }

    public MimeMessage createEmailMessage() throws
            MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        for (Object toEmail : toEmailList) {
            Log.i(TAG, "toEmail: " + toEmail);
            emailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress((String) toEmail));
        }

        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");// for a html email

        Log.i(TAG, "Mensagem de e-mail criada.");
        return emailMessage;
    }

    public void sendEmail() throws MessagingException {

        Transport transport = mailSession.getTransport("smtp");
        transport.connect(EMAIL_HOST, fromEmail, fromPassword);
        Log.i(TAG, "allrecipients: " + Arrays.toString(emailMessage.getAllRecipients()));
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        Log.i(TAG, "E-mail enviado com sucesso.");
    }

}