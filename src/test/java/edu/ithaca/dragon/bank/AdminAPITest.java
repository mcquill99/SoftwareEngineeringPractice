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


}
