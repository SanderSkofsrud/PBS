package edu.ntnu.idatt1002.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.frontend.GUI;
import edu.ntnu.idatt1002.frontend.utility.TimeOfDayChecker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that exports a csv file to excel.
 *
 * @author Emil J., Vegard J., Sander S. and Elias T.
 * @version 1.2 - 26.04.2023
 */
public class ExcelExporter {
  /*
   * The suffixes of an Excel file.
   */
  private static final String EXCEL_SUFFIX = ".xlsx";
  /**
   * The suffixes of a csv file.
   */
  private static final String CSV_SUFFIX = ".csv";
  /**
   * The path to the csv file that stores all the information of a user.
   */
  private static final String USERFILES_PATH = "src/main/resources/userfiles/";

  /**
   * The constant expensesToTable.
   */
  private List<Expense> expensesToTable = new ArrayList<>();
  /**
   * The constant instance.
   */
  public static final ExcelExporter instance = new ExcelExporter();
  /**
   * A method that exports the expenses to an Excel file.
   */
  public static final String UNIQUEID =
          TimeOfDayChecker.getCurrentMonth() + TimeOfDayChecker.getYear();
  /**
   * The Output directory.
   */
  private static final String OUTPUTDIRECTORY =
          USERFILES_PATH + GUI.getCurrentUser() + "/";

