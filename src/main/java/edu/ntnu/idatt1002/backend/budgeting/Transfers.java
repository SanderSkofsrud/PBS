package edu.ntnu.idatt1002.backend.budgeting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a collection of transfers.
 * The collection of transfers is an ArrayList.
 * The class is used for displaying the transfers in the GUI.
 * The class is also used for deleting transfers, depending on the type of the transfer.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.0 - 24.04.2023
 */
public class Transfers {
  /**
   * A list of transfers.
   */
  private List<Transfers> transfersList;
  /**
   * The single instance of the class used in the singleton pattern.
   */
  private static final Transfers instance = new Transfers();
  /**
   * The account name of the account that made/received the transfer.
   */
  private String accountName;
  /**
   * The date of the transfer.
   */
  private String date;
  /**
   * The amount of the transfer.
   */
  private double amount;
  /**
   * The type of the transfer. A for adding to an account, B for transferring between accounts.
   */
  private char transferType;
  /**
   * Dummy variable for initializing the arraylist.
   */
  private String type;

  /**
   * Constructor to initialize an object of the transfers arraylist.
   *
   * @param type the type
   */
  public Transfers(String type) {
    transfersList = new ArrayList<>();
    this.type = type;
  }

  /**
   * Private constructor to avoid multiple instances of the class.
   */
  private Transfers() {
  }

  /**
   * Adds a transfer to the list of transfers.
   *
   * @param accountName  the account name of the account that made/received the transfer.
   * @param amount       the amount of the transfer.
   * @param date         the date of the transfer.
   * @param transferType the type of the transfer. A for adding to an account,
   *                     B for transferring between accounts.
   */
  public Transfers(String accountName, double amount, String date, char transferType) {
    if (accountName.isBlank()) {
      throw new NullPointerException("Account name cannot be blank");
    }
    if (date.isBlank()) {
      throw new NullPointerException("Date cannot be blank");
    }


    this.accountName = accountName;
    this.date = date;
    this.amount = amount;
    this.transferType = transferType;
  }

  /**
   * Returns the single instance of the class.
   *
   * @return the single instance of the class.
   */
  public Transfers getInstance() {
    return instance;
  }

  /**
   * Returns the list of transfers.
   *
   * @return the list of transfers.
   */
  public List<Transfers> transfersList() {
    return transfersList;
  }

  /**
   * Adds a transfer to the list of transfers.
   *
   * @param account      the account name of the account that made/received the transfer.
   * @param amount       the amount of the transfer.
   * @param date         the date of the transfer.
   * @param transferType the type of the transfer. A for adding to an account,
   *                     B for transferring between accounts.
   */
  public void addTransfer(String account, double amount, String date, char transferType) {
    if (account == null || date == null) {
      throw new NullPointerException("Account or date cannot be null");
    }

    if (date.isBlank()) {
      throw new IllegalArgumentException("Date cannot be blank");
    }
    if (account.isBlank()) {
      throw new IllegalArgumentException("Account cannot be blank");
    }
    Transfers newTransfer = new Transfers(account, amount, date, transferType);
    transfersList.add(newTransfer);
  }

  /**
   * Returns the transfer type.
   *
   * @return the transfer type.
   */
  public char getTransferType() {
    return transferType;
  }

  /**
   * Sets the transfer type.
   *
   * @param transferType the transfer type.
   */
  public void setTransferType(char transferType) {
    this.transferType = transferType;
  }

  /**
   * Returns the account name.
   *
   * @return the account name.
   */
  public List<Transfers> getTransfers() {
    return transfersList;
  }

  /**
   * Sets the account name.
   *
   * @param transfers the account name.
   */
  public void setTransfers(List<Transfers> transfers) {
    this.transfersList = transfers;
  }

  /**
   * Returns the account name.
   *
   * @return the account name.
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * Sets the account name.
   *
   * @param accountName the account name.
   */
  public void setAccount(String accountName) {
    this.accountName = accountName;
  }

  /**
   * Returns the date.
   *
   * @return the date.
   */
  public String getDate() {
    return date;
  }

  /**
   * Sets the date.
   *
   * @param date the date.
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * Returns the amount.
   *
   * @return the amount.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount.
   *
   * @param amount the amount.
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * The equals method for the Transfers class.
   *
   * @param o the object to compare to.
   * @return true if the objects are equal, false if they are not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transfers transfers = (Transfers) o;
    return Double.compare(transfers.amount, amount) == 0
            && transferType == transfers.transferType
            && Objects.equals(accountName, transfers.accountName)
            && Objects.equals(date, transfers.date)
            && Objects.equals(type, transfers.type);
  }

  /**
   * The hashCode method for the Transfers class.
   *
   * @return the hashcode of the object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(accountName, date, amount, transferType, type);
  }
}
