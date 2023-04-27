package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.backend.user.CreateUserBackend;
import edu.ntnu.idatt1002.frontend.controllers.CreateUserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

/**
 * A class that creates the view for the create user page.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.5 - 27.04.2023
 */
public class CreateUser {
  /*
   * The constant CSS_FILE that contains the styling for the page.
   */
  private static final String CSS_FILE = "/Styling.css";
  /*
   * The constant TEXTFIELD that is used for the id of text fields.
   */
  private static final String TEXTFIELD = "textField";
  /*
   * The constant ERRORTEXT that is used for the id of error texts.
   */
  private static final String ERRORTEXT = "errorText";
  /*
   * The constant PASSWORD_REQUIREMENTS that is used to define
   * the requirements of a password.
   */
  private static final String PASSWORD_REQUIREMENTS =
          """
          Password must contain at least 8 characters,
          one uppercase letter,
          one lowercase letter,
          one number and one special character.\s
          """;
  /*
   * An instance of the Random library.
   */
  private static final Random random = new Random();
  /**
   * A text field to enter the username.
   */
  private static final TextField username = new TextField();
  /**
   * The current user.
   */
  private static String currentUser;

  /**
   * Returns the current user.
   *
   * @return the current user
   */
  public static String getCurrentUser() {
    return currentUser;
  }

  /**
   * Sets the current user.
   *
   * @param currentUser the current user
   */
  public static void setCurrentUser(String currentUser) {
    CreateUser.currentUser = currentUser;
  }

  /**
   * Gets random int.
   *
   * @return the random int
   */
  public static int getRandomInt() {
    return random.nextInt(1) + 1;
  }

  /**
   * A method that creates the create user view.
   *
   * @param controller the controller
   * @return the view as a parent
   */
  public static Parent createUserView(CreateUserController controller) {

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

    Text welcomeText = new Text("Create user ");
    welcomeText.setId("titleText");

    Text welcomeText2 = new Text("Please enter your information");
    welcomeText2.setId("underTitleText");

    VBox usernameBox = new VBox();
    usernameBox.setSpacing(1);

    username.setPromptText("Enter username");
    username.setId(TEXTFIELD);

    Text usernameError = new Text();
    usernameError.setId(ERRORTEXT);

    usernameBox.getChildren().addAll(username, usernameError);

    VBox emailBox = new VBox();
    emailBox.setSpacing(1);

    TextField email = new TextField();
    email.setPromptText("Enter email");
    email.setId(TEXTFIELD);

    Text emailError = new Text();
    emailError.setId(ERRORTEXT);

    emailBox.getChildren().addAll(email, emailError);

    VBox passwordBox = new VBox();
    passwordBox.setSpacing(1);

    PasswordField password = new PasswordField();
    password.setPromptText("Enter password");
    password.setId(TEXTFIELD);

    Text passwordError = new Text();
    passwordError.setId(ERRORTEXT);

    passwordBox.getChildren().addAll(password, passwordError);

    VBox passwordBox2 = new VBox();
    passwordBox2.setSpacing(1);

    PasswordField password2 = new PasswordField();
    password2.setPromptText("Repeat password");
    password2.setId(TEXTFIELD);

    Text password2Error = new Text();
    password2Error.setId(ERRORTEXT);

    passwordBox2.getChildren().addAll(password2, password2Error);

    VBox createUserBox = new VBox();
    createUserBox.setSpacing(1);
    createUserBox.getChildren().addAll(usernameBox, emailBox, passwordBox, passwordBox2);


    Button createUser = new Button("Create user");
    createUser.setId("actionButton");

    password2.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        createUser.fire();
      }
    });

    createUser.setOnAction(e -> {
      String passwordStringTest1 = password.getText();
      String passwordStringTest2 = password2.getText();

      String passwordString;
      usernameError.setText("");
      emailError.setText("");
      passwordError.setText("");
      password2Error.setText("");

      if (!CreateUserBackend.isValidUsername(username.getText())) {
        usernameError.setText("""
                A user with this username already exists.
                """);
        return;
      } else if (!CreateUserBackend.isValidEmail(email.getText())) {
        emailError.setText("""
                Email is not valid. It needs to be in the format:
                username@email.domain
                """);
        return;
      } else if (!CreateUserBackend.isValidPassword(passwordStringTest1)) {
        passwordError.setText(PASSWORD_REQUIREMENTS);
        return;
      } else if (!passwordStringTest1.equals(passwordStringTest2)) {
        password2Error.setText("Passwords do not match");
        return;
      } else {
        passwordString = passwordStringTest1;
        setCurrentUser(username.getText());
      }

      try {
        controller.handleCreateButton(username.getText(), passwordString, email.getText());
        username.clear();
      } catch (IOException ex) {
        throw new IllegalArgumentException("Something went wrong with the file");
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


    loginVBox.getChildren().addAll(welcomeText, welcomeText2, createUserBox, createUser);

    StackPane backgroundAndLogin = new StackPane(background, backButtonBox, loginVBox);
    backgroundAndLogin.getStylesheets().add(CSS_FILE);


    VBox vbox = new VBox(backgroundAndLogin);
    vbox.setAlignment(Pos.TOP_CENTER);
    return vbox;
  }
}
