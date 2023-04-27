package edu.ntnu.idatt1002.backend;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import static javax.mail.Transport.send;

/**
 * A class that sends an email to the user.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class Email {

  /**
   * The email address of the sender.
   */
  private static final String SENDER_EMAIL = "idatt1002.ntnu.pbs@gmail.com";
  /**
   * The password of the sender email.
   */
  private static final String SENDER_PASSWORD = "ewqtptwvusfuzrlm";
  /**
   * The Smtp host.
   */
  private static final String SMTPHOST = "smtp.gmail.com";
  /**
   * The Smtp port.
   */
  private static final String SMTPPORT = "587";

  /**
   * The email address of the recipient.
   */
  String recipientEmail;
  /**
   * The password of the recipient.
   */
  String passwordString;
  /**
   * The subject of the email.
   */
  String subject = "Forgotten your password?";

  /**
   * Sends an email to the user with their password.
   *
   * @param email    the email address
   * @param password the password of the user
   * @throws MessagingException the messaging exception
   */
  public void sendEmail(String email, String password) throws MessagingException {

    recipientEmail = email;
    passwordString = password;

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", SMTPHOST);
    props.put("mail.smtp.port", SMTPPORT);

    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
      }
    });

    MimeMessage emailMessage = new MimeMessage(session);
    emailMessage.setFrom(new InternetAddress(SENDER_EMAIL));
    emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
    emailMessage.setSubject(subject);
    MimeMultipart multipart = new MimeMultipart("related");

    MimeBodyPart htmlPart = new MimeBodyPart();
    htmlPart.setContent("<html> <body style='background-color: #f2f2f2;'> " +
            "<div style='text-align: center;'>" +
            "<h1>Reset your password!</h1> " +
            "<p> Your master-password is: " + passwordString + "</p>" +
            "<p> Best regard, Private Budgeting System.</p>" +
            "</div>" +
            "</body> </html>", "text/html");

    multipart.addBodyPart(htmlPart);

    emailMessage.setContent(multipart);

    send(emailMessage, SENDER_EMAIL, SENDER_PASSWORD);
  }
}
