package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.user.LoginBackend;
import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;


/**
 * A class that creates the view for the login page.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 0.5 - 19.04.2023
 */
public class Login {
  /**
   * The constant CSS_FILE that contains the styling for the page.
   */
  private static final String CSS_FILE = "/Styling.css";
  /**
   * An instance of the Random library.
   */
  private static final Random random = new Random();
  /**
   * A text field to enter the username.
   */
  public static TextField username = new TextField();
  /**
   * The current user.
   */
  public static String currentUser;

  /**
   * Returns the current user.
   *
   * @return the current user
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Gets random int.
   *
   * @return the random int
   */
  public static int getRandomInt() {
    return random.nextInt(2) + 1;
  }

  /**
   * Login view parent.
   *
   * @param controller the controller
   * @return the view as a parent
   */
  public static Parent loginView(LoginController controller) {

    Pane background = new Pane();
    background.setPrefSize(1000, 700);

    background.getStylesheets().add(CSS_FILE);

    background.getStyleClass().add("loginScreen" + getRandomInt());

    VBox loginVBox = new VBox();
    loginVBox.setPadding(new Insets(10));

    loginVBox.setAlignment(Pos.CENTER);
    loginVBox.setSpacing(20);
    loginVBox.setMaxSize(300, 400);

    loginVBox.getStylesheets().add(CSS_FILE);

    loginVBox.setId("overlayLogin");

    Text welcomeText = new Text("Take back ");
    welcomeText.setId("titleText");

    Text welcomeText2 = new Text("control of your life");
    welcomeText2.setId("underTitleText");

    username.setPromptText("Enter username");
    username.setId("textField");

    PasswordField password = new PasswordField();
    password.setPromptText("Enter password");
    password.setId("textField");

    Button logIn = new Button("Log in");
    logIn.setId("actionButton");

    password.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        logIn.fire();
      }
    });

    logIn.setOnAction(e -> {
      try {
        LoginBackend.login(username.getText(), password.getText(), controller);
      } catch (IOException ex) {
        throw new IllegalArgumentException(ex);
      }
    });

    Text createUser = new Text("Create user");
    createUser.setId("linkSmallText");

    createUser.setOnMouseClicked(e -> {
      try {
        controller.handleCreateUserButton();
      } catch (Exception ex) {
        throw new IllegalArgumentException(ex);
      }
    });

    Text forgotPassword = new Text("Forgot password");
    forgotPassword.setId("linkSmallText");
    forgotPassword.setOnMouseClicked(e -> {
      try {
        controller.handleForgotPasswordButton();
      } catch (Exception ex) {
        throw new IllegalArgumentException(ex);
      }
    });

    loginVBox.getChildren().addAll(welcomeText,
            welcomeText2, username, password,
            logIn, createUser, forgotPassword);

    StackPane backgroundAndLogin = new StackPane(background, loginVBox);

    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.getStylesheets().add(CSS_FILE);
    return vbox;
  }
}
