package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Income;
import edu.ntnu.idatt1002.backend.budgeting.Incomes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Incomes Test")
class IncomesTest {

  @Nested
  @DisplayName("CreateIncomes Test")
  class CreateIncomeTest {
    @Test
    @DisplayName("Valid createIncomes test")
    void createIncomes() {
      Incomes instance = Incomes.getInstance();
      instance.createIncomes();
      assertEquals(0, instance.getIncomes().size());
    }
  }
  @Nested
  @DisplayName("GetIncomes Test")
  class GetIncomesTest {
    @Test
    @DisplayName("Valid getIncomes test")
    void getIncomes() {
      Incomes instance = Incomes.getInstance();
      instance.createIncomes();
      instance.addToArrayList(new Income("test", 100,2, LocalDate.now()), instance.getIncomes());
      assertEquals(1, instance.getIncomes().size());
    }

  }


  @Nested
  @DisplayName("CreateAllIncomes Test")
  class CreateAllIncomesTest {
    @Test
    @DisplayName("Valid createAllIncomes test")
    void createAllIncomes() {
      Incomes instance = Incomes.getInstance();
      instance.createIncomes();
      instance.addToArrayList(new Income("test", 100,2, LocalDate.now()),instance.getIncomes());
      instance.createAllIncomes();
      assertEquals(1, instance.getIncomes().size());
    }
  }

  @Nested
  @DisplayName("AddToArrayList Test")
  class AddToArrayListTest {
    @Test
    @DisplayName("Valid addToArrayList test")
    void addToArrayList() {
      Incomes instance = Incomes.getInstance();
      instance.createIncomes();
      instance.addToArrayList(new Income("test", 100,2, LocalDate.now()), instance.getIncomes());
      assertEquals(1, instance.getIncomes().size());
    }

    @Test
    @DisplayName("Invalid addToArrayList test with null income")
    void addToArrayListInvalid() {
      Incomes instance = Incomes.getInstance();
      assertThrows(NullPointerException.class, () -> instance.addToArrayList(null, instance.getIncomes()));
    }

    @Test
    @DisplayName("Invalid addToArrayList test with null arraylist")
    void addToArrayListInvalid2() {
      Incomes instance = Incomes.getInstance();
      assertThrows(NullPointerException.class, () -> instance.addToArrayList(new Income("test", 100,2, LocalDate.now()),null));
    }
  }
}