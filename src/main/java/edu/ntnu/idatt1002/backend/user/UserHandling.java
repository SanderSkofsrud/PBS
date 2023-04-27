package edu.ntnu.idatt1002.backend.user;

import edu.ntnu.idatt1002.frontend.GUI;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that handles the user.
 * Tha class is used for getting and changing the email and password of the user.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class UserHandling {

  /**
   * A pattern to check if the email is valid.
   */
  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
          Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  /*
   * A pattern to check if the password is valid.
   */
  private static final Pattern PASSWORD_PATTERN = Pattern.compile(
          "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
  /*
   * The email of the current user.
   */
  private static String email;
  /*
   * The password of the current user.
   */
  private static String password;

  /**
   * Returns the email of the current user.
   *
   * @return the email of the current user
   * @throws IOException the io exception
   */
  public static String getEmail() throws IOException {
    String line = "";
    try (BufferedReader reader = new BufferedReader(
            new FileReader("src/main/resources/users.csv"))) {

      while ((line = reader.readLine()) != null) {
        String[] user = line.split(",");
        if (user[0].equals(GUI.getCurrentUser())) {
          email = user[3];
        }
      }
    } catch (IOException e) {
      throw new IOException("Could not read file");
    }
    return email;
  }

  /**
   * Returns the password of the current user.
   *
   * @return the password of the current user
   */
  public static String getPassword() {
    String line = "";
    try (BufferedReader reader = new BufferedReader(
            new FileReader("src/main/resources/users.csv"))) {
      GUI.getCurrentUser();
      while ((line = reader.readLine()) != null) {
        String[] user = line.split(",");
        if (user[0].equals(GUI.getCurrentUser())) {
          String salt = user[2];
          password = user[1];
          password = LoginBackend.decrypt(password, salt);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return password;
  }

  /**
   * Change the email of the current user.
   * The method checks if the email is valid.
   *
   * @param email the new email
   * @throws Exception the exception
   */
  public static void changeEmail(String email) throws Exception {
    String csvFile = "src/main/resources/users.csv";
    String tempCsvFile = "src/main/resources/temp_users.csv";
    String line = "";
    String csvSplitBy = ",";
    Matcher emailMatcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

    if (!emailMatcher.matches()) {
      throw new Exception("Invalid email");
    }
    try (BufferedReader br = new BufferedReader(
            new FileReader(csvFile)); FileWriter fw = new FileWriter(tempCsvFile)) {
      while ((line = br.readLine()) != null) {
        String[] user = line.split(csvSplitBy);
        if (user[3].equals(getEmail())) {
          user[3] = email;
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
  }


  /**
   * Change the password of the current user.
   * The method checks if the password is valid.
   *
   * @param password the new password
   * @throws Exception the exception
   */
  public static void changePassword(String password) throws Exception {
    String csvFile = "src/main/resources/users.csv";
    String tempCsvFile = "src/main/resources/temp_users.csv";
    String line = "";
    String csvSplitBy = ",";
    String salt = CreateUserBackend.generateSalt();
    String newPassword = CreateUserBackend.encrypt(password, salt);
    Matcher passwordMatcher = PASSWORD_PATTERN.matcher(password);

    if (!passwordMatcher.matches()) {
      throw new Exception("Invalid password");
    }
    try (BufferedReader br = new BufferedReader(
            new FileReader(csvFile)); FileWriter fw = new FileWriter(tempCsvFile)) {
      while ((line = br.readLine()) != null) {
        String[] user = line.split(csvSplitBy);
        if (user[3].equals(getEmail())) {
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
  }
}


