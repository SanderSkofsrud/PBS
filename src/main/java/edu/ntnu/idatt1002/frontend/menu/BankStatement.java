package edu.ntnu.idatt1002.frontend.menu;

import com.itextpdf.text.DocumentException;
import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the bank statement view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.0 - 26.04.2023
 */
public class BankStatement {

  /**
   * A method that creates the bank statement view. The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox bankStatementView() {

    VBox bankStatementVbox = new VBox();
    bankStatementVbox.setSpacing(40);

    Text viewBankStatement = new Text("View bank statement");
    viewBankStatement.setId("titleText");

    HBox selectAccountHbox = new HBox();
    selectAccountHbox.setAlignment(Pos.CENTER);

    ObservableList<String> options2 = null;
    try {
      CSVReader csvReaderInstance = CSVReader.getInstance();
      options2 = FXCollections.observableArrayList(csvReaderInstance.readCSV().keySet());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    final ComboBox<String> accountMenu = new ComboBox<>(options2);
    accountMenu.setId("categoryMenuButton");
    accountMenu.setPromptText("Select account");
    selectAccountHbox.getChildren().addAll(accountMenu);

    HBox selectCategoryHbox = new HBox();
    selectCategoryHbox.setAlignment(Pos.CENTER);

    ObservableList<String> options = FXCollections.observableArrayList(
            "Rent", "Food", "Transportation", "Clothing", "Entertainment", "Other");
    final ComboBox<String> categoryMenu = new ComboBox<>(options);
    categoryMenu.setPromptText("Pick a category");
    categoryMenu.setId("categoryMenuButton");
    selectCategoryHbox.getChildren().addAll(categoryMenu);

    HBox calenderIntervalHbox = new HBox();
    calenderIntervalHbox.setAlignment(Pos.CENTER);

    Text calenderIntervalText = new Text("Select timeframe: ");
    calenderIntervalText.setId("bodyText");

    DatePicker datePickerFrom = new DatePicker();
    datePickerFrom.setValue(LocalDate.now());
    datePickerFrom.setShowWeekNumbers(true);

    ImageView arrow = new ImageView(new Image("icons/fromTo.png"));
    arrow.setFitHeight(20);
    arrow.setFitWidth(20);

    DatePicker datePickerTo = new DatePicker();
    datePickerTo.setValue(LocalDate.now());
    datePickerTo.setShowWeekNumbers(true);

    calenderIntervalHbox.getChildren().addAll(datePickerFrom, arrow, datePickerTo);
    calenderIntervalHbox.setSpacing(20);

    Button export = new Button("Export to PDF");
    export.setId("actionButton");

    export.setOnAction(e -> {
      if (accountMenu.getValue() == null) {
        SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
        String customMessage = "Please select an account";
        showAlert(customMessage);
      } else if (categoryMenu.getValue() == null) {
        SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
        String customMessage = "Please select a category";
        showAlert(customMessage);
      } else {
        String account = accountMenu.getValue();
        String category = categoryMenu.getValue();
        String from = String.valueOf(datePickerFrom.getValue());
        String to = String.valueOf(datePickerTo.getValue());

        try {
          ExcelExporter instance = ExcelExporter.getInstance();
          instance.convertToPdf(instance.createBankStatement(
                  account, category, from, to), "bankstatement");

          if (Desktop.isDesktopSupported()) {
            File myFile = new File(ExcelExporter.getBankStatementPath());
            Desktop.getDesktop().open(myFile);
          }
        } catch (IOException | DocumentException exception) {
          showAlert(exception.getMessage());
        }
      }
    });


    HBox tableHbox = new HBox();
    tableHbox.setAlignment(Pos.CENTER);

    TableColumn<Expense, String> rightColumn1 = new TableColumn<>("Name: ");
    rightColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Expense, Double> rightColumn2 = new TableColumn<>("Price: ");
    rightColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<Expense, LocalDate> rightColumn3 = new TableColumn<>("Date: ");
    rightColumn3.setCellValueFactory(new PropertyValueFactory<>("date"));

    TableColumn<Expense, String> rightColumn4 = new TableColumn<>("Category: ");
    rightColumn4.setCellValueFactory(new PropertyValueFactory<>("category"));

    TableColumn<Expense, String> rightColumn5 = new TableColumn<>("Account: ");
    rightColumn5.setCellValueFactory(new PropertyValueFactory<>("account"));

    TableView<Expense> bankStatementTable = new TableView<>();
    bankStatementTable.getColumns().addAll(rightColumn1, rightColumn2,
            rightColumn3, rightColumn4, rightColumn5);

    ExcelExporter instance = ExcelExporter.getInstance();

    bankStatementTable.getItems().addAll(instance.getExpensesForMonth());

    bankStatementTable.setMinWidth(600);

    tableHbox.getChildren().add(bankStatementTable);


    bankStatementVbox.getChildren().addAll(viewBankStatement,
            selectAccountHbox, selectCategoryHbox,
            calenderIntervalText, calenderIntervalHbox,
            export, tableHbox);
    bankStatementVbox.setAlignment(Pos.TOP_CENTER);
    return bankStatementVbox;

  }
}