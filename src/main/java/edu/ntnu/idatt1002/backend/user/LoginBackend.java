package edu.ntnu.idatt1002.backend.user;

import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * A class that represents the login backend.
 * The class contains methods for decrypting the password and checking if the password is correct.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class LoginBackend {
  /**
   * The constant SECRET_KEY is the secret key used for decrypting the password.
   */
  private static final String SECRET_KEY = "PASSWORD";
  /**
   * The constant currentUser is the current user.
   */
  private static String currentUser;

  /**
   * A method that decrypts the password.
   *
   * @param password the password to decrypt
   * @param salt     the salt used for decrypting the password
   * @return the decrypted password
   */
  public static String decrypt(String password, String salt) {
    try {
      byte[] iv = new byte[16];
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(), 65536, 256);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
      return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e);
    }
    return null;
  }

  /**
   * A method that checks if the password is correct.
   * If the password is correct, the user is logged in.
   *
   * @param username   the username or email of the user
   * @param password   the password of the user
   * @param controller the controller
   * @throws IOException the io exception
   */
  public static void login(String username, String password,
                           LoginController controller) throws IOException {
    Path tempDirectoryPath = Paths.get("src/main/resources/");
    File tempDirectory = tempDirectoryPath.toFile();

    if (!tempDirectory.exists()) {
      boolean created = tempDirectory.mkdirs();
      if (!created) {
        throw new IOException("Failed to create temp directory");
      }
    }

    File file = new File("src/main/resources/users.csv");
    if (!file.exists()) {
      file.createNewFile();
    }

    String line;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      while ((line = br.readLine()) != null) {
        String[] user = line.split(",");
        if (user[0].equals(username)) {
          String encryptedPassword = user[1];
          String salt = user[2];
          String decryptedPassword = decrypt(encryptedPassword, salt);
          if (password.equals(decryptedPassword)) {
            System.out.println("Logged in");
            SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
            currentUser = username;
            controller.handleLoginButton();
          }
        }
      }
    } catch (IOException ex) {
      throw new IOException("Error while reading file");
    }
  }


  /**
   * Gets current user.
   *
   * @return the current user
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Sets current user.
   *
   * @param currentUser the current user
   */
  public static void setCurrentUser(String currentUser) {
    LoginBackend.currentUser = currentUser;
  }
}
