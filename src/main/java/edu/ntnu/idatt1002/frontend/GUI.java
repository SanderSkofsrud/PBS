package edu.ntnu.idatt1002.frontend;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.user.LoginBackend;
import edu.ntnu.idatt1002.frontend.controllers.CreateUserController;
import edu.ntnu.idatt1002.frontend.controllers.ForgotPasswordController;
import edu.ntnu.idatt1002.frontend.controllers.LoginController;
import edu.ntnu.idatt1002.frontend.menu.*;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


/**
 * A class that controls the main part of the GUI.
 * The GUI is made using JavaFX.
 * Each page is a StackPane, and the buttons are added to the StackPane.
 * The pages are connected to each other using buttons.
 * The buttons are added to the StackPane, and the StackPane is added to the scene.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.4 - 27.04.2023
 */
public class GUI extends Application {
  /**
   * A map that stores all the panes.
   * Is used to update the panes when they need to be updated.
   */
  private static final Map<String, Pane> paneCache = new HashMap<>();
  /**
   * A map that stores the status of the panes.
   * Is used to update the panes when they need to be updated.
   */
  private static final Map<String, Boolean> paneUpdateStatus = new HashMap<>();
  /**
   * The current user.
   */
  private static final String REPORT = "report";
  /**
   * The constant currentUser.
   */
  public static String currentUser;
  /**
   * The constant overviewWindow that contains the overview page.
   */
  protected static StackPane overviewWindow = new StackPane();
  /**
   * The constant transferWindow that contains the transfer page.
   */
  protected static StackPane transferWindow = new StackPane();
  /**
   * The constant reportWindow that contains the report page.
   */
  protected static StackPane reportWindow = new StackPane();
  /**
   * The constant addExpenseWindow that contains the add expense page.
   */
  protected static StackPane addExpenseWindow = new StackPane();
  /**
   * The constant settingsWindow that contains the settings page.
   */
  protected static StackPane settingsWindow = new StackPane();
  /**
   * The constant budgetWindow that contains the budget page.
   */
  protected static StackPane budgetWindow = new StackPane();
  /**
   * The constant bankStatementWindow that contains the bank statement page.
   */
  protected static StackPane bankStatementWindow = new StackPane();
  /**
   * The Border pane that contains the different panes.
   */
  static BorderPane borderPane = new BorderPane();
  /**
   * The Scroll pane.
   */
  static ScrollPane scrollPane = new ScrollPane();
  /**
   * The stylesheet for the GUI.
   */
  private static String stylesheet = "/Styling.css";
  /**
   * The primary stage of the GUI.
   */
  private Stage primaryStage;
  /**
   * The login controller.
   */
  private LoginController loginController;
  /**
   * The login view.
   */
  private Login loginView;


  /**
   * Instantiates a new Gui.
   */
  public GUI() {
  }

  /**
   * The main method that starts the GUI.
   *
   * @param primaryStage the primary stage
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    primaryStage.getIcons().add(new Image("icons/icon.png"));
    primaryStage.setMinWidth(1050);
    primaryStage.setMinHeight(700);
    loginController = new LoginController(this);
    loginView = new Login();

    Parent root = Login.loginView(loginController);
    Scene scene = new Scene(root);

    scene.getStylesheets().add(stylesheet);

    primaryStage.setTitle("Login");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Sets the login view as the current scene.
   */
  public void navigateToLogin() {
    loginController = new LoginController(this);
    loginView = new Login();

    Parent root = Login.loginView(loginController);
    Scene scene = new Scene(root);
    scene.getStylesheets().add(stylesheet);
    updateScene(scene, root);
  }

  /**
   * Sets the create user view as the current scene.
   */
  public void navigateToCreateUser() {
    CreateUserController createUserController = new CreateUserController(this);
    CreateUser createUser = new CreateUser();

    Parent root = CreateUser.createUserView(createUserController);
    Scene scene = new Scene(root);
    scene.getStylesheets().add(stylesheet);
    updateScene(scene, root);
  }

  /**
   * Sets the forgot password view as the current scene.
   */
  public void navigateToForgotPassword() {
    ForgotPasswordController forgotPasswordController = new ForgotPasswordController(this);
    ForgotPassword forgotPasswordView = new ForgotPassword();

    Parent root = forgotPasswordView.forgottenPasswordView(forgotPasswordController);
    Scene scene = new Scene(root);
    scene.getStylesheets().add(stylesheet);
    updateScene(scene, root);
  }

