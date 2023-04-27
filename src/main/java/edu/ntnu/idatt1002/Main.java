package edu.ntnu.idatt1002;

import edu.ntnu.idatt1002.backend.budgeting.Expenses;
import edu.ntnu.idatt1002.backend.budgeting.Incomes;
import edu.ntnu.idatt1002.frontend.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A class that starts the GUI.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 19.04.2023
 */
public class Main extends Application {

  /**
   * The entry point of the application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    Expenses expenseInstance = Expenses.getInstance();
    Incomes incomeInstance = Incomes.getInstance();

    expenseInstance.createTransportation();
    expenseInstance.createRent();
    expenseInstance.createEntertainment();
    expenseInstance.createClothing();
    expenseInstance.createOther();
    expenseInstance.createFood();
    incomeInstance.createIncomes();

    expenseInstance.createAllAlist();
    expenseInstance.createAllExpenses();
    incomeInstance.createAllIncomes();

    launch(args);
  }

  /**
   * A method that starts the GUI.
   *
   * @param stage the primary stage for this application, onto which
   *              the application scene can be set.
   *              Applications may create other stages, if needed, but they will not be
   *              primary stages.
   * @throws Exception the exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    GUI gui = new GUI();
    gui.start(stage);
  }

}