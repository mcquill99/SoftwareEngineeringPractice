package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAPITest {


    @Test
    void freezeTest(){
        CentralBank bankAccount = new CentralBank();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount.createAccount("test123", "a@b.com", "testpass", 1000);

        assertEquals(false, bankAccount.getIsFrozen("11212"));
        assertEquals(false, bankAccount.getIsFrozen("11BFWGG"));
        assertEquals(false, bankAccount.getIsFrozen("test123"));

        bankAccount.freezeAccount("11212");
        bankAccount.freezeAccount("11BFWGG");
        bankAccount.freezeAccount("test123");

        assertEquals(true, bankAccount.getIsFrozen("11212"));
        assertEquals(true, bankAccount.getIsFrozen("11BFWGG"));
        assertEquals(true, bankAccount.getIsFrozen("test123"));

        //next adding tests to see if when the account is frozen that deposit and other functions do not work

    }

    @Test
    void unFreezeTest(){
        CentralBank bankAccount = new CentralBank();

        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount.createAccount("test123", "a@b.com", "testpass", 1000);

        bankAccount.freezeAccount("11212");
        bankAccount.freezeAccount("11BFWGG");
        bankAccount.freezeAccount("test123");

        assertEquals(true, bankAccount.getIsFrozen("11212"));
        assertEquals(true, bankAccount.getIsFrozen("11BFWGG"));
        assertEquals(true, bankAccount.getIsFrozen("test123"));

        bankAccount.unfreezeAcct("11212");
        bankAccount.unfreezeAcct("11BFWGG");
        bankAccount.unfreezeAcct("test123");

        assertEquals(false, bankAccount.getIsFrozen("11212"));
        assertEquals(false, bankAccount.getIsFrozen("11BFWGG"));
        assertEquals(false, bankAccount.getIsFrozen("test123"));

    }

    @Test
    void totalAssetTest() throws InsufficientFundsException{
        CentralBank bank = new CentralBank();

        //multiple accounts
        bank.createAccount("123","a@b.com", "testPass", 0.99);
        assertEquals(0.99, bank.calcTotalAssets());

        bank.createAccount("456","a@b.com", "testPass", 1500.57);
        assertEquals(1501.56, bank.calcTotalAssets());

        bank.createAccount("789","a@b.com", "testPass", 100000.99);
        assertEquals(101502.55, bank.calcTotalAssets());

        //deposit
        bank.deposit("123", 97.45);
        assertEquals(101600, bank.calcTotalAssets());

        bank.deposit("456", 1400.50);
        assertEquals(103000.50, bank.calcTotalAssets());

        //withdraw
        bank.withdraw("789", 0.50);
        assertEquals(103000, bank.calcTotalAssets());
        bank.withdraw("789", 3000.48);
        assertEquals(99999.52, bank.calcTotalAssets());
    }

    @Test
    void suspiciousActivityTest(){
        CentralBank bank = new CentralBank();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500000);
        bankAccount.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.createAccount("test123", "a@b.com", "testpass", 100000);

        bankAccount.createAccount("12345", "a@b.com", "testingPassword", 500000);
        bankAccount.createAccount("98765", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.createAccount("test321", "a@b.com", "testpass", 100000);

        bankAccount.createAccount("a1", "a@b.com", "testingPassword", 100000);
        bankAccount.createAccount("a2", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.createAccount("a3", "a@b.com", "testpass", 100000);

        bankAccount.createAccount("a4", "a@b.com", "testingPassword", 100000);
        bankAccount.createAccount("a5", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.createAccount("a6", "a@b.com", "testpass", 100000);

        //test empty map of suspicious accounts
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious deposits
        bank.deposit("11212",49999);
        bank.deposit("11BFWGG", 10);
        bank.deposit("11BFWGG", 10);
        bank.deposit("11BFWGG", 10);
        bank.deposit("test123", 1234);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious withdrawls
        bank.withdraw("11212", 49999);
        bank.withdraw("11BFWGG", 10000);
        bank.withdraw("11BFWGG", 1234);
        bank.withdraw("test123", 100);
        bank.withdraw("test123", 100);
        bank.withdraw("test123", 100);
        bank.withdraw("test123", 100);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity().isEmpty());

        //deposits of greater than or equal to 50,000
        bank.deposit("11212", 50000);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("11212"));
        bank.deposit("11BFWGG",100000);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("11BFWGG"));
        bank.deposit("test123", 50001);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("test123"));

        //withdraws greater than or equal to 50,000
        bank.withdraw("12345", 50000);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("12345"));
        bank.deposit("98765",60234);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("98765"));
        bank.deposit("test321", 50001);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("test321"));

        //transfers greater than or equal to 75% of the account balance
        bank.tranfer("a1", "a2", 75000);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a1"));
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a2"));
        bank.tranfer("a3", "a4", 100000);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a3"));
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a4"));
        bank.tranfer("a5", "a6", 75001);
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a5"));
        assertEquals(true, bank.findAcctIdsWithSuspiciousActivity.containsKey("a6"));


    }


}
