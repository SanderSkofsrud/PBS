package edu.ntnu.idatt1002.model;

import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Transfers;
import edu.ntnu.idatt1002.frontend.GUI;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

/**
 * A class that reads a csv file.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.1 - 26.04.2023
 */
public class CSVReader {
  /**
   * The delimiter used in the csv file.
   */
  private static final String CVS_SPLIT_BY = ",";
  /**
   * The path to the csv file that stores all the information of a user.
   */
  private static final String USERFILES_PATH = "src/main/resources/userfiles/";
  /**
   * The name of the csv file that stores all the transfers of a user.
   */
  private static final String TRANSFER_CSV = "transfer.csv";
  /**
   * The path to the csv file that stores all the transfers of a user.
   */
  private String csvTransferFilePath;
  /**
   * The path to the csv file.
   */
  private String csvFilePath;
  /**
   * The path to the output directory.
   */
  private String outPutDirectory;

  /**
   * The instance of the CSVReader class.
   */
  private static final CSVReader instance = new CSVReader();

  /**
   * Instantiates a new Csv reader.
   */
  private CSVReader() {
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static CSVReader getInstance() {
    return instance;
  }

  /**
   * A method that reads a csv file.
   *
   * @return a map of expenses
   * @throws IOException the io exception
   */
  public Map<String, Double> readCSV() throws IOException {
    HashMap<String, Double> newAccounts = new HashMap<>(); // Create a new instance of hashmap
    outPutDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    csvTransferFilePath = outPutDirectory + GUI.getCurrentUser() + TRANSFER_CSV;
    csvFilePath = outPutDirectory + GUI.getCurrentUser() + ".csv";
    File csvTransferFile = new File(csvTransferFilePath);
    File csvFile = new File(csvFilePath);
    if (!csvTransferFile.exists() && !csvFile.exists()) {
      try {
        File outPutDirectoryFile = new File(outPutDirectory);
        outPutDirectoryFile.mkdirs();
        Files.createFile(csvTransferFile.toPath());
        Files.createFile(csvFile.toPath());
      } catch (IOException e) {
        throw new IllegalArgumentException(e);
      }
      return newAccounts;
    }

    if (!csvTransferFile.exists()) {
      try {
        Files.createFile(csvTransferFile.toPath());
      } catch (IOException e) {
        throw new IllegalArgumentException(e);
      }
      return newAccounts;
    }

    if (!csvFile.exists()) {
      try {
        Files.createFile(csvFile.toPath());
      } catch (IOException e) {
        throw new IllegalArgumentException(e);
      }
    }

    try (BufferedReader br1 = new BufferedReader(
            new FileReader(csvTransferFile));
         BufferedReader br2 = new BufferedReader(new FileReader(csvFile))) {
      String line;
      while ((line = br1.readLine()) != null) {
        String[] account = line.split(CVS_SPLIT_BY);
        if (newAccounts.containsKey(account[0])) {
          newAccounts.put(account[0], newAccounts.get(account[0]) + Double.parseDouble(account[1]));
        } else {
          newAccounts.put(account[0], Double.parseDouble(account[1]));
        }
      }
      while ((line = br2.readLine()) != null) {
        String[] account = line.split(CVS_SPLIT_BY);
        for (Map.Entry<String, Double> entry : newAccounts.entrySet()) {
          String key = entry.getKey();
          Double value = entry.getValue();
          if (key.equals(account[4])) {
            entry.setValue(value - Double.parseDouble(account[3]));
          }
        }
      }
      return newAccounts;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * A method that reads a csv file and returns a list of transfers.
   *
   * @return a list of transfers
   * @throws IOException the io exception
   */
  public List<Transfers> listOfTransfers() throws IOException {
    outPutDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    csvTransferFilePath = outPutDirectory + GUI.getCurrentUser() + TRANSFER_CSV;
    Transfers transfers = new Transfers("listConstructor");
    File csvTransferFile = new File(csvTransferFilePath);
    if (!csvTransferFile.exists()) {
      try {
        Files.createFile(csvTransferFile.toPath());
      } catch (IOException e) {
        throw new IllegalArgumentException(e);
      }
    }
    try (BufferedReader br1 = new BufferedReader(new FileReader(csvTransferFile))) {
      String line;
      while ((line = br1.readLine()) != null) {
        String[] account = line.split(CVS_SPLIT_BY);
        if (account.length >= 4) {
          char transferType = account[3].charAt(0);
          transfers.addTransfer(account[0],
                  Double.parseDouble(account[1]), account[2], transferType);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Error with CSV file for transfer");
    }

    return transfers.transfersList();
  }

  /**
   * A method that reads a csv file and returns a list of expenses.
   *
   * @return a list of expenses
   */
  public List<Expense> getExpensesFromCSV() {
    outPutDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    csvFilePath = outPutDirectory + GUI.getCurrentUser() + ".csv";
    List<Expense> expensesFromFile = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
      String line;
      while ((line = reader.readLine()) != null) {

        List<String> columnsList = new ArrayList<>();
        boolean insideQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (char c : line.toCharArray()) {
          if (c == '|') {
            insideQuotes = !insideQuotes;
          } else if (c == ',' && !insideQuotes) {
            columnsList.add(sb.toString());
            sb = new StringBuilder();
          } else {
            sb.append(c);
          }
        }

        columnsList.add(sb.toString());

        String[] columns = columnsList.toArray(new String[0]);

        for (int i = 0; i < columns.length; i++) {
          if (columns[i].startsWith("|") && columns[i].endsWith("|")) {
            columns[i] = columns[i].substring(1, columns[i].length() - 1);
          }
          columns[i] = columns[i].replaceAll("^\"|\"$", "");
        }

        String category = columns[0];
        String name = columns[1];
        String date = columns[2];
        String price = columns[3];
        String accountName = columns[4];
        Expense expense = new Expense(name,
                Double.parseDouble(price),
                LocalDate.parse(date),
                category, accountName);
        expensesFromFile.add(expense);
      }
      return expensesFromFile;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * A method that updates the rows that are different in the table.
   *
   * @param expensesInTable  the expenses in table
   * @param expensesFromFile the expenses from file
   */
  public void updateRowsThatAreDifferentInTable(
          List<Expense> expensesInTable, List<Expense> expensesFromFile) {
    outPutDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    csvFilePath = outPutDirectory + GUI.getCurrentUser() + ".csv";
    List<Expense> expensesToBeUpdated = new ArrayList<>();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
      for (Expense expenseFromFile : expensesFromFile) {
        if ((expenseFromFile.getDate().getMonthValue() != LocalDate.now().getMonthValue())) {
          expensesToBeUpdated.add(expenseFromFile);
        }
      }
      expensesToBeUpdated.addAll(expensesInTable);
      for (Expense expenseToUpdatedFile : expensesToBeUpdated) {
        writer.write(expenseToUpdatedFile.getCategory()
                + ",|" + expenseToUpdatedFile.getName()
                + "|," + expenseToUpdatedFile.getDate()
                + "," + expenseToUpdatedFile.getPrice()
                + "," + expenseToUpdatedFile.getAccount());
        writer.newLine();
      }
      writer.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * A method that removes a transfer from the csv file.
   *
   * @param transfersListInTable the transfers list in table
   */
  public void removeTransfer(List<Transfers> transfersListInTable) {
    outPutDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    csvTransferFilePath = outPutDirectory + GUI.getCurrentUser() + TRANSFER_CSV;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvTransferFilePath))) {
      for (Transfers transfer : transfersListInTable) {
        writer.write(String.format(Locale.US,
                "%s,%.2f,%s,%c%n", transfer.getAccountName(),
                transfer.getAmount(), transfer.getDate(),
                transfer.getTransferType()));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
