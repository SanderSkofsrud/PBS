package edu.ntnu.idatt1002.frontend;

import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A class that creates the top menu.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.3 - 27.04.2023
 */
public class TopMenu {

  /**
   * An instance of the GUI class.
   */
  private static GUI gui;

  /**
   * Instantiates a new Top menu.
   *
   * @param gui the gui
   */
  public TopMenu(GUI gui) {
    TopMenu.gui = gui;
  }

  /**
   * Creates the top menu bar.
   * Contains all the buttons for the different windows.
   *
   * @param primaryStage the primary stage
   * @return the horizontal box
   */
  public static HBox topMenu(Stage primaryStage) {
    BorderPane borderPane = new BorderPane();
    MenuBar menuBar = new MenuBar();
    borderPane.setTop(menuBar);
    HBox topMenu = new HBox();
    topMenu.setAlignment(Pos.TOP_CENTER);
    primaryStage.show();

    topMenu.setSpacing(20);
    topMenu.setPadding(new Insets(20, 20, 20, 20));

    Button overviewButton = createMenuButton("Overview",
            "/icons/overview.png", () -> handleButtonClick(
                    GUI.overviewWindow, "overview"));
    topMenu.getChildren().add(overviewButton);

    Button transferButton = createMenuButton("Accounts",
            "/icons/transfer.png", () -> handleButtonClick(
                    GUI.transferWindow, "transfer"));
    topMenu.getChildren().add(transferButton);

    Button addExpenseButton = createMenuButton("Add Expense",
            "/icons/addExpense.png", () -> handleButtonClick(
                    GUI.addExpenseWindow, "addExpense"));
    topMenu.getChildren().add(addExpenseButton);

    Button reportButton = createMenuButton("Report",
            "/icons/report.png", () -> handleButtonClick(
                    GUI.reportWindow, "report"));
    topMenu.getChildren().add(reportButton);

    Button budgetButton = createMenuButton("Budget",
            "/icons/budget.png", () -> handleButtonClick(
                    GUI.budgetWindow, "budget"));
    topMenu.getChildren().add(budgetButton);

    Button bankStatementButton = createMenuButton("Bank Statement",
            "/icons/bankStatement.png", () -> handleButtonClick(
                    GUI.bankStatementWindow, "bankStatement"));
    topMenu.getChildren().add(bankStatementButton);


    ImageView settingsIcon = new ImageView(new Image("/icons/settings.png"));
    settingsIcon.setFitHeight(15);
    settingsIcon.setFitWidth(15);

    Button settingsButton = new Button();
    settingsButton.setGraphic(settingsIcon);
    settingsButton.setId("squareButton");
    settingsButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        settingsButton.fire();
        event.consume();
      }
    });
    settingsButton.setOnAction(event -> {
      try {
        GUI.overviewWindow.setVisible(false);
        GUI.transferWindow.setVisible(false);
        GUI.addExpenseWindow.setVisible(false);
        GUI.reportWindow.setVisible(false);
        GUI.settingsWindow.setVisible(true);
        GUI.budgetWindow.setVisible(false);
        GUI.bankStatementWindow.setVisible(false);

        GUI.setPaneToUpdate("settings");
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    });
    topMenu.getChildren().add(settingsButton);


    ImageView logOutImage = new ImageView(new Image("/icons/logOut.png"));
    logOutImage.setFitHeight(15);
    logOutImage.setFitWidth(15);

    Button logOutButton = new Button();
    logOutButton.setGraphic(logOutImage);
    logOutButton.setId("squareButton");

    topMenu.getChildren().add(logOutButton);
    logOutButton.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        logOutButton.fire();
        event.consume();
      }
    });
    logOutButton.setOnAction(event -> {
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));

      try {
        GUI.logout();
        gui.navigateToLogin();
      } catch (Exception e) {
        throw new IllegalArgumentException(e);
      }
    });

    return topMenu;
  }

  /**
   * Creates a button for the top menu.
   *
   * @param buttonText the button text
   * @param iconPath   the icon path
   * @param action     the action
   * @return the button
   */
  private static Button createMenuButton(String buttonText, String iconPath, Runnable action) {
    ImageView buttonImage = new ImageView(new Image(iconPath));
    buttonImage.setFitHeight(20);
    buttonImage.setFitWidth(20);

    Button button = new Button(buttonText, buttonImage);
    button.setId("topMenuButton");
    button.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        button.fire();
        event.consume();
      }
    });
    button.setOnAction(event -> action.run());

    return button;
  }

  /**
   * Handles the button click.
   * Sets the correct window to visible.
   *
   * @param paneToShow   the pane to show
   * @param paneToUpdate the pane to update
   */
  private static void handleButtonClick(Node paneToShow, String paneToUpdate) {
    GUI.overviewWindow.setVisible(false);
    GUI.transferWindow.setVisible(false);
    GUI.addExpenseWindow.setVisible(false);
    GUI.reportWindow.setVisible(false);
    GUI.settingsWindow.setVisible(false);
    GUI.budgetWindow.setVisible(false);
    GUI.bankStatementWindow.setVisible(false);

    paneToShow.setVisible(true);

    GUI.setPaneToUpdate(paneToUpdate);
    GUI.updatePane();
  }
}
