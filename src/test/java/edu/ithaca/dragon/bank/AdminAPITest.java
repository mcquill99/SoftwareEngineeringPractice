package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAPITest {


    @Test
    void freezeTest() throws AccountFrozenException, InsufficientFundsException {
        BankTeller bankAccount = new BankTeller();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount.createAccount("test123", "a@b.com", "testpass", 1000);

        assertEquals(false, bankAccount.accountMap.get("11212").getIsFrozen());
        assertEquals(false, bankAccount.accountMap.get("11BFWGG").getIsFrozen());
        assertEquals(false, bankAccount.accountMap.get("test123").getIsFrozen());

        bankAccount.accountMap.get("11212").freeze();
        bankAccount.accountMap.get("11BFWGG").freeze();


        bankAccount.accountMap.get("test123").freeze();


        assertEquals(true, bankAccount.accountMap.get("11212").getIsFrozen());
        assertEquals(true, bankAccount.accountMap.get("11BFWGG").getIsFrozen());
        assertEquals(true, bankAccount.accountMap.get("test123").getIsFrozen());

        //next adding tests to see if when the account is frozen that deposit and other functions do not work

        assertThrows(AccountFrozenException.class, () -> bankAccount.withdraw("11212", 10));
        assertThrows(AccountFrozenException.class, () -> bankAccount.deposit("test123", 25));
        assertThrows(AccountFrozenException.class, () -> bankAccount.withdraw("11BFWGG", 10));

        //bankAccount.unfreezeAcct("test123");
        bankAccount.accountMap.get("test123").unfreeze();

        assertThrows(AccountFrozenException.class, () -> bankAccount.transfer("test123", "11BFWGG", 10));
        bankAccount.unfreezeAcct("11BFWGG");
        bankAccount.accountMap.get("11BFWGG").unfreeze();
        bankAccount.transfer("test123", "11BFWGG", 10);

        assertEquals(990, bankAccount.checkBalance("test123"));
        assertEquals(1010, bankAccount.checkBalance("11BFWGG"));

        bankAccount.accountMap.get("11BFWGG").freeze();
        assertThrows(AccountFrozenException.class, () -> bankAccount.transfer("test123", "11BFWGG", 10));





    }

    @Test
    void unFreezeTest() throws AccountFrozenException{
        BankTeller bankAccount = new BankTeller();

        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount.createAccount("test123", "a@b.com", "testpass", 1000);

        bankAccount.accountMap.get("11212").freeze();
        bankAccount.accountMap.get("11BFWGG").freeze();
        bankAccount.accountMap.get("test123").freeze();


        assertEquals(true, bankAccount.accountMap.get("11212").getIsFrozen());
        assertEquals(true, bankAccount.accountMap.get("11BFWGG").getIsFrozen());
        assertEquals(true, bankAccount.accountMap.get("test123").getIsFrozen());



        bankAccount.accountMap.get("test123").unfreeze();
        bankAccount.accountMap.get("11BFWGG").unfreeze();
        bankAccount.accountMap.get("11212").unfreeze();

        assertEquals(false, bankAccount.accountMap.get("11212").getIsFrozen());
        assertEquals(false, bankAccount.accountMap.get("11BFWGG").getIsFrozen());
        assertEquals(false, bankAccount.accountMap.get("test123").getIsFrozen());
    }

    @Test
    void totalAssetTest() throws InsufficientFundsException, AccountFrozenException {
        BankTeller bank = new BankTeller();

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


    }
