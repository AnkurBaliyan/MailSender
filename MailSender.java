package com.sparktg.mail;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    private static final String host="smtp-relay.gmail.com";
    private static final String port="587";
    private static final String auth="false";
    private static final String maxTimeOut="30000";
    private static final String sendFrom="no-reply@sparktg.com";
    private static Properties props = System.getProperties();
    static {
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.timeout", maxTimeOut);
        props.put("mail.smtp.connectiontimeout", maxTimeOut);
    }
    public static  void sendEmail( String toEmail, String subject,String message) {
        Session mailSession = Session.getDefaultInstance(props);
        try {
            Message mailMessage = new MimeMessage(mailSession);
            String ccList[]=new String[]{"sales@sparktg.com","sudhir.sparktg@gmail.com","ankur@sparktg.com"};
            for(String cc: ccList){
            	mailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
            mailMessage.setFrom(new InternetAddress(sendFrom));
            mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mailMessage.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            mailMessage.setContent(multipart);
            Transport.send(mailMessage);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
    }
    public static void main (String args[]){
        String sendTo[]=new String[]{"ankur@sparktg.com"};
        String subject = "SparkTG Outstanding Payment";   //"Urgent KYC Documentation Update";
        String message ="This is an auto-generated email to inform you that the payment for your invoices till January has not been cleared yet.\n\nWe have not received your payment yet. Please pay the outstanding amount latest by 12-03-2018 otherwise your service will be terminated W.E.F 15-03-2018.\n\nYou can contact Mr. Sudhir (9250404164) for any billing related queries.\n\nIf you have already made the payment, please ignore this email.";
        for(String sendto: sendTo){
            sendEmail(sendto,subject,message);
            System.out.println("mail sent to " + sendto);
        }
    }
}
