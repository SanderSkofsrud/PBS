package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.backend.budgeting.Income;
import edu.ntnu.idatt1002.backend.budgeting.Incomes;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the transfer view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class Transfer {
  /*
   * The constant TITLETEXT used to set the id of the title text.
   */
  private static final String TITLETEXT = "titleText";
  /*
   * The constant SELECTACCOUNT used for prompt text.
   */
  private static final String SELECTACCOUNT = "Select Account";
  /*
   * The constant CATEGORYMENUBUTTON used to set the id of the category menu button.
   */
  private static final String CATEGORYMENUBUTTON = "categoryMenuButton";
  /*
   * The constant ACTIONBUTTON used to set the id of the action button.
   */
  private static final String ACTIONBUTTON = "actionButton";
  /*
   * The constant TEXTFIELD used to set the id of the text field.
   */
  private static final String TEXTFIELD = "textField";
  /*
   * The constant CONFIRM used for prompt text.
   */
  private static final String CONFIRM = "Confirm";
  /*
   * The constant CONFIRMLABEL used to set the id of the confirm label.
   */
  private static final String CONFIRMLABEL = "confirmLabel";
  /*
   * The constant CANNOT_TRANSFER_NEGATIVE_AMOUNT used for error message.
   */
  private static final String CANNOT_TRANSFER_NEGATIVE_AMOUNT
          = "You cannot transfer a negative " + "amount of money";
  /*
   * The constant FILL_ALL_FIELDS used for error message.
   */
  private static final String FILL_ALL_FIELDS = "Please fill in all fields.";
  /*
   * The constant CONFIRMSOUND used for the sound when the user confirms the transfer.
   */
  private static final String CONFIRMSOUND = "16bitconfirm.wav";
  /*
   * The constant ERROR used for the sound when an error occurs.
   */
  private static final String ERROR = "error.wav";
  /*
   * The constant TRANSFER_CSV used for the name of the transfer csv file.
   */
  private static final String TRANSFER_CSV = "transfer.csv";
  /*
   * The constant ERRORMESSAGE used for the error message.
   */
  private static final String ERRORMESSAGE = "An error occurred while trying to write to the file.";

  /**
   * A method that creates the transfer view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox transferView() {
    VBox vbox = null;

    Accounts instance = Accounts.getInstance();
    Incomes incomesInstance = Incomes.getInstance();
    try {
      VBox transferVBox = new VBox();


      Text transferBetweenAccounts = new Text("Transfer between accounts:");
      transferBetweenAccounts.setId(TITLETEXT);


      HBox transferBetweenAccountsHbox = new HBox();
      transferBetweenAccountsHbox.setSpacing(20);
      transferBetweenAccountsHbox.setAlignment(Pos.CENTER);

      ComboBox<String> leftTransfer = new ComboBox<>();
      leftTransfer.setPromptText(SELECTACCOUNT);
      leftTransfer.setItems(FXCollections.observableArrayList(instance.getAccounts().keySet()));
      leftTransfer.setFocusTraversable(true);
      leftTransfer.setId(CATEGORYMENUBUTTON);


      ImageView arrow = new ImageView(new Image("icons/fromTo.png"));
      arrow.setFitHeight(20);
      arrow.setFitWidth(20);


      ComboBox<String> rightTransfer = new ComboBox<>();
      rightTransfer.setPromptText(SELECTACCOUNT);

      rightTransfer.setDisable(true);
      rightTransfer.setItems(FXCollections.observableArrayList(instance.getAccounts().keySet()));
      leftTransfer.setOnAction(e -> {
        rightTransfer.setItems(FXCollections.observableArrayList(instance.getAccounts().keySet()));
        rightTransfer.setDisable(false);
        rightTransfer.getItems().remove(leftTransfer.getValue());
      });
      rightTransfer.setId(CATEGORYMENUBUTTON);
      rightTransfer.setFocusTraversable(true);

      TextField priceEntry = new TextField();
      priceEntry.setFocusTraversable(true);
      priceEntry.setPromptText("Enter transfer amount");
      priceEntry.setId(TEXTFIELD);

      Button confirmTransfer = new Button(CONFIRM);
      confirmTransfer.setId(ACTIONBUTTON);

      Label confirmTransferLabel = new Label("The transfer has been confirmed");
      confirmTransferLabel.setVisible(false);
      confirmTransferLabel.setId(CONFIRMLABEL);
      confirmTransferLabel.setAlignment(Pos.CENTER);

      HBox confirmTransferHbox = new HBox(confirmTransferLabel);
      confirmTransferHbox.setAlignment(Pos.CENTER);

      priceEntry.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
          confirmTransfer.fire(); // Simulate a click event on the logIn button
        }
      });

      confirmTransfer.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmTransfer.fire();
        }
      });

      confirmTransfer.setFocusTraversable(true);
      confirmTransfer.setOnAction(e -> {


        String removeFromAccount = leftTransfer.getValue();
        String addToAccount = rightTransfer.getValue();
        String tempText = priceEntry.getText();
        if (removeFromAccount == null || addToAccount == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(FILL_ALL_FIELDS);
          throw new IllegalArgumentException(FILL_ALL_FIELDS);
        }
        double amountToAdd;
        amountToAdd = Double.parseDouble(tempText);
        if (amountToAdd < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
          throw new IllegalArgumentException(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
        }
        if (amountToAdd > instance.getAccounts().get(removeFromAccount)) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert("You do not have enough money in the " + removeFromAccount
                  + " account to transfer " + amountToAdd + " to the "
                  + addToAccount + " account");
          throw new IllegalArgumentException("You do not have enough money in the "
                  + removeFromAccount + " account to transfer " + amountToAdd + " to the "
                  + addToAccount + " account");
        }

        confirmTransferLabel.setVisible(true);

        FadeTransition ftTransfer = new FadeTransition(
                javafx.util.Duration.millis(1750), confirmTransferLabel);
        ftTransfer.setFromValue(1.0);
        ftTransfer.setToValue(0.0);

        ftTransfer.play();
        SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRMSOUND));
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(ExcelExporter.getOutputDirectory(),
                        GUI.getCurrentUser() + TRANSFER_CSV), true))) {
          writer.write(removeFromAccount + "," + (amountToAdd * -1)
                  + "," + LocalDate.now() + "," + 'B' + "\n");
          writer.write(addToAccount + "," + amountToAdd + "," + LocalDate.now() + "," + 'B' + "\n");
        } catch (IOException f) {
          showAlert(ERRORMESSAGE);
        }
        leftTransfer.setValue(null);
        rightTransfer.setValue(null);
        priceEntry.setText(null);
        rightTransfer.setDisable(true);
        rightTransfer.getItems().clear();
        ftTransfer.setOnFinished(event1 -> {
          GUI.setPaneToUpdate("transfer");
          GUI.updatePane();
        });
      });


      Text registerIncome = new Text("Register new income:");
      registerIncome.setId(TITLETEXT);


      HBox registerIncomeHBox = new HBox();
      registerIncomeHBox.setSpacing(20);
      registerIncomeHBox.setAlignment(Pos.CENTER);


      ComboBox<String> incomeAccount = new ComboBox<>();
      incomeAccount.setPromptText(SELECTACCOUNT);

      CSVReader csvReaderInstance = CSVReader.getInstance();

      incomeAccount.setItems(FXCollections.observableArrayList(
              csvReaderInstance.readCSV().keySet()));

      incomeAccount.setId(CATEGORYMENUBUTTON);
      incomeAccount.setFocusTraversable(true);

      TextField amountIncome = new TextField();
      amountIncome.setPromptText("Enter income amount");
      amountIncome.setId(TEXTFIELD);

      Button confirmIncome = new Button("Confirm");
      confirmIncome.setId(ACTIONBUTTON);

      Label confirmIncomeLabel = new Label("The income has been confirmed");
      confirmIncomeLabel.setVisible(false);
      confirmIncomeLabel.setId(CONFIRMLABEL);
      confirmIncomeLabel.setAlignment(Pos.CENTER);

      HBox confirmIncomeHbox = new HBox(confirmIncomeLabel);
      confirmIncomeHbox.setAlignment(Pos.CENTER);

      amountIncome.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.ENTER) {
          confirmIncome.fire();
        }
      });

      confirmIncome.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmIncome.fire();
        }
      });

      confirmIncome.setFocusTraversable(true);
      confirmIncome.setOnAction(e -> {


        String inncomeAccountName = incomeAccount.getValue();
        String tempText = amountIncome.getText();
        if (inncomeAccountName == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(FILL_ALL_FIELDS);
          throw new IllegalArgumentException(FILL_ALL_FIELDS);
        }

        double amountToAdd;
        amountToAdd = Double.parseDouble(tempText);
        if (amountToAdd < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
          throw new IllegalArgumentException(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
        }

        confirmIncomeLabel.setVisible(true);
        FadeTransition ftIncome = new FadeTransition(
                javafx.util.Duration.millis(1750), confirmIncomeLabel);
        ftIncome.setFromValue(1.0);
        ftIncome.setToValue(0.0);

        incomesInstance.getIncomes().add(new Income(
                inncomeAccountName, amountToAdd, 1, LocalDate.now()));
        ftIncome.play();
        SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRMSOUND));
        incomeAccount.setValue(null);
        amountIncome.setText(null);

        Accounts accounts = Accounts.getInstance();

        leftTransfer.getItems().clear();
        leftTransfer.getItems().addAll(accounts.getAccounts().keySet());

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(ExcelExporter.getOutputDirectory(),
                        GUI.getCurrentUser() + TRANSFER_CSV), true))) {
          writer.write(inncomeAccountName + "," + amountToAdd
                  + "," + LocalDate.now() + "," + 'A' + "\n");
        } catch (IOException f) {
          showAlert(ERRORMESSAGE);
        }

        ftIncome.setOnFinished(event1 -> {
          GUI.setPaneToUpdate("transfer");
          GUI.updatePane();
        });
      });
      Text addNewAccount = new Text("Add new account:");
      addNewAccount.setId(TITLETEXT);
      addNewAccount.setFocusTraversable(true);

      HBox addNewAccountHBox = new HBox();
      addNewAccountHBox.setSpacing(20);
      addNewAccountHBox.setAlignment(Pos.CENTER);

      TextField newAccountName = new TextField();
      newAccountName.setFocusTraversable(true);
      newAccountName.setPromptText("Enter account name");
      newAccountName.setId(TEXTFIELD);

      TextField newAccountBalance = new TextField();
      newAccountBalance.setFocusTraversable(true);
      newAccountBalance.setPromptText("Enter account balance");
      newAccountBalance.setId(TEXTFIELD);

      Label confirmNewAccountLabel = new Label("The account has been confirmed");
      confirmNewAccountLabel.setVisible(false);
      confirmNewAccountLabel.setId(CONFIRMLABEL);
      confirmNewAccountLabel.setAlignment(Pos.CENTER);

      HBox confirmNewAccountHbox = new HBox(confirmNewAccountLabel);
      confirmNewAccountHbox.setAlignment(Pos.CENTER);

      Button confirmNewAccount = new Button("Confirm");
      confirmNewAccount.setFocusTraversable(true);
      confirmNewAccount.setId(ACTIONBUTTON);

      newAccountBalance.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmNewAccount.fire();
        }
      });
      confirmNewAccount.setOnKeyPressed(event -> {
        if (event.getCode() == KeyCode.ENTER) {
          confirmNewAccount.fire();
        }
      });
      confirmNewAccount.setOnAction(e -> {


        String accountName = newAccountName.getText();
        String tempText = newAccountBalance.getText();
        if (accountName == null || tempText.isEmpty()) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(FILL_ALL_FIELDS);
          throw new IllegalArgumentException(FILL_ALL_FIELDS);
        }

        double accountBalance;
        accountBalance = Double.parseDouble(tempText);
        if (accountBalance < 0) {
          SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
          showAlert(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
          throw new IllegalArgumentException(CANNOT_TRANSFER_NEGATIVE_AMOUNT);
        }

        confirmNewAccountLabel.setVisible(true);
        FadeTransition ftNewAccount = new FadeTransition(
                javafx.util.Duration.millis(1750), confirmNewAccountLabel);
        ftNewAccount.setFromValue(1.0);
        ftNewAccount.setToValue(0.0);

        Accounts accounts = Accounts.getInstance();
        accounts.addAccount(accountName, accountBalance);
        ftNewAccount.play();
        SoundPlayer.play(FileUtil.getResourceFilePath(CONFIRMSOUND));
        newAccountName.setText(null);
        newAccountBalance.setText(null);

        leftTransfer.getItems().clear();
        leftTransfer.getItems().addAll(accounts.getAccounts().keySet());

        incomeAccount.getItems().clear();
        incomeAccount.getItems().addAll(accounts.getAccounts().keySet());


        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(ExcelExporter.getOutputDirectory(),
                        GUI.getCurrentUser() + TRANSFER_CSV), true))) {
          writer.write(accountName + "," + accountBalance + ","
                  + LocalDate.now() + "," + 'A' + "\n");
        } catch (IOException f) {
          showAlert(ERRORMESSAGE);
        }
      });


      transferBetweenAccountsHbox.getChildren().addAll(
              leftTransfer, arrow, rightTransfer, priceEntry, confirmTransfer);

      registerIncomeHBox.getChildren().addAll(incomeAccount, amountIncome, confirmIncome);

      addNewAccountHBox.getChildren().addAll(newAccountName, newAccountBalance, confirmNewAccount);

      transferVBox.getChildren().addAll(transferBetweenAccounts,
              transferBetweenAccountsHbox, confirmTransferHbox,
              registerIncome, registerIncomeHBox, confirmIncomeHbox,
              addNewAccount, addNewAccountHBox, confirmNewAccountHbox);
      transferVBox.setSpacing(13);

      vbox = new VBox(transferVBox);
      vbox.setPadding(new Insets(40, 40, 40, 40));
    } catch (Exception e) {
      SoundPlayer.play(FileUtil.getResourceFilePath(ERROR));
      showAlert(e.getMessage());
    }
    return vbox;
  }
}