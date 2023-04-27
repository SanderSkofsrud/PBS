package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Transfers;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Transfers Test")
public class TransfersTest {

  @Nested
  @DisplayName("Constructor Test")
  class ConstructorTest {

    @Test
    @DisplayName("Test valid constructor")
    void validConstructorTest() {
      Transfers transfers = new Transfers("Income", 100, "Today", 'A');
      assertEquals("Income", transfers.getAccountName());
      assertEquals(100, transfers.getAmount());
      assertEquals("Today", transfers.getDate());
      assertEquals('A', transfers.getTransferType());
    }

    @Test
    @DisplayName("Test constructor with blank account name")
    void blankAccountNameTest() {
      assertThrows(NullPointerException.class, () -> new Transfers("", 100, "Today", 'A'));
    }



    @Test
    @DisplayName("Test constructor with blank date")
    void blankAccountDateTest() {
      assertThrows(NullPointerException.class, () -> new Transfers("Income", 100, "", 'A'));
    }
  }

  @Nested
  @DisplayName("Add Transfers Test")
  class addTransfersTest {

    @Test
    @DisplayName("Test addTransfer")
    void testAddTransfer() {
      Transfers transfers = new Transfers("Expense");
      transfers.addTransfer("Account 1", 100.0, "2023-04-26", 'A');
      assertEquals("Account 1", transfers.getTransfers().get(0).getAccountName());
      assertEquals(100.0, transfers.getTransfers().get(0).getAmount());
      assertEquals("2023-04-26", transfers.getTransfers().get(0).getDate());
      assertEquals('A', transfers.getTransfers().get(0).getTransferType());
    }
    @Test
    @DisplayName("Test addTransfers with blank account name")
    void blankAccountNameTest() {
      Transfers transfers = new Transfers("Income", 100, "Today", 'A');
      assertThrows(IllegalArgumentException.class, () -> transfers.addTransfer("", 200, "Last day", 'B'));
    }
  }
}
