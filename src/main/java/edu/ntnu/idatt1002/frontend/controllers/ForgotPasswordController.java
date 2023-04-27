package edu.ntnu.idatt1002.frontend.controllers;

import edu.ntnu.idatt1002.frontend.GUI;

/**
 * A class that handles the forgot password view.
 * The class is used for navigating between the ForgotPasswordView and the LoginView.
 * The class is used by the ForgotPasswordView.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 19.04.2023
 */
public class ForgotPasswordController {
  /**
   * An instance of the GUI class.
   */
  private final GUI gui;

  /**
   * Instantiates a new Forgot password controller.
   *
   * @param gui the gui
   */
  public ForgotPasswordController(GUI gui) {
    this.gui = gui;
  }

  /**
   * A method that handles the back button.
   */
  public void handleBackButton() {
    gui.navigateToLogin();
  }

  /**
   * A method that handles the reset button.
   */
  public void handleResetButton() {
    gui.navigateToLogin();
  }
}
