package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.Email;
import edu.ntnu.idatt1002.frontend.controllers.ForgotPasswordController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.mail.MessagingException;
import java.util.Random;
import java.util.regex.Pattern;

import static edu.ntnu.idatt1002.backend.user.ForgotPasswordBackend.handleSubmit;

/**
 * A class that creates the view for the forgot password page.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class ForgotPassword {
  /**
   * The pattern the password should be.
   */
  private static final String PASSWORD_PATTERN =
          "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
  /**
   * The pattern for the password.
   */
  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
  /**
   * The constant errorLabel.
   */
  public static Label errorLabel;
  /**
   * The email.
   */
  public static String emailString;
  /**
   * The new password.
   */
  public static String newPasswordString;
  /**
   * An instance of the Random library.
   */
  private static final Random random = new Random();
  /**
   * The constant CSS_FILE that contains the styling for the page.
   */
  private static final String CSS_FILE = "/Styling.css";
  /**
   * The constant TEXTFIELD that is used for the id of text fields.
   */
  private static final String TEXTFIELD = "textField";

  /**
   * Gets random int.
   *
   * @param length the length
   * @return the random int
   */
  public static int getRandomInt(int length) {
    return random.nextInt(length);
  }

  /**
   * A method that creates the forgot password view.
   *
   * @param controller the controller
   * @return the view as a parent
   */
  public Parent forgottenPasswordView(ForgotPasswordController controller) {
    TextField emailTextField;

    Pane background = new Pane();
    background.setPrefSize(1000, 700);

    background.getStylesheets().add(CSS_FILE);

    background.getStyleClass().add("loginScreen" + getRandomInt(1) + 1);

    VBox forgottenPasswordVBox = new VBox();
    forgottenPasswordVBox.setId("overlayLogin");
    forgottenPasswordVBox.setSpacing(20);
    forgottenPasswordVBox.setMaxSize(300, 600);

    forgottenPasswordVBox.getStylesheets().add(CSS_FILE);

    forgottenPasswordVBox.setPadding(new Insets(10));

    emailTextField = new TextField();
    emailTextField.setPromptText("Enter email");
    emailTextField.setId(TEXTFIELD);
    emailTextField.setMaxWidth(250);

    TextField masterPassword = new TextField();
    masterPassword.setPromptText("Enter master password");
    masterPassword.setId(TEXTFIELD);
    masterPassword.setMaxWidth(250);

    PasswordField newPassword = new PasswordField();
    newPassword.setPromptText("Enter new password");
    newPassword.setId(TEXTFIELD);
    newPassword.setMaxWidth(250);

    PasswordField confirmNewPassword = new PasswordField();
    confirmNewPassword.setPromptText("Confirm new password");
    confirmNewPassword.setId(TEXTFIELD);
    confirmNewPassword.setMaxWidth(250);

    Button changePasswordButton = new Button("Update Password");
    changePasswordButton.setId("actionButton");
    changePasswordButton.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        changePasswordButton.fire(); // Simulate a click event on the logIn button
      }
    });

    VBox backButtonBox = new VBox();
    Button backButton = new Button();
    backButton.setId("backButton");

    ImageView backImage = new ImageView(new Image("icons/back.png"));
    backImage.setFitHeight(50);
    backImage.setFitWidth(50);
    backButton.setGraphic(backImage);
    backButtonBox.getChildren().add(backButton);
    backButtonBox.setAlignment(Pos.TOP_LEFT);
    backButtonBox.setPadding(new Insets(20, 0, 0, 20));

    backButton.setOnAction(e -> controller.handleBackButton());

    masterPassword.setVisible(false);
    newPassword.setVisible(false);
    confirmNewPassword.setVisible(false);
    changePasswordButton.setVisible(false);

    Button submitButton = new Button("Submit");
    emailTextField.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        submitButton.fire(); // Simulate a click event on the logIn button
      }
    });
    GridPane.setConstraints(submitButton, 1, 2);
    submitButton.setOnAction(e -> {

      Email email = new Email();
      emailString = emailTextField.getText();

      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
      StringBuilder sb = new StringBuilder();
      int length = 10;
      for (int i = 0; i < length; i++) {
        int index = getRandomInt(alphabet.length());
        char randomChar = alphabet.charAt(index);
        sb.append(randomChar);
      }
      String masterPasswordString = sb.toString();
      try {
        email.sendEmail(emailString, masterPasswordString);
      } catch (MessagingException ex) {
        throw new IllegalArgumentException("Email could not be sent.");
      }
      masterPassword.setVisible(true);
      newPassword.setVisible(true);
      confirmNewPassword.setVisible(true);
      changePasswordButton.setVisible(true);

      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Password Reset");
      alert.setHeaderText("Instructions to reset your password have been sent to your email.");
      alert.showAndWait();
    });

    submitButton.setId("actionButton");

    changePasswordButton.setOnAction(e -> {

      if (newPassword.getText().equals(confirmNewPassword.getText())) {
        if (!pattern.matcher(newPassword.getText()).matches()) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error");
          alert.setHeaderText("Password must contain at least one uppercase letter,"
                  + " one lowercase letter, one number,"
                  + " one special character "
                  + "and be between 8 and 20 characters long.");
          alert.showAndWait();
        } else {
          newPasswordString = newPassword.getText();
          try {
            handleSubmit(controller);
          } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
          }
        }
      }
    });

    forgottenPasswordVBox.getChildren().addAll(backButtonBox,
            emailTextField, submitButton, masterPassword,
            newPassword, confirmNewPassword, changePasswordButton);
    forgottenPasswordVBox.setSpacing(30);
    forgottenPasswordVBox.setAlignment(Pos.CENTER);
    forgottenPasswordVBox.getStylesheets().add(CSS_FILE);

    StackPane stackPane = new StackPane(background, forgottenPasswordVBox);

    VBox vBox = new VBox(stackPane);
    vBox.setAlignment(Pos.TOP_CENTER);
    vBox.getStylesheets().add(CSS_FILE);

    return vBox;
  }
}