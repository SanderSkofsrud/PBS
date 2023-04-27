package edu.ntnu.idatt1002.frontend.utility;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

/**
 * A class that creates an alert window.
 * The alert window is used to display error messages.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class AlertWindow {


  /**
   * A method that creates an alert window.
   * The alert window is used to display error messages.
   *
   * @param message the message to be displayed
   */
  public static void showAlert(String message) {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Alert");
    dialog.setHeaderText(null);
    dialog.setResizable(false);
    dialog.setWidth(300);
    dialog.setHeight(200);

    Label messageLabel = new Label(message);
    messageLabel.setStyle("-fx-font-size: 20px;");

    VBox content = new VBox(messageLabel);
    content.setAlignment(Pos.CENTER);
    content.setPadding(new Insets(10, 10, 10, 10));

    ButtonType okButton = new ButtonType("OK");


    dialog.getDialogPane().getButtonTypes().add(okButton);


    String okButtonStyle = "-fx-font-size: 15px; -fx-min-width: 40px;"
            + " -fx-min-height: " + "20px;-fx-background-color: #9FB8AD;"
            + " -fx-border-width: 2; " + "-fx-padding: 5px; -fx-background-radius: 0.5em;";
    Button okButtonNode = (Button) dialog.getDialogPane().lookupButton(okButton);
    okButtonNode.setStyle(okButtonStyle);
    okButtonNode.setId("actionButton");
    okButtonNode.setAlignment(Pos.CENTER);

    okButtonNode.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        okButtonNode.fire();
      }
    });
    content.getChildren().add(okButtonNode);
    content.setSpacing(20);
    dialog.getDialogPane().setContent(content);
    dialog.showAndWait();
  }
}
