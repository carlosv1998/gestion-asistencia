package com.mycompany.gestionasistencia.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {
    private final String emailFrom = "ccva1998@gmail.com";
    private final String passwordFrom = "chjdurhpgmkovuxd";
    private final String emailTo;
    private final String subject;
    private final String content;
    
    private final Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;
    
    
    public EnviarCorreo(String emailTo, String subject, String content){
        this.emailTo = emailTo;
        this.subject = subject;
        this.content = content;
        
        mProperties = new Properties();
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");
    }
    
    public void enviarEmail(){
        
        mSession = Session.getDefaultInstance(mProperties);
        mCorreo = new MimeMessage(mSession);
        try {
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");
            
            try (Transport mTransport = mSession.getTransport("smtp")) {
                mTransport.connect(emailFrom, passwordFrom);
                mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            }
            
        } catch (AddressException ex) {
            Logger.getLogger(EnviarCorreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnviarCorreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
