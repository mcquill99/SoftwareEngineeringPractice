package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAPITest {


    @Test
    void freezeTest() throws AccountFrozenException, InsufficientFundsException {
        CentralBank bank = new CentralBank();
        BankTeller teller = new BankTeller(bank);
        AdminSoftware admin = new AdminSoftware(bank);
        teller.createAccount("11212", "a@b.com", "testingPassword", 500,false);
        teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000,false);
        teller.createAccount("test123", "a@b.com", "testpass", 1000,false);

        assertEquals(false, admin.getIsFrozen("11212"));
        assertEquals(false, admin.getIsFrozen("11BFWGG"));
        assertEquals(false, admin.getIsFrozen("test123"));

//        bankAccount.accountMap.get("11212").freeze();
//        bankAccount.accountMap.get("11BFWGG").freeze();
//        bankAccount.accountMap.get("test123").freeze();

        admin.freezeAccount("11212");
        admin.freezeAccount("11BFWGG");
        admin.freezeAccount("test123");



        assertEquals(true, admin.getIsFrozen("11212"));
        assertEquals(true, admin.getIsFrozen("11BFWGG"));
        assertEquals(true, admin.getIsFrozen("test123"));

        //next adding tests to see if when the account is frozen that deposit and other functions do not work

        assertThrows(AccountFrozenException.class, () -> teller.withdraw("11212", 10));
        assertThrows(AccountFrozenException.class, () -> teller.deposit("test123", 25));
        assertThrows(AccountFrozenException.class, () -> teller.withdraw("11BFWGG", 10));

        //admin.unfreezeAcct("test123");
        admin.unfreezeAcct("test123");

        assertThrows(AccountFrozenException.class, () -> teller.transfer("test123", "11BFWGG", 10));
        admin.unfreezeAcct("11BFWGG");
        teller.transfer("test123", "11BFWGG", 10);

        assertEquals(990, teller.checkBalance("test123"));
        assertEquals(1010, teller.checkBalance("11BFWGG"));

        admin.freezeAccount("11BFWGG");
        assertThrows(AccountFrozenException.class, () -> teller.transfer("test123", "11BFWGG", 10));





    }

    @Test
    void unFreezeTest() throws AccountFrozenException{
        CentralBank bank = new CentralBank();
        BankTeller teller = new BankTeller(bank);
        AdminSoftware admin = new AdminSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testingPassword", 500,false);
        teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000,false);
        teller.createAccount("test123", "a@b.com", "testpass", 1000,false);



        admin.freezeAccount("11212");
        admin.freezeAccount("test123");
        admin.freezeAccount("11BFWGG");


        assertEquals(true, admin.getIsFrozen("11212"));
        assertEquals(true, admin.getIsFrozen("11BFWGG"));
        assertEquals(true, admin.getIsFrozen("test123"));



        admin.unfreezeAcct("test123");
        admin.unfreezeAcct("11BFWGG");
        admin.unfreezeAcct("11212");

        assertEquals(false, admin.getIsFrozen("11212"));
        assertEquals(false, admin.getIsFrozen("11BFWGG"));
        assertEquals(false, admin.getIsFrozen("test123"));
    }

    @Test
    void totalAssetTest() throws InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankTeller teller = new BankTeller(bank);
        AdminSoftware admin = new AdminSoftware(bank);

        //multiple accounts
        teller.createAccount("123","a@b.com", "testPass", 0.99,false);
        assertEquals(0.99, admin.calcTotalAssets());

        teller.createAccount("456","a@b.com", "testPass", 1500.57,false);
        assertEquals(1501.56, admin.calcTotalAssets());

        teller.createAccount("789","a@b.com", "testPass", 100000.99,false);
        assertEquals(101502.55, admin.calcTotalAssets());

        //deposit
        teller.deposit("123", 97.45);
        assertEquals(101600, admin.calcTotalAssets());

        teller.deposit("456", 1400.50);
        assertEquals(103000.50, admin.calcTotalAssets());

        //withdraw
        teller.withdraw("789", 0.50);
        assertEquals(103000, admin.calcTotalAssets());
        teller.withdraw("789", 3000.48);
        assertEquals(99999.52, admin.calcTotalAssets());
    }

    @Test
    void suspiciousActivityTest() throws InsufficientFundsException, AccountFrozenException {
        CentralBank bankAccount = new CentralBank();
        BankTeller teller = new BankTeller(bankAccount);
        AdminSoftware admin = new AdminSoftware(bankAccount);
        teller.createAccount("11212", "a@b.com", "testingPassword", 500000,false);
        teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 100000,false);
        teller.createAccount("test123", "a@b.com", "testpass", 100000,false);

        teller.createAccount("12345", "a@b.com", "testingPassword", 500000,false);
        teller.createAccount("98765", "tester@gmail.com", "singleLetter", 100000,false);
        teller.createAccount("test321", "a@b.com", "testpass", 100000,false);

        teller.createAccount("a1", "a@b.com", "testingPassword", 100000,false);
        teller.createAccount("a2", "tester@gmail.com", "singleLetter", 100000,false);
        teller.createAccount("a3", "a@b.com", "testpass", 100000,false);

        teller.createAccount("a4", "a@b.com", "testingPassword", 100000,false);
        teller.createAccount("a5", "tester@gmail.com", "singleLetter", 100000,false);
        teller.createAccount("a6", "a@b.com", "testpass", 100000,false);

        //test empty map of suspicious accounts
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious deposits
        teller.deposit("11212",49999);
        teller.deposit("11BFWGG", 10);
        teller.deposit("11BFWGG", 10);
        teller.deposit("11BFWGG", 10);
        teller.deposit("test123", 1234);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //non suspicious withdrawls
        teller.withdraw("11212", 49999);
        teller.withdraw("11BFWGG", 10000);
        teller.withdraw("11BFWGG", 1234);
        teller.withdraw("test123", 100);
        teller.withdraw("test123", 100);
        teller.withdraw("test123", 100);
        teller.withdraw("test123", 100);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().isEmpty());

        //deposits of greater than or equal to 50,000
        teller.deposit("11212", 50000);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("11212"));
        teller.deposit("11BFWGG",100000);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("11BFWGG"));
        teller.deposit("test123", 50001);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("test123"));

        //withdraws greater than or equal to 50,000
        teller.withdraw("12345", 50000);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("12345"));
        teller.deposit("98765",60234);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("98765"));
        teller.deposit("test321", 50001);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("test321"));

        //transfers greater than or equal to 75% of the account balance
        teller.transfer("a1", "a2", 75000);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a1"));
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a2"));
        teller.transfer("a3", "a4", 100000);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a3"));
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a4"));
        teller.transfer("a5", "a6", 75001);
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a5"));
        assertEquals(true, admin.findAcctIdsWithSuspiciousActivity().contains("a6"));


    }


    }
