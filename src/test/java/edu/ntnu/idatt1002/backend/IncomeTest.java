package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Income;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Income Test")
class IncomeTest {

  @Nested
  @DisplayName("Constructor1 Test")
  class Constructor1IncomeTest {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertEquals("IncomeName", income.getName());
      assertEquals(100, income.getPrice());
      assertEquals(2, income.getCategory());
      assertEquals(LocalDate.now(), income.getDate());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Income(null, 100, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Income("", 100, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with negative price")
    void constructorTestWithNegativePrice() {
      assertThrows(IllegalArgumentException.class, () -> new Income("IncomeName", -1, 2, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with category < 1")
    void constructorTestWithCategoryLessThanOne() {
      assertThrows(IllegalArgumentException.class, () -> new Income("IncomeName", 100, 0, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with category < 6")
    void constructorTestWithCategoryGreaterThanSix() {
      assertThrows(IllegalArgumentException.class, () -> new Income("IncomeName", 100, 7, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with null date")
    void constructorTestWithNullDate() {
      assertThrows(NullPointerException.class, () -> new Income("IncomeName", 100, 2, null));
    }

  }

  @Nested
  @DisplayName("Constructor2 Income Test")
  class Constructor2IncomeTest {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Income income = new Income("IncomeName", 100, LocalDate.now());
      assertEquals("IncomeName", income.getName());
      assertEquals(100, income.getPrice());
      assertEquals(LocalDate.now(), income.getDate());
    }

    @Test
    @DisplayName("Test constructor with null name")
    void constructorTestWithNullName() {
      assertThrows(NullPointerException.class, () -> new Income(null, 100, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with empty name")
    void constructorTestWithEmptyName() {
      assertThrows(NullPointerException.class, () -> new Income("", 100, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with negative price")
    void constructorTestWithNegativePrice() {
      assertThrows(IllegalArgumentException.class, () -> new Income("IncomeName", -1, LocalDate.now()));
    }

    @Test
    @DisplayName("Test constructor with null date")
    void constructorTestWithNullDate() {
      assertThrows(NullPointerException.class, () -> new Income("IncomeName", 100, null));
    }
  }
  @Nested
  @DisplayName("Getters Test")
  class GettersTest {

    @Test
    @DisplayName("Test getName")
    void getName() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertEquals("IncomeName", income.getName());
    }

    @Test
    @DisplayName("Test getPrice")
    void getPrice() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertEquals(100, income.getPrice());
    }

    @Test
    @DisplayName("Test getCategory")
    void getCategory() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertEquals(2, income.getCategory());
    }

    @Test
    @DisplayName("Test getDate")
    void getDate() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertEquals(LocalDate.now(), income.getDate());
    }
  }

  @Nested
  @DisplayName("Setters Test")
  class SettersTest {

    @Test
    @DisplayName("Test setValidName")
    void setName() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      income.setName("NewIncomeName");
      assertEquals("NewIncomeName", income.getName());
    }
    @Test
    @DisplayName("Test setInvalidName")
    void setInvalidName() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertThrows(NullPointerException.class, () -> income.setName(""));
    }

    @Test
    @DisplayName("Test setValidPrice")
    void setPrice() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      income.setPrice(200);
      assertEquals(200, income.getPrice());
    }

    @Test
    @DisplayName("Test setInvalidPrice")
    void setInvalidPrice() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertThrows(IllegalArgumentException.class, () -> income.setPrice(-1));
    }

    @Test
    @DisplayName("Test setValidCategory")
    void setCategory() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      income.setCategory(3);
      assertEquals(3, income.getCategory());
    }

    @Test
    @DisplayName("Test setInvalidCategory")
    void setInvalidCategory() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertThrows(IllegalArgumentException.class, () -> income.setCategory(7));
    }

    @Test
    @DisplayName("Test setValidDate")
    void setDate() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      income.setDate(LocalDate.now().plusDays(1));
      assertEquals(LocalDate.now().plusDays(1), income.getDate());
    }
    @Test
    @DisplayName("Test setInvalidDate")
    void setInvalidDate() {
      Income income = new Income("IncomeName", 100, 2, LocalDate.now());
      assertThrows(NullPointerException.class, () -> income.setDate(null));
    }
  }
}
