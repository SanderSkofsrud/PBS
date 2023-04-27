package edu.ntnu.idatt1002.frontend.utility;

import java.awt.*;
import java.net.URI;

/**
 * A class that sends an email to the PBS.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.0 - 19.04.2023
 */
public class ContactUs {
  /**
   * The email address of the PBS.
   */
  private static final String RECIPIENT_EMAIL = "idatt1002.ntnu.pbs@gmail.com";

  /**
   * Sends an email to the PBS.
   */
  public static void sendEmail() {
    try {
      Desktop.getDesktop().mail(new URI("mailto:" + RECIPIENT_EMAIL));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}




