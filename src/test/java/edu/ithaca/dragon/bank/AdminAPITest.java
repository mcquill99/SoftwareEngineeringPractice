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

        //deposits of greater than 100,000

        //deposits increasing in size

        //more than 10 depsits or withdrawls with in short period of time (one week)

        //withdraws greater than 50,000

    }


}
