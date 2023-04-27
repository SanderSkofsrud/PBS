package edu.ntnu.idatt1002.backend.budgeting;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a collection of incomes.
 * The collection of incomes is an ArrayList with the income name as key
 * and the income amount as value.
 * Uses singleton pattern to avoid multiple instances of the class,
 * and to ensure data encapsulation and integrity.
 * The class also has methods for creating an ArrayList of all the incomes,
 * and for adding an income to an ArrayList of incomes.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class Incomes {
  /**
   * An ArrayList consisting of incomes.
   */
  private List<Income> incomesList;

  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Incomes instance = new Incomes();

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  private Incomes() {
  }

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Incomes getInstance() {
    return instance;
  }

  /**
   * Creates an ArrayList of incomes.
   */
  public void createIncomes() {
    incomesList = new ArrayList<>();
  }

  /**
   * Returns the ArrayList of incomes.
   *
   * @return the ArrayList of incomes.
   */
  public List<Income> getIncomes() {
    return incomesList;
  }

  /**
   * Creates an ArrayList of all the incomes.
   *
   * @return an ArrayList of all the incomes.
   */
  public List<Income> createAllIncomes() {
    List<Income> allIncomes;
    allIncomes = new ArrayList<>();
    allIncomes.addAll(incomesList);
    return allIncomes;
  }

  /**
   * Adds an income to an ArrayList of incomes.
   *
   * @param income    the income to be added.
   * @param arrayList the ArrayList to which the income is to be added.
   */
  public void addToArrayList(Income income, List<Income> arrayList) {
    if (income == null) {
      throw new NullPointerException("Income cannot be null");
    }
    if (arrayList == null) {
      throw new NullPointerException("List cannot be null");
    }
    arrayList.add(income);
  }
}