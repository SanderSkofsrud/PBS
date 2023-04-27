package edu.ntnu.idatt1002.backend.budgeting;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class that represents an expense.
 * An expense has a name, a price, a category and a date.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.0 - 26.04.2023
 */
public class Expense {
  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Expense instance = new Expense();
  /**
   * The uniqueID of the expense, used for creating the bank statement,
   * where uniqueID is the month and category.
   */
  private String uniqueId;
  /**
   * The name of the expense.
   */
  private String name;
  /**
   * The price of the expense.
   */
  private double price;
  /**
   * The category of the expense.
   */
  private int category;
  /**
   * The category of the expense as a string.
   */
  private String categoryAsString;
  /**
   * The account of the expense as a string.
   */
  private String accountAsString;
  /**
   * The date of the expense.
   */
  private LocalDate date;

  /**
   * The exception message for when the name is null or empty.
   */
  private static final String NAME_CANNOT_BE_NULL = "Name cannot be null or empty";
  /**
   * The exception message for when the price is negative.
   */
  private static final String PRICE_CANNOT_BE_NEGATIVE = "Price cannot be negative";
  /**
   * The exception message for when the category is not between 1 and 6.
   */
  private static final String CATEGORY_MUST_BE_BETWEEN_1_AND_6 = "Category must be between 1 and 6";
  /**
   * The exception message for when the date is null.
   */
  private static final String DATE_CANNOT_BE_NULL = "Date cannot be null";
  /**
   * The exception message for when the category is null.
   */
  private static final String CATEGORY_CANNOT_BE_NULL = "Category cannot be null";
  /**
   * The exception message for when the uniqueID is null.
   */
  private static final String UNIQUEID_CANNOT_BE_NULL = "UniqueID cannot be null";

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  public Expense() {
  } // Singleton

