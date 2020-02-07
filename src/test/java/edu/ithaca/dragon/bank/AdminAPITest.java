package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminAPITest {


    @Test
    void freezeTest(){
        CentralBank bankAccount = new CentralBank();
        CentralBank bankAccount2 = new CentralBank();
        CentralBank bankAccount3 = new CentralBank();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount2.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount3.createAccount("test123", "a@b.com", "testpass", 1000);

        AssertEquals(false, bankAccount.getIsFrozen);
        AssertEquals(false, bankAccount2.getIsFrozen);
        AssertEquals(false, bankAccount3.getIsFrozen);

        bankAccount.freeze();
        bankAccount2.freeze();
        bankAccount3.freeze();

        AssertEquals(true, bankAccount.getIsFrozen);
        AssertEquals(true, bankAccount2.getIsFrozen);
        AssertEquals(true, bankAccount3.getIsFrozen);

        //next adding tests to see if when the account is frozen that deposit and other functions do not work

    }

    void unFreezeTest(){
        CentralBank bankAccount = new CentralBank();
        CentralBank bankAccount2 = new CentralBank();
        CentralBank bankAccount3 = new CentralBank();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount2.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);
        bankAccount3.createAccount("test123", "a@b.com", "testpass", 1000);

        bankAccount.freeze();
        bankAccount2.freeze();
        bankAccount3.freeze();

        AssertEquals(true, bankAccount.getIsFrozen);
        AssertEquals(true, bankAccount2.getIsFrozen);
        AssertEquals(true, bankAccount3.getIsFrozen);

        bankAccount.unreeze();
        bankAccount2.unfreeze();
        bankAccount3.unfreeze();

        AssertEquals(false, bankAccount.getIsFrozen);
        AssertEquals(false, bankAccount2.getIsFrozen);
        AssertEquals(false, bankAccount3.getIsFrozen);
    }


}
