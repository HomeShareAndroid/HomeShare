package com.example.homeshare;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.homeshare.Model.User;
import com.example.homeshare.Util.Mail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HomeShareUnitTests {
    User testUser=null;
    @Before
    public void constructUser(){
        testUser = new User("Toan","toannhuynh206@gmail.com","u817dhau","Computer Science","206-954-8882","Chill Dude","17aduj");
    }
    @Test
    public void draftRoomMateEmailValid() {
        Mail m = new Mail("toannhuynh206@gmail.com","Toan","homesharehub@zohomail.com","Jeff","a");
        try {
            m.draftRoommateEmail();
            MimeMessage sendMail = m.mimeMessage;
            Address[] to= sendMail.getAllRecipients();
            Address[] from = sendMail.getFrom();
            assertEquals("toannhuynh206@gmail.com",to[0].toString());
            assertEquals("homesharehub@zohomail.com",from[0].toString());
            assertEquals("HomeShare - You Have a New Roommate Pairing!",sendMail.getSubject());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void draftRoomMateEmailInvalid() {
        Mail m = null;
        try{
             m = new Mail("dwa.com", "Toan", "homesharehub@zohomail.com", "Jeff", "a");
            try {
                m.draftRoommateEmail();
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
         }catch (Exception e){
            assertEquals("Invalid Email",e.getMessage());
        }
    }
    @Test
    public void draftInvitationEmailValid() {
        Mail m = new Mail("toannhuynh206@gmail.com","Toan","homesharehub@zohomail.com","Jeff","a");
        try {
            m.draftInvitationEmail();
            MimeMessage sendMail = m.mimeMessage;
            Address[] to= sendMail.getAllRecipients();
            Address[] from = sendMail.getFrom();
            assertEquals("toannhuynh206@gmail.com",to[0].toString());
            assertEquals("homesharehub@zohomail.com",from[0].toString());
            assertEquals("HomeShare - Someone has Accepted Your Invitation!",sendMail.getSubject());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void draftInvitationEmailInvalid() {
        Mail m = null;
        try{
            m = new Mail("dwa.com", "Toan", "homesharehub@zohomail.com", "Jeff", "a");
            try {
                m.draftInvitationEmail();
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            assertEquals("Invalid Email",e.getMessage());
        }
    }
    @Test
    public void draftRejectionEmailInvalid() {
        Mail m = null;
        try{
            m = new Mail("dwa.com", "Toan", "homesharehub@zohomail.com", "Jeff", "a");
            try {
                m.draftRejectionEmail();
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            assertEquals("Invalid Email",e.getMessage());
        }
    }
    @Test
    public void draftRejectionEmailValid() {
        Mail m = new Mail("toannhuynh206@gmail.com","Toan","homesharehub@zohomail.com","Jeff","a");
        try {
            m.draftRejectionEmail();
            MimeMessage sendMail = m.mimeMessage;
            Address[] to= sendMail.getAllRecipients();
            Address[] from = sendMail.getFrom();
            assertEquals("toannhuynh206@gmail.com",to[0].toString());
            assertEquals("homesharehub@zohomail.com",from[0].toString());
            assertEquals("HomeShare - Your invitation request was denied",sendMail.getSubject());

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void validServerProperties() {
        Mail m = new Mail("toannhuynh206@gmail.com","Toan","homesharehub@zohomail.com","Jeff","a");
        m.setupServerProperties();
        assertEquals("true",   m.newSession.getProperties().getProperty("mail.smtp.auth"));
        assertEquals("true",   m.newSession.getProperties().getProperty("mail.smtp.starttls.enable"));
        assertEquals("587",   m.newSession.getProperties().getProperty("mail.smtp.port"));
        assertEquals("smtp.zoho.com",   m.newSession.getProperties().getProperty("mail.smtp.host"));
    }
    @Test
    public void invalidServerProperties() {
        Mail m = new Mail("toannhuynh206@gmail.com","Toan","homesharehub@zohomail.com","Jeff","a");
        m.setupServerProperties();
        assertEquals(null,   m.newSession.getProperties().getProperty("smtp-gmail"));
    }
    @Test
    public void validateUserName() {
      assertEquals("Toan",testUser.getName());
    }
    @Test
    public void validateEmail() {
        assertEquals("toannhuynh206@gmail.com",testUser.getEmail());
    }
    @Test
    public void validateMajor() {
        assertEquals("Computer Science",testUser.getMajor());
    }
    @Test
    public void validateNumber() {
        assertEquals("206-954-8882",testUser.getPhone());
    }
    @Test
    public void validateBio() {
        assertEquals("Chill Dude",testUser.getAboutMe());
    }
    @Test
    public void validateUID() {
        assertEquals("u817dhau",testUser.getUid());
    }
    @Test
    public void validatePhotoURI() {
        assertEquals("17aduj",testUser.getPhotoUri());
    }


}