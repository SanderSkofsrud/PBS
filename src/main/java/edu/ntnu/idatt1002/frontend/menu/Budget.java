package edu.ntnu.idatt1002.frontend.menu;

import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import edu.ntnu.idatt1002.frontend.utility.TimeOfDayChecker;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static edu.ntnu.idatt1002.frontend.utility.AlertWindow.showAlert;

/**
 * A class that creates the budget view.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.3 - 19.04.2023
 */
public class Budget {

  /**
   * A method that creates the budget view.
   * The method is used by the GUI class.
   *
   * @return the vertical box
   */
  public static VBox budgetView() {

    VBox budgetLayout = new VBox();
    budgetLayout.setAlignment(Pos.CENTER_LEFT);
    budgetLayout.setSpacing(20);

    Text editMonthBudget = new Text("Edit this months budget");
    editMonthBudget.setId("titleText");


    HBox categorySelectorHbox = new HBox();


    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Rent",
                    "Food",
                    "Transportation",
                    "Clothing",
                    "Entertainment",
                    "Other"
            );


    final ComboBox<String> categoryMenu = new ComboBox<>(options);
    categoryMenu.setPromptText("Select category");
    categoryMenu.setId("categoryMenuButton");

    categorySelectorHbox.getChildren().addAll(categoryMenu);
    categorySelectorHbox.setAlignment(Pos.CENTER);
    categorySelectorHbox.setSpacing(20);


    HBox budgetAmountHbox = new HBox();

    TextField budgetAmountField = new TextField();
    budgetAmountField.setPromptText("Set budget for next month: ");
    budgetAmountField.setId("textField");


    Button confirmAmount = new Button("Confirm");
    confirmAmount.setId("actionButton");

