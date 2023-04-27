package edu.ntnu.idatt1002.backend.budgeting;

import java.time.LocalDate;

/**
 * A class that represents an income.
 * An income has a name, a price, a category and a date.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.3 - 27.04.2023
 */
public class Income {
  /**
   * The name of the income.
   */
  private String name;
  /**
   * The price of the income.
   */
  private double price;
  /**
   * The category of the income.
   */
  private int category;
  /**
   * The date of the income.
   */
  private LocalDate date;

  /*
   * Exception message for when the name is empty.
   */
  private static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty";
  /*
   * Exception message for when the category is not between 1 and 6.
   */
  private static final String CATEGORY_MUST_BE_BETWEEN_1_AND_6 = "Category must be between 1 and 6";
  /*
   * Exception message for when the date is empty.
   */
  private static final String DATE_CANNOT_BE_EMPTY = "Date cannot be empty";
  /*
   * Exception message for when the price is negative.
   */
  private static final String PRICE_CANNOT_BE_NEGATIVE = "Price cannot be negative";

  /**
   * Constructor for the Income class.
   *
   * @param name     The name of the income.
   * @param price    The price of the income.
   * @param category The category of the income.
   * @param date     The date of the income.
   */
  public Income(String name, double price, int category, LocalDate date) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_EMPTY);
    }
    if (price < 0) {
      throw new IllegalArgumentException();
    }
    if (category < 1 || category > 6) {
      throw new IllegalArgumentException(CATEGORY_MUST_BE_BETWEEN_1_AND_6);
    }
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_EMPTY);
    }

    this.name = name;
    this.price = price;
    this.category = category;
    this.date = date;
  }

  /**
   * Private constructor to for barchart.
   */
  private Income() {
  }

  /**
   * Constructor for the Income class.
   *
   * @param name  the name of the income.
   * @param price the price of the income.
   * @param date  the date of the income.
   */
  public Income(String name, double price, LocalDate date) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_EMPTY);
    }
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_EMPTY);
    }
    this.name = name;
    this.price = price;
    this.date = date;
  }

  /**
   * Returns the name of the income.
   *
   * @return the name of the income.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the income.
   *
   * @param name the name of the income.
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new NullPointerException(NAME_CANNOT_BE_EMPTY);
    }
    this.name = name;
  }

  /**
   * Returns the price of the income.
   *
   * @return the price of the income.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the income.
   *
   * @param price the price of the income.
   */
  public void setPrice(double price) {
    if (price < 0) {
      throw new IllegalArgumentException(PRICE_CANNOT_BE_NEGATIVE);
    }
    this.price = price;
  }

  /**
   * Returns the category of the income.
   *
   * @return the category of the income.
   */
  public int getCategory() {
    return category;
  }

  /**
   * Sets the category of the income.
   *
   * @param category the category of the income.
   */
  public void setCategory(int category) {
    if (category < 1 || category > 6) {
      throw new IllegalArgumentException(CATEGORY_MUST_BE_BETWEEN_1_AND_6);
    }
    this.category = category;
  }

  /**
   * Returns the date of the income.
   *
   * @return the date of the income.
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * Sets the date of the income.
   *
   * @param date the date of the income.
   */
  public void setDate(LocalDate date) {
    if (date == null) {
      throw new NullPointerException(DATE_CANNOT_BE_EMPTY);
    }
    this.date = date;
  }
}
