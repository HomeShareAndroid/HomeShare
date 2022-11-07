package com.example.homeshare;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail extends AsyncTask
{

    //SETUP MAIL SERVER PROPERTIES
    //DRAFT AN EMAIL
    //SEND EMAIL

    String sendEmail;

    String ourName;
    String matchedName;
    String matchedPhone;
    String matchedEmail;
    Session newSession = null;
    MimeMessage mimeMessage = null;

    public Mail(String email,String matchedName, String matchedEmail,String name,String matchedPhone) {
        this.sendEmail=email;
        this.matchedEmail=matchedEmail;
        this.matchedName=matchedName;
        this.ourName=name;
        this.matchedPhone=matchedPhone;
    }

    public void someoneAcceptedYourInvitation() throws MessagingException, IOException {
        System.out.println("CHECKPOINT 2: INSIDE MAIL METHOD");
        setupServerProperties();
//        try {
            draftInvitationEmail();
//        } catch (AddressException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
            sendEmail();
//        } catch (MessagingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        System.out.println("Request Email Sent.");
    }

    public void youMatchedWithARoommate() throws MessagingException, IOException {
        setupServerProperties();
//        try {
            draftRoommateEmail();
//        } catch (AddressException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        try {
            sendEmail();
//        } catch (MessagingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        System.out.println("Accept Email Sent.");

    }

    private void sendEmail() throws MessagingException {
        System.out.println("CHECKPOINT 5");
        Transport.send(mimeMessage);
        System.out.println("CHECKPOINT 6");
        System.out.println("Email successfully sent!!!");
    }

    private MimeMessage draftInvitationEmail() throws AddressException, MessagingException, IOException {
        System.out.println("CHECKPOINT 3: SEND EMAIL TO " + sendEmail);
        String[] emailReceipients = {sendEmail};  //Enter list of email recepients
        String emailSubject = "HomeShare - Someone has Accepted Your Invitation!";
        String emailBody = "Hello " + ourName +",\n\n" + "Your Invitation has been accepted by"+ matchedName +"\n"+
               "Accept or decline this request on the 'Response Feed' page of the HomeShare App. Good luck finding your future roommate! - HomeShare";
        mimeMessage = new MimeMessage(newSession);

        for (int i =0 ;i<emailReceipients.length;i++)
        {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }
        mimeMessage.setSubject(emailSubject);
        System.out.println("CHECKPOINT 4");

        // CREATE MIMEMESSAGE
        // CREATE MESSAGE BODY PARTS
        // CREATE MESSAGE MULTIPART
        // ADD MESSAGE BODY PARTS ----> MULTIPART
        // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object


        MimeBodyPart bodyPart = new MimeBodyPart();
        // bodyPart.setContent(emailBody,"text/html; charset=utf-8\"");
        bodyPart.setText(emailBody);
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        mimeMessage.setFrom(new InternetAddress("sharehome@myyahoo.com"));

        return mimeMessage;
    }

    private MimeMessage draftRoommateEmail() throws AddressException, MessagingException, IOException {
        String[] emailReceipients = {sendEmail};  //Enter list of email recepients
        String emailSubject = "HomeShare - You Have a New Roommate Pairing!";
        String emailBody = "Hello " + ourName +",\n\n" + "You have a new roommate pairing! Roommate Info \nName: "+ matchedName + "\n"
                + "Phone Number: " + matchedPhone + "\n" + "Email: "+ matchedEmail +"\n\n"+ "To see all of the Roommate information, go to the 'Roommate Feed' page on the HomeShare App. - HomeShare";
        mimeMessage = new MimeMessage(newSession);

        for (int i =0 ;i<emailReceipients.length;i++)
        {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients[i]));
        }
        mimeMessage.setSubject(emailSubject);

        // CREATE MIMEMESSAGE
        // CREATE MESSAGE BODY PARTS
        // CREATE MESSAGE MULTIPART
        // ADD MESSAGE BODY PARTS ----> MULTIPART
        // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object


        MimeBodyPart bodyPart = new MimeBodyPart();
        // bodyPart.setContent(emailBody,"text/html; charset=utf-8\"");
        bodyPart.setText(emailBody);
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        mimeMessage.setFrom(new InternetAddress("sharehome@myyahoo.com"));
        return mimeMessage;
    }

    private void setupServerProperties() {
        final String username = "sharehome@myyahoo.com";
        final String password = "Degree08_";


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.socketFactory.port", "25");
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.socketFactory.fallback", "true");

        newSession = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            someoneAcceptedYourInvitation();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
