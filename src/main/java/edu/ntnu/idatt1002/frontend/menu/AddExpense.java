package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Expenses;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;


/**
 * A class that creates the add expense view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class AddExpense {

  /**
   * A method that creates the add expense view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox expenseView() {
    VBox addExpenseVBox = new VBox();
    addExpenseVBox.setAlignment(Pos.CENTER);
    addExpenseVBox.getChildren().add(new Label("This is the addExpense page"));

    Text text3 = new Text("Add new expense");
    text3.setId("titleText");
    text3.setLineSpacing(10);

    ObservableList<String> options = FXCollections.observableArrayList(
            "Rent", "Food", "Transportation", "Clothing", "Entertainment", "Other");

    final ComboBox<String> categoryMenu = new ComboBox<>(options);
    categoryMenu.setFocusTraversable(true);

    DatePicker datePicker = new DatePicker();
    datePicker.getStyleClass().add("date-picker");
    datePicker.setValue(LocalDate.now());
    datePicker.setShowWeekNumbers(true);
    datePicker.setFocusTraversable(true);

    ObservableList<String> options2 = null;
    try {
      CSVReader csvReaderInstance = CSVReader.getInstance();
      options2 = FXCollections.observableArrayList(csvReaderInstance.readCSV().keySet());
    } catch (IOException e) {
      showAlert("Error reading CSV file: " + e.getMessage());
    }
    final ComboBox<String> accountMenu = new ComboBox<>(options2);
    accountMenu.setPromptText("Pick an account");
    accountMenu.setId("categoryMenuButton");
    accountMenu.setFocusTraversable(true);
    String originalPromptText = "Pick a category";
    categoryMenu.setPromptText(originalPromptText);
    categoryMenu.setId("categoryMenuButton");

    TextField prices = new TextField();
    prices.setPromptText("Enter price");
    prices.setId("textField");
    prices.setFocusTraversable(true);

    TextField names = new TextField();
    names.setPromptText("Enter name");
    names.setId("textField");
    names.setFocusTraversable(true);

    Label confirmLabel = new Label("The expense has been added.");
    confirmLabel.setVisible(false);
    confirmLabel.setId("confirmLabel");

    Button confirmExpense = new Button("Confirm");
    confirmExpense.setId("actionButton");
    confirmExpense.setFocusTraversable(true);

    prices.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        confirmExpense.fire();
      }
    });

    confirmExpense.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmExpense.fire();
      }
    });

    Accounts accountsInstance = Accounts.getInstance();
    ExcelExporter instance = ExcelExporter.getInstance();

    confirmExpense.setOnAction(e -> {
      if (categoryMenu.getValue() == null) {
        playErrorSound();
        String customMessage = "Please select a category.";
        showAlert(customMessage);
      } else if (prices.getText().isEmpty()) {
        playErrorSound();
        showAlert("Please enter a price.");
      } else if (names.getText().isEmpty()) {
        playErrorSound();
        showAlert("Please enter a name.");
      } else if (accountsInstance.getAccounts().get(accountMenu.getValue())
              - Double.parseDouble(prices.getText()) < 0) {
        showAlert("Not enough money in account.");
        categoryMenu.setValue(null);
        categoryMenu.setPromptText(originalPromptText);
        names.setText(null);
        prices.setText(null);
      } else {
        String selectedOption = categoryMenu.getValue();
        String name = ('|' + names.getText() + '|');
        String tempText = prices.getText();
        String accountName = accountMenu.getValue();
        LocalDate date = datePicker.getValue();
        double price = Double.parseDouble(tempText);
        Expenses expenseInstance = Expenses.getInstance();
        switch (selectedOption) {
          case "Entertainment" -> expenseInstance.addToArrayList(new Expense(name, price,
                  1, datePicker.getValue()), expenseInstance.getEntertainment());
          case "Food" -> expenseInstance.addToArrayList(new Expense(name, price,
                  2, datePicker.getValue()), expenseInstance.getFood());
          case "Transportation" -> expenseInstance.addToArrayList(new Expense(name, price,
                  3, datePicker.getValue()), expenseInstance.getTransportation());
          case "Clothing" -> expenseInstance.addToArrayList(new Expense(name, price,
                  4, datePicker.getValue()), expenseInstance.getClothing());
          case "Other" -> expenseInstance.addToArrayList(new Expense(name, price,
                  5, datePicker.getValue()), expenseInstance.getOther());
          case "Rent" -> expenseInstance.addToArrayList(new Expense(name, price,
                  6, datePicker.getValue()), expenseInstance.getRent());
          default -> showAlert("Error: invalid category selected.");
        }

        confirmLabel.setVisible(true);

        FadeTransition ft = new FadeTransition(Duration.millis(1750), confirmLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        try {
          instance.exportToExcel();
        } catch (IOException ioException) {
          showAlert("Error exporting to Excel: " + ioException.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
                ExcelExporter.getOutputDirectory(), GUI.getCurrentUser() + ".csv"), true))) {
          writer.write(selectedOption + "," + name
                  + "," + date + "," + price + "," + accountName + "\n");
        } catch (IOException f) {
          showAlert("Error writing to file: " + f.getMessage());
        }
        names.setText(null);
        prices.setText(null);
        ft.play();
        SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
      }
    });

    HBox title = new HBox(text3);
    title.setAlignment(Pos.CENTER);
    title.setSpacing(40);

    VBox categoryNamePrice = new VBox(accountMenu, categoryMenu, names, prices);
    categoryNamePrice.setPadding(new Insets(25));
    categoryNamePrice.setSpacing(20);
    categoryNamePrice.setAlignment(Pos.TOP_LEFT);

    VBox calendar = new VBox(datePicker);
    calendar.setAlignment(Pos.TOP_LEFT);
    calendar.setSpacing(20);
    calendar.setPadding(new Insets(25));

    HBox dateAndInput = new HBox(categoryNamePrice, calendar);
    dateAndInput.setAlignment(Pos.CENTER);
    dateAndInput.setPadding(new Insets(15));

    VBox dateAndInputAndConfirm = new VBox(title, dateAndInput, confirmExpense, confirmLabel);
    dateAndInputAndConfirm.setAlignment(Pos.CENTER);
    dateAndInputAndConfirm.setSpacing(20);

    return new VBox(dateAndInputAndConfirm);
  }

  /**
   * Plays an error sound.
   */
  public static void playErrorSound() {
    SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
  }

}