  /**
   * Instantiates a new Excel exporter.
   */
  private ExcelExporter() {
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static ExcelExporter getInstance() {
    return instance;
  }

  /**
   * A method that exports a csv file to excel.
   *
   * @return the string of the output file
   * @throws FileNotFoundException the file not found exception
   */
  public String exportToExcel() throws FileNotFoundException {
    String outputDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    String csvFile = outputDirectory + GUI.getCurrentUser() + CSV_SUFFIX;
    String excelFile = outputDirectory + GUI.getCurrentUser() + EXCEL_SUFFIX;
    File outputDirectoryFile = new File(outputDirectory);
    if (!outputDirectoryFile.exists()) {
      outputDirectoryFile.mkdirs();
    }
    File inputFileObj = new File(csvFile);
    if (inputFileObj.length() == 0) {
      try (Workbook workbook = new XSSFWorkbook();
           FileOutputStream outputStream = new FileOutputStream(excelFile)) {
        workbook.createSheet(TimeOfDayChecker.getCurrentMonth());
        workbook.write(outputStream);
      } catch (IOException e) {
        throw new IllegalArgumentException("Could not write to file: " + excelFile, e);
      }
    } else {
      try (BufferedReader br = new BufferedReader(
              new FileReader(csvFile)); Workbook workbook = new XSSFWorkbook()) {

        String line;

        while ((line = br.readLine()) != null) {
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

          String month = TimeOfDayChecker.getSelectedMonth(columns[2]);
          String category = columns[0];
          String name = columns[1];
          String date = columns[2];
          String price = columns[3];
          String accountName = columns[4];

          Sheet sheet = workbook.getSheet(month);
          if (sheet == null) {
            sheet = workbook.createSheet(month);
            Row headerRow = sheet.createRow(0);
            String[] headerColumns = {"Category", "Name", "Date", "Price", "Account"};
            for (int i = 0; i < headerColumns.length; i++) {
              Cell cell = headerRow.createCell(i);
              cell.setCellValue(headerColumns[i]);
            }
          }

          Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
          Cell categoryCell = dataRow.createCell(0);
          categoryCell.setCellValue(category);
          Cell nameCell = dataRow.createCell(1);
          nameCell.setCellValue(name);
          Cell dateCell = dataRow.createCell(2);
          dateCell.setCellValue(date);
          Cell priceCell = dataRow.createCell(3);
          priceCell.setCellValue(Double.parseDouble(price));
          Cell accountNameCell = dataRow.createCell(4);
          accountNameCell.setCellValue(accountName);
        }
        for (Sheet sheet : workbook) {
          double monthlyTotal = 0;
          for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
              Cell priceCell = row.getCell(3);
              if (priceCell != null && priceCell.getCellType() == CellType.NUMERIC) {
                monthlyTotal += priceCell.getNumericCellValue();
              }
              if (priceCell != null && priceCell.getCellType().equals(CellType.STRING)) {
                monthlyTotal += Double.parseDouble(priceCell.getStringCellValue());
              }
            }
          }

          Row totalRow = sheet.createRow(sheet.getLastRowNum() + 1);
          Cell totalLabelCell = totalRow.createCell(0);
          totalLabelCell.setCellValue("Monthly total: ");
          Cell totalValueCell = totalRow.createCell(1);
          totalValueCell.setCellValue(monthlyTotal);
        }

        workbook.write(new FileOutputStream(excelFile));

      } catch (IOException e) {
        throw new IllegalArgumentException("Error while reading file: " + csvFile, e);
      }
    }
    return excelFile;
  }

  /**
   * A method that exports an Excel file to pdf.
   *
   * @param excelFile the Excel file
   * @param fileName  the file name
   * @throws IOException       the io exception
   * @throws DocumentException the document exception
   */
  public void convertToPdf(String excelFile,
                           String fileName) throws IOException, DocumentException {
    String outputDirectory = USERFILES_PATH + GUI.getCurrentUser() + "/";
    try (Workbook workbook = new XSSFWorkbook(
            new FileInputStream(excelFile));
         FileOutputStream fos = new FileOutputStream(
                 outputDirectory + GUI.getCurrentUser() + fileName + ".pdf")) {

      Document document = new Document();
      PdfWriter.getInstance(document, fos);
      document.setPageSize(PageSize.A4.rotate());
      document.open();

      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        Sheet sheet = workbook.getSheetAt(i);
        for (Row row : sheet) {
          com.itextpdf.text.pdf.PdfPTable table =
                  new com.itextpdf.text.pdf.PdfPTable(row.getLastCellNum());
          float[] columnWidths = new float[row.getLastCellNum()];
          for (int j = 0; j < row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            com.itextpdf.text.Font font1 = new com.itextpdf.text.Font();
            if (row.getRowNum() == 0) {
              font1.setStyle(com.itextpdf.text.Font.BOLD);
            }
            PdfPCell pdfPCell = new PdfPCell(new Phrase(cell.toString(), font1));
            table.addCell(pdfPCell);
            columnWidths[j] = sheet.getColumnWidthInPixels(j);
          }
          table.setWidths(columnWidths);
          document.add(table);
        }
        document.add(new Paragraph("\n"));
      }
      document.close();
    }
  }

  /**
   * A method that creates a bank statement based on the category and date range.
   *
   * @param account  the account name
   * @param category the category name
   * @param dateFrom the date from
   * @param dateTo   the date to
   * @return the file name
   * @throws IOException       the io exception
   * @throws DocumentException the document exception
   */
  public String createBankStatement(String account, String category,
                                    String dateFrom, String dateTo)
          throws IOException, DocumentException {
    String csvFile = USERFILES_PATH + GUI.getCurrentUser()
            + "/" + GUI.getCurrentUser() + CSV_SUFFIX;
    String bankstatementAsExcel = USERFILES_PATH + GUI.getCurrentUser()
            + "/" + GUI.getCurrentUser() + "bankstatement" + EXCEL_SUFFIX;
    BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
    String row;
    List<String[]> rows = new ArrayList<>();
    while ((row = csvReader.readLine()) != null) {
      String[] data = row.split(",");
      rows.add(data);
    }
    csvReader.close();

    List<String[]> filteredRows = new ArrayList<>();
    for (String[] data : rows) {
      String date = data[2];
      if (data[0].equals(category) && data[4].equals(account)
              && date.compareTo(dateFrom) >= 0 && date.compareTo(dateTo) <= 0) {
        String name = data[1];
        name = name.replaceAll("^(\\|+)|(\\|+)$", "");
        data[1] = name;
        filteredRows.add(data);
      }
    }

    try (XSSFWorkbook workbook = new XSSFWorkbook();
         FileOutputStream outputStream =
                 new FileOutputStream(bankstatementAsExcel)) {
      XSSFSheet sheet = workbook.createSheet("Sheet1");
      int rowNum = 0;
      Row headerRow = sheet.createRow(rowNum++);
      String[] headerColumns = {"Category", "Name", "Date", "Price", "Account"};
      int colNum = 0;
      for (String header : headerColumns) {
        Cell cell = headerRow.createCell(colNum++);
        cell.setCellValue(header);
      }
      for (String[] data : filteredRows) {
        Row row1 = sheet.createRow(rowNum++);
        colNum = 0;
        for (String cellData : data) {
          Cell cell = row1.createCell(colNum++);
          if (cellData instanceof String) {
            cell.setCellValue(cellData);
          }
        }
      }
      workbook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return bankstatementAsExcel;

  }

  /**
   * A method that gets the expenses for the current month.
   *
   * @return the expenses for month
   */
  public List<Expense> getExpensesForMonth() {
    String outputFile = USERFILES_PATH + GUI.getCurrentUser()
            + "/" + GUI.getCurrentUser() + EXCEL_SUFFIX;
    String currentMonth = TimeOfDayChecker.getCurrentMonth();
    try {
      exportToExcel();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }
    try (Workbook workbook = new XSSFWorkbook(
            new FileInputStream(outputFile))) {
        {
        Sheet sheet = workbook.getSheet(currentMonth);
        if (sheet == null) {
          sheet = workbook.createSheet(currentMonth);
        }
        expensesToTable = readExpensesFromSheet(sheet);
        }
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read file: " + outputFile, e);
    }
    return expensesToTable;
  }

  /**
   * Returns the total of all expenses in the category food.
   *
   * @param expenses the expenses
   * @return the total of food
   */
  public double getTotalOfFood(List<Expense> expenses) {
    double totalFood = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Food")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalFood += expense.getPrice();
      }
    }
    return totalFood;
  }

  /**
   * Returns the total of all expenses in the category transportation.
   *
   * @param expenses the expenses
   * @return the total of transportation
   */
  public double getTotalOfTransportation(List<Expense> expenses) {
    double totalTransportation = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Transportation")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalTransportation += expense.getPrice();
      }
    }
    return totalTransportation;
  }

  /**
   * Returns the total of all expenses in the category entertainment.
   *
   * @param expenses the expenses
   * @return the total of entertainment
   */
  public double getTotalOfEntertainment(List<Expense> expenses) {
    double totalEntertainment = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Entertainment")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalEntertainment += expense.getPrice();
      }
    }
    return totalEntertainment;
  }

  /**
   * Returns the total of all expenses in the category clothing.
   *
   * @param expenses the expenses
   * @return the total of clothing
   */
  public double getTotalOfClothing(List<Expense> expenses) {
    double totalClothing = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Clothing")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalClothing += expense.getPrice();
      }
    }
    return totalClothing;
  }

  /**
   * Returns the total of all expenses in the category other.
   *
   * @param expenses the expenses
   * @return the total of other
   */
  public double getTotalOfOther(List<Expense> expenses) {
    double totalOther = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Other")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalOther += expense.getPrice();
      }
    }
    return totalOther;
  }

  /**
   * Returns the total of all expenses in the category rent.
   *
   * @param expenses the expenses
   * @return the total of rent
   */
  public double getTotalOfRent(List<Expense> expenses) {
    double totalRent = 0;
    for (Expense expense : expenses) {
      if (expense.getCategory().equals("Rent")
              && expense.getUniqueId().equals(UNIQUEID)) {
        totalRent += expense.getPrice();
      }
    }
    return totalRent;
  }

  /**
   * Returns the total of all expenses.
   *
   * @return the total of all expenses
   */
  public double getMonthlyTotal() {
    double monthlyTotal = 0;
    monthlyTotal += getTotalOfClothing(expensesToTable);
    monthlyTotal += getTotalOfEntertainment(expensesToTable);
    monthlyTotal += getTotalOfFood(expensesToTable);
    monthlyTotal += getTotalOfOther(expensesToTable);
    monthlyTotal += getTotalOfRent(expensesToTable);
    monthlyTotal += getTotalOfTransportation(expensesToTable);
    return monthlyTotal;
  }

  /**
   * Gets output directory.
   *
   * @return the output directory
   */
  public static String getOutputDirectory() {
    return OUTPUTDIRECTORY;
  }

  /**
   * Gets csv file path.
   *
   * @return the csv file path
   */
  public static String getCSVFilePath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + ".csv";
  }

  /**
   * Gets excel path.
   *
   * @return the excel path
   */
  public static String getExcelPath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + EXCEL_SUFFIX;
  }

  /**
   * Gets bank statement path.
   *
   * @return the bank statement path
   */
  public static String getBankStatementPath() {
    return USERFILES_PATH + GUI.getCurrentUser() + "/"
            + GUI.getCurrentUser() + "bankstatement" + ".pdf";
  }

  /**
   * Gets bank statement as excel path.
   *
   * @return the bank statement as excel path
   */
  public static String getBankStatementAsExcelPath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + "bankstatement.xlsx";
  }

  /**
   * Gets budget path.
   *
   * @return the budget path
   */
  public static String getBudgetPath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + "budget.csv";
  }

  /**
   * Gets temp budget path.
   *
   * @return the temp budget path
   */
  public static String getTempBudgetPath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + "budget_temp.csv";
  }

  /**
   * Gets report pdf path.
   *
   * @return the report pdf path
   */
  public static String getReportPDFPath() {
    return USERFILES_PATH + GUI.getCurrentUser() + "/" + GUI.getCurrentUser() + "report.pdf";
  }

  /**
   * Gets transfer path.
   *
   * @return the transfer path
   */
  public static String getTransferPath() {
    return OUTPUTDIRECTORY + GUI.getCurrentUser() + "transfer.csv";
  }

  /**
   * Gets expenses to table.
   *
   * @return the expenses to table
   */
  public List<Expense> getExpensesToTable() {
    return expensesToTable;
  }

  /**
   * Sets expenses to table.
   *
   * @param expensesToTable the expenses to table
   */
  public void setExpensesToTable(List<Expense> expensesToTable) {
    this.expensesToTable = expensesToTable;
  }

  /*
   * Reads the expenses from the Excel sheet and returns them as a list of expenses.
   */
  private List<Expense> readExpensesFromSheet(Sheet sheet) {
    List<Expense> expenses = new ArrayList<>();
    if (sheet.getPhysicalNumberOfRows() == 0) {
      return expenses;
    }
    for (int i = 0; i < sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      if (row.getRowNum() == 0) {
        // Skip header row
        continue;
      }
      Cell categoryCell = row.getCell(0);
      String category = "";
      if (categoryCell != null) {
        if (categoryCell.getCellType() == CellType.STRING) {
          category = categoryCell.getStringCellValue();
        } else if (categoryCell.getCellType() == CellType.NUMERIC) {
          category = String.valueOf(categoryCell.getNumericCellValue());
        }
      }

      Cell nameCell = row.getCell(1);
      String name = "";
      if (nameCell != null) {
        if (nameCell.getCellType() == CellType.STRING) {
          name = nameCell.getStringCellValue();
        } else if (nameCell.getCellType() == CellType.NUMERIC) {
          name = String.valueOf(nameCell.getNumericCellValue());
        }
      }

      Cell dateCell = row.getCell(2);
      LocalDate date = null;
      if (dateCell != null && dateCell.getCellType() == CellType.STRING) {
        date = LocalDate.parse(dateCell.getStringCellValue());
      }

      Cell priceCell = row.getCell(3);
      Double price = null;
      if (priceCell != null) {
        if (priceCell.getCellType() == CellType.NUMERIC) {
          price = priceCell.getNumericCellValue();
        } else if (priceCell.getCellType() == CellType.STRING) {
          price = Double.parseDouble(priceCell.getStringCellValue());
        }
      }

      Cell accountCell = row.getCell(4);
      String account = "";
      if (accountCell != null) {
        if (accountCell.getCellType() == CellType.STRING) {
          account = accountCell.getStringCellValue();
        } else if (accountCell.getCellType() == CellType.NUMERIC) {
          account = String.valueOf(accountCell.getNumericCellValue());
        }
      }

      Expense expense = new Expense(name, price, date, category, account, UNIQUEID);
      expenses.add(expense);
    }
    return expenses;
  }
}