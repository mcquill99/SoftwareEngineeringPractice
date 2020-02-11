package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAPITest {


    @Test
    void freezeTest() throws AccountFrozenException, InsufficientFundsException {
        CentralBank bank = new CentralBank();
        bank.teller.createAccount("11212", "a@b.com", "testingPassword", 500);
        bank.teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bank.teller.createAccount("test123", "a@b.com", "testpass", 1000);

        assertEquals(false, bank.admin.getIsFrozen("11212"));
        assertEquals(false, bank.admin.getIsFrozen("11BFWGG"));
        assertEquals(false, bank.admin.getIsFrozen("test123"));

//        bankAccount.accountMap.get("11212").freeze();
//        bankAccount.accountMap.get("11BFWGG").freeze();
//        bankAccount.accountMap.get("test123").freeze();

        bank.admin.freezeAccount("11212");
        bank.admin.freezeAccount("11BFWGG");
        bank.admin.freezeAccount("test123");



        assertEquals(true, bank.admin.getIsFrozen("11212"));
        assertEquals(true, bank.admin.getIsFrozen("11BFWGG"));
        assertEquals(true, bank.admin.getIsFrozen("test123"));

        //next adding tests to see if when the account is frozen that deposit and other functions do not work

        assertThrows(AccountFrozenException.class, () -> bank.teller.withdraw("11212", 10));
        assertThrows(AccountFrozenException.class, () -> bank.teller.deposit("test123", 25));
        assertThrows(AccountFrozenException.class, () -> bank.teller.withdraw("11BFWGG", 10));

        //bank.admin.unfreezeAcct("test123");
        bank.admin.unfreezeAcct("test123");

        assertThrows(AccountFrozenException.class, () -> bank.teller.transfer("test123", "11BFWGG", 10));
        bank.admin.unfreezeAcct("11BFWGG");
        bank.teller.transfer("test123", "11BFWGG", 10);

        assertEquals(990, bank.teller.checkBalance("test123"));
        assertEquals(1010, bank.teller.checkBalance("11BFWGG"));

        bank.admin.freezeAccount("11BFWGG");
        assertThrows(AccountFrozenException.class, () -> bank.teller.transfer("test123", "11BFWGG", 10));





    }

    @Test
    void unFreezeTest() throws AccountFrozenException{
        CentralBank bank = new CentralBank();

        bank.teller.createAccount("11212", "a@b.com", "testingPassword", 500);
        bank.teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bank.teller.createAccount("test123", "a@b.com", "testpass", 1000);



        bank.admin.freezeAccount("11212");
        bank.admin.freezeAccount("test123");
        bank.admin.freezeAccount("11BFWGG");


        assertEquals(true, bank.admin.getIsFrozen("11212"));
        assertEquals(true, bank.admin.getIsFrozen("11BFWGG"));
        assertEquals(true, bank.admin.getIsFrozen("test123"));



        bank.admin.unfreezeAcct("test123");
        bank.admin.unfreezeAcct("11BFWGG");
        bank.admin.unfreezeAcct("11212");

        assertEquals(false, bank.admin.getIsFrozen("11212"));
        assertEquals(false, bank.admin.getIsFrozen("11BFWGG"));
        assertEquals(false, bank.admin.getIsFrozen("test123"));
    }

    @Test
    void totalAssetTest() throws InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();

        //multiple accounts
        bank.teller.createAccount("123","a@b.com", "testPass", 0.99);
        assertEquals(0.99, bank.admin.calcTotalAssets());

        bank.teller.createAccount("456","a@b.com", "testPass", 1500.57);
        assertEquals(1501.56, bank.admin.calcTotalAssets());

        bank.teller.createAccount("789","a@b.com", "testPass", 100000.99);
        assertEquals(101502.55, bank.admin.calcTotalAssets());

        //deposit
        bank.teller.deposit("123", 97.45);
        assertEquals(101600, bank.admin.calcTotalAssets());

        bank.teller.deposit("456", 1400.50);
        assertEquals(103000.50, bank.admin.calcTotalAssets());

        //withdraw
        bank.teller.withdraw("789", 0.50);
        assertEquals(103000, bank.admin.calcTotalAssets());
        bank.teller.withdraw("789", 3000.48);
        assertEquals(99999.52, bank.admin.calcTotalAssets());
    }

    @Test
    void suspiciousActivityTest() throws InsufficientFundsException {
        CentralBank bankAccount = new CentralBank();
        bankAccount.teller.createAccount("11212", "a@b.com", "testingPassword", 500000);
        bankAccount.teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.teller.createAccount("test123", "a@b.com", "testpass", 100000);

        bankAccount.teller.createAccount("12345", "a@b.com", "testingPassword", 500000);
        bankAccount.teller.createAccount("98765", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.teller.createAccount("test321", "a@b.com", "testpass", 100000);

        bankAccount.teller.createAccount("a1", "a@b.com", "testingPassword", 100000);
        bankAccount.teller.createAccount("a2", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.teller.createAccount("a3", "a@b.com", "testpass", 100000);

        bankAccount.teller.createAccount("a4", "a@b.com", "testingPassword", 100000);
        bankAccount.teller.createAccount("a5", "tester@gmail.com", "singleLetter", 100000);
        bankAccount.teller.createAccount("a6", "a@b.com", "testpass", 100000);

        //test empty map of suspicious accounts
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious deposits
        bankAccount.teller.deposit("11212",49999);
        bankAccount.teller.deposit("11BFWGG", 10);
        bankAccount.teller.deposit("11BFWGG", 10);
        bankAccount.teller.deposit("11BFWGG", 10);
        bankAccount.teller.deposit("test123", 1234);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious withdrawls
        bankAccount.teller.withdraw("11212", 49999);
        bankAccount.teller.withdraw("11BFWGG", 10000);
        bankAccount.teller.withdraw("11BFWGG", 1234);
        bankAccount.teller.withdraw("test123", 100);
        bankAccount.teller.withdraw("test123", 100);
        bankAccount.teller.withdraw("test123", 100);
        bankAccount.teller.withdraw("test123", 100);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //deposits of greater than or equal to 50,000
        bankAccount.teller.deposit("11212", 50000);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("11212"));
        bankAccount.teller.deposit("11BFWGG",100000);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("11BFWGG"));
        bankAccount.teller.deposit("test123", 50001);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("test123"));

        //withdraws greater than or equal to 50,000
        bankAccount.teller.withdraw("12345", 50000);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("12345"));
        bankAccount.teller.deposit("98765",60234);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("98765"));
        bankAccount.teller.deposit("test321", 50001);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("test321"));

        //transfers greater than or equal to 75% of the account balance
        bankAccount.teller.transfer("a1", "a2", 75000);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a1"));
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a2"));
        bankAccount.teller.transfer("a3", "a4", 100000);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a3"));
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a4"));
        bankAccount.teller.transfer("a5", "a6", 75001);
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a5"));
        assertEquals(true, bankAccount.admin.findAcctIdsWithSuspiciousActivity().contains("a6"));


    }


    }
