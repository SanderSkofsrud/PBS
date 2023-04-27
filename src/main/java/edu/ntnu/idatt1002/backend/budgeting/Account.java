package edu.ntnu.idatt1002.backend.budgeting;

/**
 * A class that represents an account.
 * An account has a name and a balance.
 * Singleton is used to ensure data encapsulation and integrity.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class Account {
  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Account instance = new Account();
  /**
   * The name of the account.
   */
  private String accountName;
  /**
   * The balance of the account.
   */
  private double accountBalance;

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  private Account() {
  } // Singleton

  /**
   * Constructor for the class.
   *
   * @param accountName    the name of the account.
   * @param accountBalance the balance of the account.
   * @throws NullPointerException     if the account name is null or empty.
   * @throws IllegalArgumentException if the account balance is negative.
   */
  public Account(String accountName, double accountBalance) {
    if (accountName == null || accountName.isBlank()) {
      throw new NullPointerException("Account name cannot be empty");
    }
    if (accountBalance < 0) {
      throw new IllegalArgumentException("Account balance cannot be negative");
    }
    this.accountName = accountName;
    this.accountBalance = accountBalance;
  }

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Account getInstance() {
    return instance;
  }

  /**
   * Gets the account name of the account.
   *
   * @return the account name of the account.
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * Sets the account name of the account.
   *
   * @param accountName the new account name.
   * @throws NullPointerException if the account name is null or empty.
   */
  public void setAccountName(String accountName) {
    if (accountName == null || accountName.isBlank()) {
      throw new NullPointerException("Account name cannot be empty");
    }
    this.accountName = accountName;
  }

  /**
   * Gets the account balance of the account.
   *
   * @return the account balance of the account.
   */
  public double getAccountBalance() {
    return accountBalance;
  }

  /**
   * Sets the account balance of the account.
   *
   * @param accountBalance the new account balance.
   * @throws IllegalArgumentException if the account balance is negative.
   */
  public void setAccountBalance(double accountBalance) {
    if (accountBalance < 0) {
      throw new IllegalArgumentException("Account balance cannot be negative");
    }
    this.accountBalance = accountBalance;
  }
}
