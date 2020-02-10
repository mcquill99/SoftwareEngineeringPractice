package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicAPITest {

    @Test
    void confirmCredentialsTest(){

        CentralBank  bankAccount = new CentralBank();
        CentralBank bankAccount2 = new CentralBank();
        bankAccount.createAccount("11212", "a@b.com", "testingPassword", 500);
        bankAccount2.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);

        assertFalse(bankAccount.confirmCredentials("11212", "test"));
        assertFalse( bankAccount.confirmCredentials("112", "testingPassword"));
        assertTrue( bankAccount.confirmCredentials("11212", "testingPassword"));

        assertFalse(bankAccount2.confirmCredentials("11bfwgg", "singleLetter"));
        assertFalse(bankAccount2.confirmCredentials("11bfwgg", "SingleLetter"));
        assertTrue(bankAccount2.confirmCredentials("11BFWGG", "singleLetter"));

    }

    @Test
    void checkBalanceTest() throws IllegalArgumentException, InsufficientFundsException{
        CentralBank bank = new CentralBank();
        bank.createAccount("test123", "a@b.com", "testpass", 1000);
        assertEquals(1000, bank.checkBalance("test123"));
        //different account names
        bank.withdraw("test123", 1);
        assertEquals(999, bank.checkBalance("test123"));
        bank.withdraw("test123", 250.47);
        assertEquals(748.53, bank.checkBalance("test123"));
        bank.withdraw("test123", 748.53);
        assertEquals(0, bank.checkBalance("test123"));

        bank.createAccount("test456", "b@a.com", "testpass", 3290.57);
        assertEquals(3290.57, bank.checkBalance("test456"));

        bank.createAccount("test789", "b@a.com", "testpass", 999999999.99);
        assertEquals(999999999.99, bank.checkBalance("test789"));



    }

    @Test
    void withdrawTest() throws IllegalArgumentException, InsufficientFundsException{
        CentralBank bank = new CentralBank();
        bank.createAccount("test123", "a@b.com", "testpass", 1000);
        assertEquals(1000, bank.checkBalance("test123"));
        //legal withdraws
        bank.withdraw("test123", 1);
        assertEquals(999, bank.checkBalance("test123"));
        bank.withdraw("test123", 250.47);
        assertEquals(748.53, bank.checkBalance("test123"));
        bank.withdraw("test123", 748.53);
        assertEquals(0, bank.checkBalance("test123"));
        //illegal withdraws
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", -1));
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", -500.89));
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", -1000000.45));
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", -1.96646));
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", -1.9648690548065809546));
        assertThrows(IllegalArgumentException.class, ()->bank.withdraw("test123", 50.3940685));



    }

    @Test
    void depositTest() throws IllegalArgumentException{
        CentralBank bank = new CentralBank();
        bank.createAccount("test123", "a@b.com", "testpass", 1000);
        bank.deposit("test123", 100);
        assertEquals(1100, bank.checkBalance("test123"));
        bank.deposit("test123", 1000);
        assertEquals(2100, bank.checkBalance("test123"));
        bank.deposit("test123", .19);
        assertEquals(2100.19, bank.checkBalance("test123"));

        //account not found deposits
        assertThrows(IllegalArgumentException.class, ()-> bank.deposit("test13", 50));
        assertThrows(IllegalArgumentException.class, ()-> bank.deposit("tes3", 100));
        //illegal deposits
        assertThrows(IllegalArgumentException.class, ()-> bank.deposit("test123", 50.53894329));
        assertThrows(IllegalArgumentException.class, ()-> bank.deposit("test123", .3894329));
        //check balance remains the same
        assertEquals(2100.19, bank.checkBalance("test123"));

    }

    @Test
    void transferTest() throws InsufficientFundsException, IllegalArgumentException {
        CentralBank bank = new CentralBank();
        bank.createAccount("test1", "a@b.com", "testpass", 10000);
        bank.createAccount("test2", "b@c.com", "testpass", 500);

        assertEquals(10000, bank.checkBalance("test1"));
        bank.transfer("test1", "test2", 500);
        //check legal transfers both ways
        assertEquals(9500,bank.checkBalance("test1"));
        assertEquals(1000,bank.checkBalance("test2"));
        bank.transfer("test1", "test2", 1500);
        assertEquals(8000, bank.checkBalance("test1"));
        assertEquals(2500,bank.checkBalance("test2"));
        bank.transfer("test2", "test1", 150.02);
        assertEquals(8150.02, bank.checkBalance("test1"));
        assertEquals(2349.98,bank.checkBalance("test2"));

        //check for illegal transfers
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test1", "test2", .5424506));
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test2", "test1", .506));
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test2", "test1", -.506));
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test1", "test2", 100.64345));
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test1", "test1", 10)); //transfer to same account
        assertThrows(IllegalArgumentException.class, ()-> bank.transfer("test2", "test2", 1093)); //transfer to same account


        //check for insufficient funds
        assertThrows(InsufficientFundsException.class, ()-> bank.transfer("test1", "test2", 10000));
        assertThrows(InsufficientFundsException.class, ()-> bank.transfer("test2", "test1", 2349.99));
        assertThrows(InsufficientFundsException.class, ()-> bank.transfer("test1", "test2", 8150.03));
        assertThrows(InsufficientFundsException.class, ()-> bank.transfer("test2", "test1", 8150.03));

    }

    @Test
    void transactionHistoryTest() throws InsufficientFundsException{
        CentralBank bank = new CentralBank();
        bank.createAccount("123","a@b.com", "testPass", 1000);
        bank.createAccount("456", "b@a.com", "testPass", 500);
        //none
        assertEquals("", bank.transactionHistory("123"));
        //deposit
        bank.deposit("123", 1);
        assertEquals("Deposited $1.0", bank.transactionHistory("123"));
        bank.deposit("123", 785.68);
        assertEquals("Deposited $1.0, Deposited $785.68", bank.transactionHistory("123"));
        //withdraw
        bank.withdraw("123", 20);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0", bank.transactionHistory("123"));
        bank.withdraw("123", 583.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13", bank.transactionHistory("123"));
        //transfers
        bank.transfer("123","456", 10);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456", bank.transactionHistory("123"));
        bank.transfer("456","123", 100.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456, Received $100.13 from Account 456", bank.transactionHistory("123"));

        assertEquals("Received $10.0 from Account 123, Transferred $100.13 to Account 123", bank.transactionHistory("456"));

    }







}
