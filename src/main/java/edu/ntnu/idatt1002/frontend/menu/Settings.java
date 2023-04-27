//SETTINGS PANE

package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.user.UserHandling;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.ContactUs;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.Objects;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the settings view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class Settings {
  /*
    * Tht constant LIGHTMODE is used to set the CSS style to lightmode.
   */
  private static final String LIGHTMODE = "Lightmode";
  /*
    * Tht constant TEXTFIELD is used to set the CSS style to textfield.
   */
  private static final String TEXTFIELD = "textField";
  /*
    * Tht constant ACTIONBUTTON is used to set the CSS style to actionButton.
   */
  private static final String ACTIONBUTTON = "actionButton";
  /*
    * Tht constant BODYTEXT is used to set the CSS style to bodyText.
   */
  private static final String BODYTEXT = "bodyText";
  /*
    * Tht constant CONFIRM is used to play the confirmation sound.
   */
  private static final String CONFIRM = "16bitconfirm.wav";
  /*
    * Tht constant ERROR is used to play the error sound.
   */
  private static final String ERROR = "error.wav";
  /*
    * Tht constant RADIOBUTTON is used to set the CSS style to radioButton.
   */
  private static final String RADIOBUTTON = "radioButton";
  /*
    * The current css mode, default is lightmode.
   */
  private static String currentMode = LIGHTMODE;

  /**
   * A method that creates the settings view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox settingsView() {
    VBox vbox = new VBox();
    vbox.getChildren().clear();

    Label passwordLabel = new Label("Enter current password: ");
    passwordLabel.setId("smallTitle");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Enter password");
    passwordField.setId(TEXTFIELD);
    passwordField.setMaxWidth(200);

    Button submitButton = new Button("Submit");

    passwordField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });

    submitButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        submitButton.fire();
      }
    });
    submitButton.setId(ACTIONBUTTON);
    vbox.getChildren().addAll(passwordLabel, passwordField, submitButton);
    vbox.setSpacing(20);
    vbox.setPadding(new Insets(20, 20, 20, 20));


    submitButton.setOnAction(event -> {
      try {
        if (Objects.equals(passwordField.getText(), UserHandling.getPassword())) {


          HBox currentEmailHbox = new HBox();

          VBox currentStatus = new VBox();

          Text title = new Text("Settings");
          title.setId("titleText");
          Text currentEmailText = new Text("Current email: ");
          currentEmailText.setId(BODYTEXT);
          currentEmailText.setTextAlignment(TextAlignment.RIGHT);
          Text currentPasswordText = new Text("Current password: ");
          currentPasswordText.setId(BODYTEXT);
          currentPasswordText.setTextAlignment(TextAlignment.RIGHT);

          currentStatus.getChildren().addAll(currentEmailText, currentPasswordText);
          currentStatus.setAlignment(Pos.TOP_RIGHT);

          VBox currentPassword = new VBox();

          Text currentEmailLabel = new Text(UserHandling.getEmail());
          currentEmailLabel.setId(BODYTEXT);
          currentEmailLabel.setVisible(false);
          currentEmailLabel.setTextAlignment(TextAlignment.LEFT);
          Text currentPasswordLabel = new Text(UserHandling.getPassword());
          currentPasswordLabel.setVisible(false);
          currentPasswordLabel.setId(BODYTEXT);

          ImageView visibilityImage = new ImageView(new Image("icons/visibility.png"));
          visibilityImage.setFitHeight(20);
          visibilityImage.setFitWidth(20);

          Button showPrivateInformation = new Button();
          showPrivateInformation.setOnAction(e -> {
            currentPassword.getChildren().remove(currentPasswordLabel);
            currentPassword.getChildren().add(currentPasswordLabel);
            currentPassword.getChildren().remove(currentEmailLabel);
            currentPassword.getChildren().add(currentEmailLabel);

          });
          showPrivateInformation.setGraphic(visibilityImage);
          showPrivateInformation.setId("squareButton");

          showPrivateInformation.setOnAction(e -> {
            if (currentEmailLabel.isVisible() && currentPasswordLabel.isVisible()) {
              try {
                currentEmailLabel.setText(UserHandling.getEmail());
              } catch (IOException ex) {
                throw new RuntimeException(ex);
              }
              currentPasswordLabel.setText(UserHandling.getPassword());
              currentEmailLabel.setVisible(false);
              currentPasswordLabel.setVisible(false);
              visibilityImage.setImage(new Image("icons/visibility.png"));
            } else {
              currentEmailLabel.setVisible(true);
              currentPasswordLabel.setVisible(true);
              visibilityImage.setImage(new Image("icons/notvisibility.png"));
            }
          });

          currentPassword.getChildren().addAll(currentEmailLabel, currentPasswordLabel);

          currentEmailHbox.getChildren().addAll(currentStatus, currentPassword,
                  showPrivateInformation);

          currentEmailHbox.setSpacing(20);
          currentEmailHbox.setAlignment(Pos.CENTER);

          HBox emailUpdateHbox = new HBox();

          VBox updateEmail = new VBox();
          Text updateEmailText = new Text("Update email: ");
          updateEmailText.setId(BODYTEXT);

          updateEmail.getChildren().addAll(updateEmailText);
          updateEmail.setAlignment(Pos.CENTER);

          VBox updatePassword = new VBox();
          TextField updateEmailTextField = new TextField();
          updateEmailTextField.setId(TEXTFIELD);
          updateEmailTextField.setPromptText("Enter new email");
          updatePassword.getChildren().addAll(updateEmailTextField);
          updatePassword.setAlignment(Pos.CENTER);


          VBox confirmEmailUpdateVbox = new VBox();
          Button confirmEmailUpdate = new Button("Confirm");

          confirmEmailUpdate.setOnAction(e -> {
            try {
              if (updateEmailTextField.getText().length() > 8
                      || updateEmailTextField.getText().length() < 20) {
                UserHandling.changeEmail(updateEmailTextField.getText());
                currentEmailLabel.setText(UserHandling.getEmail());
                SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRM));
                updateEmailTextField.clear();
              } else {
                throw new IllegalArgumentException("Email must be between 8 and 20 characters");
              }
            } catch (Exception ex) {
              SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
              showAlert(ex.getMessage());
            }
          });

          confirmEmailUpdate.setId(ACTIONBUTTON);
          confirmEmailUpdateVbox.getChildren().addAll(confirmEmailUpdate);
          confirmEmailUpdateVbox.setAlignment(Pos.TOP_CENTER);

          emailUpdateHbox.getChildren().addAll(updateEmail, updatePassword, confirmEmailUpdateVbox);
          emailUpdateHbox.setSpacing(20);
          emailUpdateHbox.setAlignment(Pos.CENTER);

          HBox updatePasswordHbox = new HBox();

          VBox currentAndNewPasswordHbox = new VBox();
          Text currentPasswordText2 = new Text("Update password: ");
          currentPasswordText2.setId(BODYTEXT);
          currentAndNewPasswordHbox.getChildren().addAll(currentPasswordText2);
          currentAndNewPasswordHbox.setAlignment(Pos.CENTER);

          VBox currentAndNewPasswordInputFields = new VBox();
          TextField newPasswordTextField = new TextField();
          newPasswordTextField.setId(TEXTFIELD);
          newPasswordTextField.setPromptText("Enter new password");
          currentAndNewPasswordInputFields.getChildren().addAll(newPasswordTextField);
          currentAndNewPasswordInputFields.setAlignment(Pos.CENTER);

          VBox confirmPasswordUpdateVBox = new VBox();
          Button confirmPasswordUpdate = new Button("Confirm");
          confirmPasswordUpdate.setOnAction(e -> {
            try {
              if (newPasswordTextField.getText().length() > 8
                      || newPasswordTextField.getText().length() < 20) {
                UserHandling.changePassword(newPasswordTextField.getText());
                currentPasswordLabel.setText(UserHandling.getPassword());
                SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRM));
                newPasswordTextField.clear();
              } else {
                throw new IllegalArgumentException("Password must be between 8 and 20 characters");
              }
            } catch (Exception ex) {
              SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
              showAlert(ex.getMessage());
            }
          });
          confirmPasswordUpdate.setId(ACTIONBUTTON);
          confirmPasswordUpdateVBox.getChildren().addAll(confirmPasswordUpdate);
          confirmPasswordUpdateVBox.setAlignment(Pos.CENTER);


          updatePasswordHbox.getChildren().addAll(currentAndNewPasswordHbox,
                  currentAndNewPasswordInputFields, confirmPasswordUpdateVBox);
          updatePasswordHbox.setAlignment(Pos.CENTER);
          updatePasswordHbox.setSpacing(20);

          Text prefrences = new Text("Preferences");
          prefrences.setId("titleText");
          prefrences.setTextAlignment(TextAlignment.CENTER);

          HBox viewmodeHbox = new HBox();

          RadioButton lightmode = new RadioButton(LIGHTMODE);
          RadioButton darkmode = new RadioButton("Darkmode");
          RadioButton colorblind = new RadioButton("Colorblindmode");
          lightmode.setId(RADIOBUTTON);
          darkmode.setId(RADIOBUTTON);
          colorblind.setId(RADIOBUTTON);

          ToggleGroup toggleGroup = new ToggleGroup();
          lightmode.setToggleGroup(toggleGroup);
          darkmode.setToggleGroup(toggleGroup);
          colorblind.setToggleGroup(toggleGroup);
          switch (currentMode) {
            case "Darkmode" -> darkmode.setSelected(true);
            case "Colorblindmode" -> colorblind.setSelected(true);
            default -> lightmode.setSelected(true);
          }

          toggleGroup.selectedToggleProperty().addListener((
                  observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
              if (!Objects.equals(((RadioButton) toggleGroup.getSelectedToggle()).getText(),
                      LIGHTMODE)) {
                String selectedText = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                currentMode = selectedText;
                GUI.setStyle(selectedText);
              } else {
                currentMode = LIGHTMODE;
                GUI.setStyle("Styling");
              }
            }
          });

          viewmodeHbox.getChildren().addAll(lightmode, darkmode, colorblind);
          viewmodeHbox.setSpacing(20);
          viewmodeHbox.setAlignment(Pos.CENTER);

          VBox contactUS = new VBox();
          Button contactUSButton = new Button("Contact Us");
          contactUSButton.setId(ACTIONBUTTON);
          contactUSButton.setAlignment(Pos.CENTER);

          contactUSButton.setOnAction(e -> {
            ContactUs.sendEmail();
            SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRM));
          });

          contactUS.getChildren().addAll(contactUSButton);
          contactUS.setAlignment(Pos.CENTER);

          vbox.getChildren().clear();
          vbox.getChildren().addAll(title, currentEmailHbox,
                  emailUpdateHbox, updatePasswordHbox,
                  prefrences, viewmodeHbox, contactUS);
          vbox.setSpacing(40);

          vbox.setAlignment(Pos.TOP_CENTER);
        } else {
          throw new IllegalArgumentException("Incorrect password");
        }
      } catch (Exception e) {
        SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
        showAlert(e.getMessage());
      }
    });
    return vbox;
  }
}
