package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.frontend.GUI;
import java.io.IOException;

/**
 * A class that handles the login of a user.
 * The class is used for navigating between the LoginView and the MainAppView.
 * The class is used by the LoginView.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class LoginController {
  /**
   * An instance of the GUI class.
   */
  private final GUI gui;

  /**
   * Instantiates a new Login controller.
   *
   * @param gui the gui
   */
  public LoginController(GUI gui) {
    this.gui = gui;
  }

  /**
   * A method that handles the login button.
   *
   * @throws IOException the io exception
   */
  public void handleLoginButton() throws IOException {
    gui.navigateToMainApp();
  }

  /**
   * A method that handles the create user button.
   */
  public void handleCreateUserButton() {
    gui.navigateToCreateUser();
  }

  /**
   * A method that handles the forgot password button.
   */
  public void handleForgotPasswordButton() {
    gui.navigateToForgotPassword();
  }

}
