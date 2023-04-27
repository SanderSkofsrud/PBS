package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Expense;
import edu.ntnu.idatt1002.backend.budgeting.Expenses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Expenses Test")
class ExpensesTest {

  private Expense testExpense;

  @BeforeEach
  public void setUp() {
    Expenses expenseInstance = Expenses.getInstance();

    expenseInstance.createTransportation();
    expenseInstance.createEntertainment();
    expenseInstance.createClothing();
    expenseInstance.createOther();
    expenseInstance.createFood();
    expenseInstance.createRent();
    expenseInstance.createAllAlist();
    LocalDate testDate = LocalDate.parse("2023-04-14");
    testExpense = new Expense("Test item", 100.0, 2, testDate);
  }

  @Test
  @DisplayName("Test addToArrayList")
  public void testAddToArrayList() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.getTransportation());
    assertTrue(instance.getTransportation().contains(testExpense));
  }

  @Test
  @DisplayName("Test addToArray with null expense")
  public void testAddToArrayListNullExpense() {
    Expenses instance = Expenses.getInstance();
    assertThrows(IllegalArgumentException.class, () -> instance.addToArrayList(null, instance.getTransportation()));
  }


  @Test
  @DisplayName("Test getTotalExpenses")
  public void testGetTotalExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.getTransportation());
    assertEquals(100.0, instance.getTotalExpenses(instance.getTransportation()));
  }

  @Test
  @DisplayName("Test getExpensesOfAllCategories")
  public void testGetExpensesOfAllCategories() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense,instance.getTransportation());
    instance.addToArrayList(testExpense, instance.getEntertainment());
    instance.addToArrayList(testExpense, instance.getClothing());
    instance.addToArrayList(testExpense, instance.getOther());
    instance.addToArrayList(testExpense, instance.getFood());
    instance.addToArrayList(testExpense, instance.getRent());
    assertEquals(600.0, instance.getExpensesOfAllCategories());
  }

  @Test
  @DisplayName("Test createAllExpenses")
  public void testCreateAllExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.getTransportation());
    instance.addToArrayList(testExpense, instance.getEntertainment());
    instance.addToArrayList(testExpense, instance.getClothing());
    instance.addToArrayList(testExpense, instance.getOther());
    instance.addToArrayList(testExpense, instance.getFood());
    instance.addToArrayList(testExpense, instance.getRent());
    List<Expense> allExpenses = instance.createAllExpenses();
    assertEquals(6, allExpenses.size());
  }

  @Test
  @DisplayName("Test getExpenses")
  public void testGetExpenses() {
    Expenses instance = Expenses.getInstance();
    instance.addToArrayList(testExpense, instance.getTransportation());
    instance.addToArrayList(testExpense, instance.getEntertainment());
    instance.addToArrayList(testExpense, instance.getClothing());
    instance.addToArrayList(testExpense, instance.getOther());
    instance.addToArrayList(testExpense, instance.getFood());
    instance.addToArrayList(testExpense, instance.getRent());
    instance.createAllExpenses();
    List<Expense> allExpenses = instance.getExpenses();
    assertEquals(6, allExpenses.size());
  }
}