    confirmAmount.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmAmount.fire();
      }
    });

    budgetAmountField.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        confirmAmount.fire();
      }
    });

    confirmAmount.setOnAction(e -> {
        if (categoryMenu.getValue() == null) {
          playErrorSound();
          showAlert("Please select a category");
          throw new NullPointerException("Please select a category");
        } else if (budgetAmountField.getText().isEmpty()) {
          playErrorSound();
          showAlert("Please enter a budget amount");
          throw new NullPointerException("Please enter a budget amount");
        } else if (Integer.parseInt(budgetAmountField.getText()) < 0) {
          playErrorSound();
          showAlert("Please enter a positive number");
          throw new NumberFormatException("Please enter a positive number");
        }
        String category = categoryMenu.getValue();
        String amount = budgetAmountField.getText();
        String month = TimeOfDayChecker.getCurrentMonth();

        String categorymonth = category + month;

        File csvFile = new File(ExcelExporter.getBudgetPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
          ArrayList<String> lines = new ArrayList<>();

          String line;
          while ((line = reader.readLine()) != null) {
            String oldCategoryMonth = line.split(",")[0] + line.split(",")[2];
            if (oldCategoryMonth.equals(categorymonth)) {
              continue;
            }
            lines.add(line);
          }

          reader.close();

          File tempFile = new File(ExcelExporter.getTempBudgetPath());
          FileWriter fw = new FileWriter(tempFile);
          BufferedWriter bw = new BufferedWriter(fw);
          for (String l : lines) {
            bw.write(l);
            bw.newLine();
          }

          bw.write(category + "," + amount + "," + month);
          bw.newLine();
          bw.flush();

          bw.close();
          fw.close();

          Files.delete(Path.of(ExcelExporter.getBudgetPath()));
          if (tempFile.renameTo(csvFile)){
            Logger.getLogger(Budget.class.getName()).info("File renamed successfully.");
          } else {
            Logger.getLogger(Budget.class.getName()).info("File rename failed.");
          }

        } catch (IOException f) {
          Logger.getLogger(Budget.class.getName()).info("File not found.");
        }

        String currentMonth = TimeOfDayChecker.getCurrentMonth();
        String previousMonth = TimeOfDayChecker.getPreviousMonth();

        // Read the CSV file
        File file = new File(ExcelExporter.getBudgetPath());
        if (!file.exists()) {
          try {
            if (file.createNewFile()) {
              Logger.getLogger(Budget.class.getName()).info("File created successfully.");
            }
          } catch (IOException f) {
            f.printStackTrace();
          }
        }
        String csvSplitBy = ",";
        List<String[]> currentLines = new ArrayList<>();
        List<String[]> previousLines = new ArrayList<>();
        BarChart<String, Number> barChart = null;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
          String line;
          while ((line = br.readLine()) != null) {
            String[] data = line.split(csvSplitBy);
            if (data.length >= 3 && data[2].equalsIgnoreCase(currentMonth)) {
              currentLines.add(data);
            }
            if (data.length >= 3 && data[2].equalsIgnoreCase(previousMonth)) {
              previousLines.add(data);
            }
          }

          ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
          for (String[] lineData : currentLines) {
            currentData.add(new XYChart.Data<>(lineData[0], Double.parseDouble(lineData[1])));
          }
          XYChart.Series<String, Number> currentSeries = new XYChart.Series<>(currentData);
          currentSeries.setName(currentMonth);

          ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
          for (String[] lineData : previousLines) {
            previousData.add(new XYChart.Data<>(lineData[0], Double.parseDouble(lineData[1])));
          }
          XYChart.Series<String, Number> previousSeries = new XYChart.Series<>(previousData);
          previousSeries.setName(previousMonth);

          CategoryAxis xaxis = new CategoryAxis();
          xaxis.setLabel("Category");
          NumberAxis yaxis = new NumberAxis();
          yaxis.setLabel("Value");
          barChart = new BarChart<>(xaxis, yaxis);
          barChart.setTitle("Bar Chart");
          barChart.getData().addAll(currentSeries, previousSeries);
          budgetLayout.getChildren().clear();
          budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetAmountHbox, barChart);

        } catch (IOException exception) {
          exception.printStackTrace();
        }

        categoryMenu.setValue("Select Category");
        GUI.setPaneToUpdate("overview");
        GUI.setPaneToUpdate("budget");
        GUI.updatePane();
        budgetAmountField.clear();
        SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));

        GUI.setPaneToUpdate("overviewView");
        GUI.updatePane();
    });

    String currentMonth = TimeOfDayChecker.getCurrentMonth();
    String previousMonth = TimeOfDayChecker.getPreviousMonth();

    String csvFile = ("src/main/resources/userfiles/" + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "budget.csv");
    File file = new File(csvFile);
    if (!file.exists()) {
      try {
        if (file.createNewFile()) {
          Logger.getLogger(Budget.class.getName()).info("File created successfully.");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    String line = "";
    String csvSplitBy = ",";
    List<String[]> currentLines = new ArrayList<>();
    List<String[]> previousLines = new ArrayList<>();
    BarChart<String, Number> barChart = null;
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      while ((line = br.readLine()) != null) {
        String[] data = line.split(csvSplitBy);
        if (data.length >= 3 && data[2].equalsIgnoreCase(currentMonth)) {
          currentLines.add(data);
        }
        if (data.length >= 3 && data[2].equalsIgnoreCase(previousMonth)) {
          previousLines.add(data);
        }
      }

      ObservableList<XYChart.Data<String, Number>> currentData = FXCollections.observableArrayList();
      for (String[] lineData : currentLines) {
        currentData.add(new XYChart.Data<>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> currentSeries = new XYChart.Series<>(currentData);
      currentSeries.setName(currentMonth);

      ObservableList<XYChart.Data<String, Number>> previousData = FXCollections.observableArrayList();
      for (String[] lineData : previousLines) {
        previousData.add(new XYChart.Data<>(lineData[0], Double.parseDouble(lineData[1])));
      }
      XYChart.Series<String, Number> previousSeries = new XYChart.Series<>(previousData);
      previousSeries.setName(previousMonth);

      CategoryAxis xaxis = new CategoryAxis();
      xaxis.setLabel("Category");
      NumberAxis yaxis = new NumberAxis();
      yaxis.setLabel("Value");
      barChart = new BarChart<>(xaxis, yaxis);
      barChart.setTitle("Bar Chart");
      barChart.getData().addAll(currentSeries, previousSeries);
      if (Objects.equals(GUI.getStylesheet(), "Darkmode")) {
        applyDarkModeStylesToBudget(barChart);
      }

    } catch (IOException f) {
      f.printStackTrace();
    }


    budgetAmountHbox.getChildren().addAll(budgetAmountField, confirmAmount);
    budgetAmountHbox.setAlignment(Pos.CENTER);
    budgetAmountHbox.setSpacing(20);

    budgetLayout.getChildren().addAll(editMonthBudget, categorySelectorHbox, budgetAmountHbox, barChart);
    budgetLayout.setAlignment(Pos.TOP_CENTER);

    currentBudget = currentLines;
    return budgetLayout;
  }

  /*
    * Applies dark mode styles to bar chart
   */
  private static void applyDarkModeStylesToBudget(BarChart<String, Number> barChart) {
    barChart.setStyle("-fx-background-color: transparent;");

    CategoryAxis xaxis = (CategoryAxis) barChart.getXAxis();
    xaxis.setStyle("-fx-tick-label-fill: #FFFFFF;");

    NumberAxis yaxis = (NumberAxis) barChart.getYAxis();
    yaxis.setStyle("-fx-tick-label-fill: #FFFFFF;");

    barChart.lookupAll(".chart-legend-item-text").forEach(node ->
      node.setStyle("-fx-text-fill: #FFFFFF;"));

    barChart.lookupAll(".series0 .chart-bar").forEach(node ->
      node.setStyle("-fx-bar-fill: #FF0000;"));

    barChart.lookupAll(".series1 .chart-bar").forEach(node ->
      node.setStyle("-fx-bar-fill: #FFFF00;"));

    barChart.lookupAll(".series0 .chart-legend-symbol").forEach(node ->
      node.setStyle("-fx-background-color: #FF0000, white;"));
    barChart.lookupAll(".series1 .chart-legend-symbol").forEach(node ->
      node.setStyle("-fx-background-color: #FFFF00, white;"));
  }

  /*
    * This method is used to play the error sound.
   */
  public static void playErrorSound(){
    SoundPlayer.play(FileUtil.getResourceFilePath("error.wav"));
  }
  /**
   * A list that contains the current budget.
   */
  protected static List<String[]> currentBudget = new ArrayList<>();

  /**
   * A method that returns the current budget.
   * @return currentBudget
   */
  public static List<String[]> getCurrentBudget() {
    return currentBudget;
  }
}
