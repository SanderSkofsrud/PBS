package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Expense;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Expense test")
class ExpenseTest {

  @Nested
  @DisplayName("Constructor Test with 4 parameters (String name, double price, int category, LocalDate date)")
  class ExpenseConstructor1Test {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Expense expense = new Expense("ExpenseName", 100, 3, LocalDate.now());
      assertEquals("ExpenseName", expense.getName());
      assertEquals(100, expense.getPrice());
      assertEquals(3, expense.getCategoryInt());
      assertEquals(LocalDate.now(), expense.getDate());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Expense(null, 100, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Expense("", 100, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with negative price")
    void constructorTestWithNegativePrice() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with category < 1")
    void constructorTestWithCategoryLessThanOne() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", 100, 0, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with category < 6")
    void constructorTestWithCategoryGreaterThanSix() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", 100, 7, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with null date")
    void constructorTestWithNullDate() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", 100, 2, null));
    }
  }

  @Nested
  @DisplayName("Constructor2 Test with 5 parameters (String name, Double price, LocalDate date, String category, String account)")
  class ExpenseConstructor2Test {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Expense expense = new Expense("ExpenseName", 100.00, LocalDate.now(),"Food", "Cash");
      assertEquals("ExpenseName", expense.getName());
      assertEquals(100.00, expense.getPrice());
      assertEquals("Food", expense.getCategory());
      assertEquals(LocalDate.now(), expense.getDate());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Expense(null, 100.00, LocalDate.now(),"Food", "Cash"));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Expense("", 100.00, LocalDate.now(),"Food", "Cash"));
    }

    @Test
    @DisplayName("Test constructor with negative price")
    void constructorTestWithNegativePrice() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(),"Food", "Cash"));
    }

    @Test
    @DisplayName("Test constructor with empty category")
    void constructorTestWithNullCategory() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(),"", "Cash"));
    }

    @Test
    @DisplayName("Test constructor with null date")
    void constructorTestWithNullDate() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, null,"Food", "Cash"));
    }
    @Test
    @DisplayName("Test constructor with null category")
    void constructorTestWithNullCategoryString() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(),null, "Cash"));
    }

    @Test
    @DisplayName("Test constructor with empty account")
    void constructorTestWithNullAccount() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(),"Food", ""));
    }
  }

  @Nested
  @DisplayName("Constructor3 Test with 6 parameters (String name, Double price, LocalDate date, String category, String account, String uniqueID)")
  class ExpenseConstructor3Test {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Expense expense = new Expense("ExpenseName", 100.00, LocalDate.now(), "Food", "Cash", "123");
      assertEquals("ExpenseName", expense.getName());
      assertEquals(100.00, expense.getPrice());
      assertEquals("Food", expense.getCategory());
      assertEquals(LocalDate.now(), expense.getDate());
      assertEquals("123", expense.getUniqueId());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Expense(null, 100.00, LocalDate.now(), "Food", "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Expense("", 100.00, LocalDate.now(), "Food", "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with negative price")
    void constructorTestWithNegativePrice() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(), "Food", "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with empty category")
    void constructorTestWithNullCategory() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(), "", "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with null date")
    void constructorTestWithNullDate() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, null, "Food", "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with null category")
    void constructorTestWithNullCategoryString() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(), null, "Cash", "123"));
    }

    @Test
    @DisplayName("Test constructor with empty account")
    void constructorTestWithEmptyAccount() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(), "Food", "", "123"));
    }

    @Test
    @DisplayName("Test constructor with empty uniqueID")
    void constructorTestWithEmptyUniqueID() {
      assertThrows(IllegalArgumentException.class, () -> new Expense("ExpenseName", -1.00, LocalDate.now(), "Food", "Cash", ""));
    }
  }

  @Nested
  @DisplayName("Getters Test")
  class ExpenseGettersTest {

    @Test
    @DisplayName("Test getName()")
    void getNameTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertEquals("ExpenseName", expense.getName());
    }

    @Test
    @DisplayName("Test getPrice()")
    void getPriceTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertEquals(100, expense.getPrice());
    }

    @Test
    @DisplayName("Test getCategory()")
    void getCategoryIntTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertEquals(2, expense.getCategoryInt());
    }

    @Test
    @DisplayName("Test getDate()")
    void getDateTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertEquals(LocalDate.now(), expense.getDate());
    }

    // String name, Double price, LocalDate date, String category, String account, String uniqueID
    @Test
    @DisplayName("Test getUniqueID()")
    void getUniqueIDTest() {
      Expense expense = new Expense("ExpenseName", 100.00, LocalDate.now(), "Food", "Cash", "123");
      assertEquals("123", expense.getUniqueId());
    }
  }

  @Nested
  @DisplayName("Setters Test")
  class ExpenseSettersTest{

    @Test
    @DisplayName("Valid Test setName()")
    void setNameTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      expense.setName("NewName");
      assertEquals("NewName", expense.getName());
    }
    @Test
    @DisplayName("Test setName() with null")
    void setNameTestWithNull() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertThrows(NullPointerException.class, () -> expense.setName(null));
    }

    @Test
    @DisplayName("Valid Test setPrice()")
    void setPriceTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      expense.setPrice(200);
      assertEquals(200, expense.getPrice());
    }

    @Test
    @DisplayName("Test setPrice() with negative value")
    void setPriceTestWithNegative() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertThrows(IllegalArgumentException.class, () -> expense.setPrice(-1));
    }

    @Test
    @DisplayName("Test setCategoryInt()")
    void setCategoryIntTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      expense.setCategoryInt(3);
      assertEquals(3, expense.getCategoryInt());
    }

    @Test
    @DisplayName("Test setCategoryInt() with invalid value")
    void setCategoryIntTestWithInvalidValue() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertThrows(IllegalArgumentException.class, () -> expense.setCategoryInt(7));
    }
    @Test
    @DisplayName("Test setCategoryString()")
    void setCategoryAsStringTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      expense.setCategoryAsString("Food");
      assertEquals("Food", expense.getCategory());
    }

    @Test
    @DisplayName("Test setCategoryString() with blank value")
    void setCategoryAsStringTestWithBlankValue() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      assertThrows(NullPointerException.class, () -> expense.setCategoryAsString(null));
    }

    @Test
    @DisplayName("Test setDate()")
    void setDateTest() {
      Expense expense = new Expense("ExpenseName", 100, 2, LocalDate.now());
      expense.setDate(LocalDate.now().plusDays(1));
      assertEquals(LocalDate.now().plusDays(1), expense.getDate());
    }

    @Test
    @DisplayName("Test setUniqueID()")
    void setUniqueIDTest() {
      Expense expense = new Expense("ExpenseName", 100.00, LocalDate.now(), "Food", "Cash", "123");
      expense.setUniqueId("456");
      assertEquals("456", expense.getUniqueId());
    }
  }
}
