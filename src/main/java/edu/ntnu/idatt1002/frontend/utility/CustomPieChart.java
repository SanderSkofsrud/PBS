package edu.ntnu.idatt1002.frontend.utility;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.model.CSVReader;
import edu.ntnu.idatt1002.model.ExcelExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


/**
 * A class that creates the pie chart.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class CustomPieChart {
  /**
   * Creates the data for the left pie chart.
   *
   * @return the list of data
   */
  public static ObservableList<javafx.scene.chart.PieChart.Data> createData() {
    try {
      CSVReader csvReaderInstance = CSVReader.getInstance();
      Accounts accountsInstance = Accounts.getInstance();

      accountsInstance.setAccountsHashmap(csvReaderInstance.readCSV());


    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new IllegalArgumentException("IO Exception");
    }
    Accounts accountsInstance = Accounts.getInstance();
    ObservableList<javafx.scene.chart.PieChart.Data> pieChartData
            = FXCollections.observableArrayList();
    for (Map.Entry<String, Double> entry : accountsInstance.getAccounts().entrySet()) {
      pieChartData.add(new javafx.scene.chart.PieChart.Data(entry.getKey() + ": \n"
              + accountsInstance.getTotalOfAccount(entry.getKey()), entry.getValue()));
    }
    return pieChartData;
  }

  /**
   * Creates the data for the right pie chart.
   *
   * @return the list of data
   */
  public static ObservableList<javafx.scene.chart.PieChart.Data> createData2() {
    ExcelExporter instance = ExcelExporter.getInstance();
    return FXCollections.observableArrayList(
            new javafx.scene.chart.PieChart.Data("Rent: " + "\n"
                    + instance.getTotalOfRent(instance.getExpensesToTable()),
                    instance.getTotalOfRent(instance.getExpensesToTable())),
            new javafx.scene.chart.PieChart.Data("Transportation: " + "\n"
                    + instance.getTotalOfTransportation(instance.getExpensesToTable()),
                    instance.getTotalOfTransportation(instance.getExpensesToTable())),
            new javafx.scene.chart.PieChart.Data("Clothing: " + "\n"
                    + instance.getTotalOfClothing(instance.getExpensesToTable()),
                    instance.getTotalOfClothing(instance.getExpensesToTable())),
            new javafx.scene.chart.PieChart.Data("Entertainment: " + "\n"
                    + instance.getTotalOfEntertainment(instance.getExpensesToTable()),
                    instance.getTotalOfEntertainment(instance.getExpensesToTable())),
            new javafx.scene.chart.PieChart.Data("Food: " + "\n"
                    + instance.getTotalOfFood(instance.getExpensesToTable()),
                    instance.getTotalOfFood(instance.getExpensesToTable())),
            new javafx.scene.chart.PieChart.Data("Other: " + "\n"
                    + instance.getTotalOfOther(instance.getExpensesToTable()),
                    instance.getTotalOfOther(instance.getExpensesToTable())));
  }
}