  /**
   * Constructor for the class.
   *
   * @param name     the name of the expense.
   * @param price    the price of the expense.
   * @param category the category of the expense.
   * @param date     the date of the expense.
   * @throws NullPointerException     if the name is null or empty.
   * @throws IllegalArgumentException if the price is negative or the category is
   *                                  not between 1 and 6.
   */
  public Expense(String name, double price, int category, LocalDate date) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_NULL);
    }
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    if (category < 1 || category > 6) {
      throw new IllegalArgumentException(CATEGORY_MUST_BE_BETWEEN_1_AND_6);
    }
    if (date == null) {
      throw new IllegalArgumentException(DATE_CANNOT_BE_NULL);
    }

    this.name = name;
    this.price = price;
    this.category = category;
    this.date = date;
  }

  /**
   * Constructor for the class.
   *
   * @param name     the name of the expense.
   * @param price    the price of the expense.
   * @param date     the date of the expense.
   * @param category the category of the expense.
   * @param account  the account
   * @throws NullPointerException     if the name is null or empty.
   * @throws IllegalArgumentException if the price is negative or the category is
   *                                  not between 1 and 6.
   */
  public Expense(String name, Double price, LocalDate date, String category, String account) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_NULL);
    }
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    }
    if (category == null || category.isBlank()) {
      throw new NullPointerException();
    }
    this.name = name;
    this.price = price;
    this.date = date;
    this.categoryAsString = category;
    this.accountAsString = account;
  }

  /**
   * Constructor for the class.
   *
   * @param name     the name of the expense.
   * @param price    the price of the expense.
   * @param date     the date of the expense.
   * @param category the category of the expense.
   * @param account  the account
   * @param uniqueId the uniqueID of the expense.
   * @throws NullPointerException     if the name is null or empty.
   * @throws IllegalArgumentException if the price is negative or the category is
   *                                  not between 1 and 6.
   */
  public Expense(String name, Double price, LocalDate date, String category,
                 String account, String uniqueId) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_NULL);
    }
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    }
    if (category == null || category.isBlank()) {
      throw new NullPointerException(CATEGORY_CANNOT_BE_NULL);
    }
    if (uniqueId == null || uniqueId.isBlank()) {
      throw new NullPointerException(UNIQUEID_CANNOT_BE_NULL);
    }

    this.name = name;
    this.price = price;
    this.date = date;
    this.categoryAsString = category;
    this.accountAsString = account;
    this.uniqueId = uniqueId;
  }

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Expense getInstance() {
    return instance;
  }

  /**
   * Returns the name of the expense.
   *
   * @return the name of the expense.
   */
  public String getName() {
    return name.replace("\"", "");
  }

  /**
   * Sets the name of the expense.
   *
   * @param name the new name of the expense.
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_NULL);
    }
    this.name = name;
  }

  /**
   * Returns the price of the expense.
   *
   * @return the price of the expense.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the expense.
   *
   * @param price the new price of the expense.
   */
  public void setPrice(double price) {
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    this.price = price;
  }

  /**
   * Returns the date of the expense.
   *
   * @return the date of the expense.
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets the date of the expense.
   *
   * @param date the new date of the expense.
   */
  public void setDate(LocalDate date) {
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_NULL);
    }
    this.date = date;
  }

  /**
   * Returns the category of the expense.
   *
   * @return the category of the expense.
   */
  public int getCategoryInt() {
    return category;
  }

  /**
   * Sets the category of the expense.
   *
   * @param category the new category of the expense.
   */
  public void setCategoryInt(int category) {
    if (category < 1 || category > 6) {
      throw new IllegalArgumentException(CATEGORY_MUST_BE_BETWEEN_1_AND_6);
    }
    this.category = category;
  }

  /**
   * Returns the category of the expense as a string.
   *
   * @return the category of the expense as a string.
   */
  public String getCategory() {
    return categoryAsString;
  }

  /**
   * Sets the category as a string of the expense.
   *
   * @param category the new category as string of the expense.
   */
  public void setCategoryAsString(String category) {
    if (category == null || category.isBlank()) {
      throw new NullPointerException("Category cannot be null or empty");
    }
    this.categoryAsString = category;
  }

  /**
   * Returns the account of the expense.
   *
   * @return the account of the expense.
   */
  public String getAccount() {
    return accountAsString;
  }

  /**
   * Gets the uniqueID of the expense.
   *
   * @return the uniqueID of the expense.
   */
  public String getUniqueId() {
    return uniqueId;
  }

  /**
   * Sets the uniqueID of the expense.
   *
   * @param uniqueId the new uniqueID of the expense.
   */
  public void setUniqueId(String uniqueId) {
    if (uniqueId == null || uniqueId.isBlank()) {
      throw new IllegalArgumentException("UniqueID cannot be null or empty");
    }
    this.uniqueId = uniqueId;
  }


  /**
   * Sets the account as string of the expense.
   *
   * @param accountAsString the new account as string of the expense.
   */
  public void setAccountAsString(String accountAsString) {
    this.accountAsString = accountAsString;
  }

  /**
   * Validate category boolean.
   *
   * @param categoryAsString the category as string
   * @return the boolean
   */
  public boolean validateCategory(String categoryAsString) {
    String[] categories = {"Food", "Transportation", "Entertainment", "Clothing", "Other", "Rent"};
    if (categoryAsString == null || categoryAsString.isBlank()) {
      return false;
    } else {
      return Arrays.asList(categories).contains(categoryAsString);
    }
  }

  /**
   * The equals method for Expense.
   * Two expenses are equal if they have the same name, price, category, account and date.
   *
   * @param o the object to compare to
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Expense expense = (Expense) o;
    return Double.compare(expense.price, price) == 0
            && Objects.equals(name, expense.name)
            && Objects.equals(categoryAsString, expense.categoryAsString)
            && Objects.equals(accountAsString, expense.accountAsString)
            && Objects.equals(date, expense.date);
  }

  /**
   * The hashCode method for Expense.
   *
   * @return the hashCode of the expense.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, price, categoryAsString, accountAsString, date);
  }
}