  /**
   * Sets the main app view as the current scene.
   * The main app view contains the different pages.
   * The pages are added to the border pane.
   * The border pane is added to the scene.
   * The scene is added to the stage.
   * The stage is shown.
   *
   * @throws IOException the io exception
   */
  public void navigateToMainApp() throws IOException {
    if (LoginBackend.getCurrentUser() != null) {
      setCurrentUser(LoginBackend.getCurrentUser());
    } else if (CreateUser.getCurrentUser() != null) {
      setCurrentUser(CreateUser.getCurrentUser());
    } else {
      setCurrentUser("User");
    }

    overviewWindow.getChildren().add(Overview.overviewView());
    overviewWindow.getStylesheets().add(stylesheet);

    transferWindow.getChildren().add(Transfer.transferView());
    transferWindow.getStylesheets().add(stylesheet);

    reportWindow.getChildren().add(Report.reportView());
    reportWindow.getStylesheets().add(stylesheet);

    addExpenseWindow.getChildren().add(AddExpense.expenseView());
    addExpenseWindow.getStylesheets().add(stylesheet);

    settingsWindow.getChildren().add(Settings.settingsView());
    settingsWindow.getStylesheets().add(stylesheet);

    budgetWindow.getChildren().add(Budget.budgetView());
    budgetWindow.getStylesheets().add(stylesheet);

    bankStatementWindow.getChildren().add(BankStatement.bankStatementView());
    bankStatementWindow.getStylesheets().add(stylesheet);

    updatePane();

    primaryStage.setTitle("Bank");
    primaryStage.setWidth(1050);
    primaryStage.setHeight(700);
    overviewWindow.getStylesheets().add(stylesheet);

    ScrollPane newScrollPane = new ScrollPane();
    newScrollPane.setFitToWidth(true);
    newScrollPane.setFitToHeight(false);

    newScrollPane.getStylesheets().add(stylesheet);

    borderPane.setId("background");
    newScrollPane.setId("background");

    newScrollPane.setContent(borderPane);

    Scene scene = new Scene(newScrollPane);
    primaryStage.setScene(scene);
    primaryStage.show();

    overviewWindow.setVisible(true);
    transferWindow.setVisible(false);
    addExpenseWindow.setVisible(false);
    reportWindow.setVisible(false);
    settingsWindow.setVisible(false);
    budgetWindow.setVisible(false);
    bankStatementWindow.setVisible(false);

    StackPane root = new StackPane();

    root.getChildren().addAll(overviewWindow, transferWindow,
            addExpenseWindow, reportWindow, settingsWindow,
            budgetWindow, bankStatementWindow);
    TopMenu topMenu = new TopMenu(this);
    borderPane.setTop(TopMenu.topMenu(primaryStage));
    borderPane.setCenter(root);

    updatePane();
  }

  /**
   * Updates the panes that need updating.
   */
  public static void updatePane() {
    try {
      ExcelExporter instance = ExcelExporter.getInstance();

      instance.exportToExcel();
      instance.convertToPdf(instance.exportToExcel(), REPORT);
    } catch (IOException | DocumentException ex) {
      throw new IllegalArgumentException("Could not export to pdf");
    }
    overviewWindow.getChildren().clear();
    overviewWindow.getChildren().add(Overview.overviewView());
    updateCachedPane("overview", Overview::overviewView, overviewWindow);
    updateCachedPane("transfer", Transfer::transferView, transferWindow);
    updateCachedPane("addExpense", AddExpense::expenseView, addExpenseWindow);
    updateCachedPane(REPORT, Report::reportView, reportWindow);
    updateCachedPane("budget", Budget::budgetView, budgetWindow);
    updateCachedPane("settings", Settings::settingsView, settingsWindow);
    updateCachedPane("bankStatement", BankStatement::bankStatementView, bankStatementWindow);
  }

  /**
   * Updates the scene.
   *
   * @param scene the scene to update
   * @param root  the root of the scene
   */
  private void updateScene(Scene scene, Parent root) {
    Stage stage = (Stage) primaryStage.getScene().getWindow();
    stage.setScene(scene);
    primaryStage.setMinWidth(1050);
    primaryStage.setMinHeight(700);
  }

  /**
   * Logs out the user.
   */
  public static void logout() {
    setPaneToUpdate("overview");
    setPaneToUpdate("transfer");
    setPaneToUpdate("addExpense");
    setPaneToUpdate(REPORT);
    setPaneToUpdate("budget");
    setPaneToUpdate("settings");
    setPaneToUpdate("bankStatement");
    LoginBackend.setCurrentUser(null);
    CreateUser.setCurrentUser(null);
    setStyle("Styling");
  }

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
    GUI.currentUser = currentUser;
  }

  /**
   * Returns the stylesheet.
   *
   * @return the stylesheet
   */
  public static String getStylesheet() {
    return stylesheet;
  }

  /**
   * Sets the stylesheet.
   *
   * @param stylesheet the stylesheet
   */
  public static void setStylesheet(String stylesheet) {
    GUI.stylesheet = stylesheet;
  }

  /**
   * Sets the style.
   *
   * @param style the style
   */
  public static void setStyle(String style) {

    setStylesheet(style);

    StackPane[] stackPanes = new StackPane[]{overviewWindow,
        transferWindow, addExpenseWindow, reportWindow,
        budgetWindow, settingsWindow, bankStatementWindow};
    BorderPane[] borderPanes = new BorderPane[]{borderPane};

    for (StackPane stackPane : stackPanes) {
      stackPane.getStylesheets().clear();
      stackPane.getStylesheets().add("/" + style + ".css");
    }

    for (BorderPane borderPane : borderPanes) {
      borderPane.getStylesheets().clear();
      borderPane.getStylesheets().add("/" + style + ".css");
    }


  }

  /**
   * Updates the cached pane.
   *
   * @param paneName     the pane name to update
   * @param paneSupplier the pane supplier
   * @param window       the window to update
   */
  private static void updateCachedPane(String paneName, Supplier<Pane> paneSupplier, Pane window) {
    Pane pane = paneCache.computeIfAbsent(paneName, key -> paneSupplier.get());

    // Check if the pane needs an update
    if (paneUpdateStatus.getOrDefault(paneName, false)) {
      pane = paneSupplier.get();
      paneCache.put(paneName, pane);
      paneUpdateStatus.put(paneName, false);
    }

    if (window.getChildren().isEmpty() || !window.getChildren().get(0).equals(pane)) {
      window.getChildren().setAll(pane);
    }
  }

  /**
   * Sets the pane to update.
   *
   * @param paneName the pane name
   */
  public static void setPaneToUpdate(String paneName) {
    paneUpdateStatus.put(paneName, true);
  }

  /**
   * Gets scene.
   *
   * @return the scene
   */
  public Scene getScene() {
    return primaryStage.getScene();
  }
}
