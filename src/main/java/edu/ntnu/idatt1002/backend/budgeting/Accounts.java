package edu.ntnu.idatt1002.backend.budgeting;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a collection of accounts.
 * The collection of accounts is a HashMap with the account name as key
 * and the account balance as value.
 * Uses singleton pattern to avoid multiple instances of the class,
 * and to ensure data encapsulation and integrity.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class Accounts {
  /**
   * A hashmap with all the accounts.
   */
  private HashMap<String, Double> accountsHashmap;
  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Accounts instance = new Accounts();

  /**
   * Private constructor to avoid multiple instances of the class. Creates the hashmap to be used.
   */
  private Accounts() {
    accountsHashmap = new HashMap<>();
  }

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public static Accounts getInstance() {
    return instance;
  }

  /**
   * Adds an account to the hashmap.
   *
   * @param accountName    the name of the account to be added.
   * @param accountBalance the balance of the account to be added.
   * @throws NullPointerException     if the account name is null or empty.
   * @throws IllegalArgumentException if the account balance is negative.
   */
  public void addAccount(String accountName, double accountBalance) {
    if (accountName == null || accountName.isBlank()) {
      throw new NullPointerException("Account name cannot be empty");
    }
    if (accountBalance < 0) {
      throw new IllegalArgumentException("Account balance cannot be negative");
    }

    Account newAccount = new Account(accountName, accountBalance);
    accountsHashmap.put(newAccount.getAccountName(), newAccount.getAccountBalance());
  }

  /**
   * Adds an amount to a given account.
   *
   * @param accountName the name of the account to be removed.
   * @param amount      the amount to be added to the account.
   * @throws NullPointerException     if the account name is null or empty.
   * @throws IllegalArgumentException if the amount is negative.
   */
  public void addToAccount(String accountName, double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    if (accountName == null || accountName.isBlank()) {
      throw new NullPointerException("Account name cannot be empty");
    }

    accountsHashmap.put(accountName, accountsHashmap.get(accountName) + amount);
  }

  /**
   * Gets the total balance of an account.
   *
   * @param accountName the name of the account you want to get the balance of.
   * @return the balance of the account.
   */
  public double getTotalOfAccount(String accountName) {
    return accountsHashmap.get(accountName);
  }

  /**
   * Gets the total balance of all accounts.
   *
   * @return the total balance of all accounts.
   */
  public double getTotalOfAllAccounts() {
    double total = 0;
    for (Double value : accountsHashmap.values()) {
      total += value;
    }
    return total;
  }

  /**
   * Gets the hashmap of accounts.
   *
   * @return the hashmap of accounts.
   */
  public Map<String, Double> getAccounts() {
    return accountsHashmap;
  }

  /**
   * Validate account name boolean.
   *
   * @param accountName the account name
   * @return the boolean
   */
  public boolean validateAccountName(String accountName) {
    return accountsHashmap.containsKey(accountName);
  }

  /**
   * Sets accounts hashmap.
   *
   * @param accountsHashmap the accounts hashmap
   */
  public void setAccountsHashmap(Map<String, Double> accountsHashmap) {
    this.accountsHashmap = (HashMap<String, Double>) accountsHashmap;
  }
}
