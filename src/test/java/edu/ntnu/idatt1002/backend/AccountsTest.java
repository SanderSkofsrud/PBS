package edu.ntnu.idatt1002.backend;

import edu.ntnu.idatt1002.backend.budgeting.Accounts;
import edu.ntnu.idatt1002.frontend.utility.FileUtil;
import edu.ntnu.idatt1002.frontend.utility.SoundPlayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Accounts Test")
class AccountsTest {


  @Nested
  class AddAccountTest {

    @Test
    @DisplayName("Valid addAccount test")
    void addAccount() {
      Accounts instance = Accounts.getInstance();
      instance.addAccount("test", 100.00);
      assertEquals(100.00, instance.getTotalOfAccount("test"));
      //assertEquals("test", instance.getAccounts().keySet().toArray()[0]);
    }

    @Test
    @DisplayName("Test addAccount with null accountName")
    void addAccountWithNullAccountName() {
      Accounts instance = Accounts.getInstance();
      assertThrows(NullPointerException.class, () -> instance.addAccount(null, 100));
    }

    @Test
    @DisplayName("Test addAccount with empty accountName")
    void addAccountWithEmptyAccountName() {
      Accounts instance = Accounts.getInstance();
      assertThrows(NullPointerException.class, () -> instance.addAccount("", 100));
    }

    @Test
    @DisplayName("Test addAccount with negative accountBalance")
    void addAccountWithNegativeAccountBalance() {
      Accounts instance = Accounts.getInstance();
      assertThrows(IllegalArgumentException.class, () -> instance.addAccount("test", -1));
    }
  }
  @Nested
  class GetTotalOfAccountTest {

    @Test
    @DisplayName("Valid getTotalOfAccount test")
    void getTotalOfAccount() {
      Accounts instance = Accounts.getInstance();
      instance.addAccount("test", 100);
      assertEquals(100, instance.getTotalOfAccount("test"));
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
    }
    @Test
    @DisplayName("Test getTotalOfAccount with null accountName")
    void getTotalOfAccountWithNullAccountName() {
      Accounts instance = Accounts.getInstance();
      assertThrows(NullPointerException.class, () -> instance.getTotalOfAccount(null));
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));

    }

    @Test
    @DisplayName("Test getTotalOfAccount with empty accountName")
    void getTotalOfAccountWithEmptyAccountName() {
      Accounts instance = Accounts.getInstance();
      assertThrows(NullPointerException.class, () -> instance.getTotalOfAccount(""));
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
    }
  }

  @Nested
  class GetTotalOfAllAccountsTest {

    @Test
    @DisplayName("Valid getTotalOfAllAccounts test")
    void getTotalOfAllAccounts() {
      Accounts instance = Accounts.getInstance();
      instance.addAccount("test", 100);
      instance.addAccount("test2", 100);
      assertEquals(200, instance.getTotalOfAllAccounts());
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
    }
  }
  @Nested
  class GetAccountsTest {
    @Test
    @DisplayName("Valid getAccounts test")
    void getAccounts() {
      Accounts instance = Accounts.getInstance();
      instance.addAccount("test", 100);
      instance.addAccount("test2", 100);
      assertEquals(2, instance.getAccounts().size());
      SoundPlayer.play(FileUtil.getResourceFilePath("16bitconfirm.wav"));
    }
  }
}