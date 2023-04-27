package edu.ntnu.idatt1002.backend.budgeting;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a collection of expenses.
 * The collection of expenses is a HashMap with the expense name as key
 * and the expense amount as value.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class Expenses {
  /**
   * An ArrayList with all the expenses in the transportation category.
   */
  private List<Expense> transportation;

  /**
   * An ArrayList with all the expenses in the entertainment category.
   */
  private List<Expense> entertainment;

  /**
   * An ArrayList with all the expenses in the clothing category.
   */
  private List<Expense> clothing;

  /**
   * An ArrayList with all the expenses in the rent category.
   */
  private List<Expense> rent;

  /**
   * An ArrayList with all the expenses in the other category.
   */
  private List<Expense> other;

  /**
   * An ArrayList with all the expenses in the food category.
   */
  private List<Expense> food;

  /**
   * An ArrayList with all the expenses in all categories.
   */
  private List<Expense> allExpenses;

  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Expenses instance = new Expenses();

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  private Expenses() {
  } // Singleton

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Expenses getInstance() {
    return instance;
  }

  /**
   * Creates the ArrayLists for the transportation category.
   */
  public void createTransportation() {
    transportation = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for the entertainment category.
   */
  public void createEntertainment() {
    entertainment = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for the clothing category.
   */
  public void createClothing() {
    clothing = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for the other category.
   */
  public void createOther() {
    other = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for the food category.
   */
  public void createFood() {
    food = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for the rent category.
   */
  public void createRent() {
    rent = new ArrayList<>();
  }

  /**
   * Creates the ArrayLists for all categories.
   *
   * @return the list
   */
  public List<Expense> createAllExpenses() {
    allExpenses = new ArrayList<>();
    allExpenses.addAll(transportation);
    allExpenses.addAll(entertainment);
    allExpenses.addAll(clothing);
    allExpenses.addAll(other);
    allExpenses.addAll(food);
    allExpenses.addAll(rent);
    return allExpenses;
  }

  /**
   * Creates the adds the arraylists to the arraylist for all arraylists.
   */
  public void createAllAlist() {
    List<List<Expense>> allLists;
    allLists = new ArrayList<>();
    allLists.add(rent);
    allLists.add(transportation);
    allLists.add(clothing);
    allLists.add(other);
    allLists.add(food);
    allLists.add(entertainment);
  }

  /**
   * Adds an expense to the ArrayList.
   *
   * @param expense   the expense to be added.
   * @param arrayList the ArrayList to add the expense to.
   */
  public void addToArrayList(Expense expense, List<Expense> arrayList) {
    if (expense == null) {
      throw new IllegalArgumentException("Expense cannot be null");
    }
    if (arrayList == null) {
      throw new IllegalArgumentException("ArrayList cannot be null");
    }
    arrayList.add(expense);
  }

  /**
   * Gets the total expenses of a category.
   *
   * @param arrayList the ArrayList to get the total expenses from.
   * @return the total expenses of a category.
   */
  public double getTotalExpenses(List<Expense> arrayList) {
    double totalExpense = 0;
    for (Expense expense : arrayList) {
      totalExpense += expense.getPrice();
    }
    return totalExpense;
  }

  /**
   * Gets the total expenses of all categories.
   *
   * @return the total expenses of all categories.
   */
  public double getExpensesOfAllCategories() {
    double totalExpense = 0;
    totalExpense += (getTotalExpenses(transportation) + getTotalExpenses(rent)
            + getTotalExpenses(food) + getTotalExpenses(other)
            + getTotalExpenses(entertainment) + getTotalExpenses(clothing));
    return totalExpense;
  }

  /**
   * Gets the ArrayList with all the expenses in the transportation category.
   *
   * @return the ArrayList with all the expenses in the transportation category.
   */
  public List<Expense> getExpenses() {
    return allExpenses;
  }

  /**
   * Gets transportation.
   *
   * @return the transportation
   */
  public List<Expense> getTransportation() {
    return transportation;
  }

  /**
   * Gets entertainment.
   *
   * @return the entertainment
   */
  public List<Expense> getEntertainment() {
    return entertainment;
  }

  /**
   * Gets clothing.
   *
   * @return the clothing
   */
  public List<Expense> getClothing() {
    return clothing;
  }

  /**
   * Gets rent.
   *
   * @return the rent
   */
  public List<Expense> getRent() {
    return rent;
  }

  /**
   * Gets other.
   *
   * @return the other
   */
  public List<Expense> getOther() {
    return other;
  }

  /**
   * Gets food.
   *
   * @return the food
   */
  public List<Expense> getFood() {
    return food;
  }
}