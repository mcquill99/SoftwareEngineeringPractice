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


    }
