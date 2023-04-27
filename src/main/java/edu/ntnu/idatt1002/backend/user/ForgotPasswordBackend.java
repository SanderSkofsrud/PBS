package edu.ntnu.idatt1002.backend.user;

import edu.ntnu.idatt1002.frontend.ForgotPassword;
import edu.ntnu.idatt1002.frontend.controllers.ForgotPasswordController;
import java.io.*;


/**
 * A class that handles the backend of the forgot password screen.
 * The class checks if the email is valid and if the new password is valid.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class ForgotPasswordBackend {
  /**
   * The boolean that checks if the password has been changed.
   */
  public static boolean changedPassword = false;

  /**
   * A method that handles the submit button.
   * The method checks if the email matches the email in the CSV file.
   * If the email matches, the method changes the password and the salt in the CSV file.
   *
   * @param controller the controller
   * @throws Exception the exception
   */
  public static void handleSubmit(ForgotPasswordController controller) throws Exception {
    if (ForgotPassword.emailString.isEmpty()) {
      ForgotPassword.errorLabel.setText("Please enter your email.");
    } else {
      String csvFile = "src/main/resources/users.csv";
      String tempCsvFile = "src/main/resources/temp_users.csv";
      String line = "";
      String csvSplitBy = ",";
      String salt = CreateUserBackend.generateSalt();
      String newPassword = CreateUserBackend.encrypt(ForgotPassword.newPasswordString, salt);

      try (BufferedReader br = new BufferedReader(
              new FileReader(csvFile)); FileWriter fw = new FileWriter(tempCsvFile)) {
        while ((line = br.readLine()) != null) {
          String[] user = line.split(csvSplitBy);
          if (user[3].equals(ForgotPassword.emailString)) {
            user[1] = newPassword;
            user[2] = salt;
          }
          fw.write(String.join(",", user) + "\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      File originalFile = new File(csvFile);
      File tempFile = new File(tempCsvFile);
      if (originalFile.delete()) {
        if (!tempFile.renameTo(originalFile)) {
          throw new IOException("Failed to rename temp file");
        }
      } else {
        throw new IOException("Failed to delete original file");
      }
      System.out.println("Password reset");
      changedPassword = true;
      controller.handleResetButton();
    }
  }
}