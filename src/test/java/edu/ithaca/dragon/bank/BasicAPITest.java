package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicAPITest {

    @Test
    void confirmCredentialsTest(){

        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);

        teller.createAccount("11212", "a@b.com", "testingPassword", 500);
        teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000);

        assertFalse(atm.confirmCredentials("11212", "test"));
        assertFalse( atm.confirmCredentials("112", "testingPassword"));
        assertTrue( atm.confirmCredentials("11212", "testingPassword"));

        assertFalse(atm.confirmCredentials("11bfwgg", "singleLetter"));
        assertFalse(atm.confirmCredentials("11bfwgg", "SingleLetter"));
        assertTrue(atm.confirmCredentials("11BFWGG", "singleLetter"));

    }

    @Test
    void checkBalanceTest() throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000);
        assertEquals(1000, atm.checkBalance("test123"));
        //different account names
        atm.withdraw("test123", 1);
        assertEquals(999, atm.checkBalance("test123"));
        atm.withdraw("test123", 250.47);
        assertEquals(748.53, atm.checkBalance("test123"));
        atm.withdraw("test123", 748.53);
        assertEquals(0, atm.checkBalance("test123"));

        teller.createAccount("test456", "b@a.com", "testpass", 3290.57);
        assertEquals(3290.57, atm.checkBalance("test456"));

        teller.createAccount("test789", "b@a.com", "testpass", 999999999.99);
        assertEquals(999999999.99, atm.checkBalance("test789"));



    }

    @Test
    void withdrawTest() throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000);
        assertEquals(1000, atm.checkBalance("test123"));
        //legal withdraws
        atm.withdraw("test123", 1);
        assertEquals(999, atm.checkBalance("test123"));
        atm.withdraw("test123", 250.47);
        assertEquals(748.53, atm.checkBalance("test123"));
        atm.withdraw("test123", 748.53);
        assertEquals(0, atm.checkBalance("test123"));
        //illegal withdraws
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", -1));
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", -500.89));
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", -1000000.45));
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", -1.96646));
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", -1.9648690548065809546));
        assertThrows(IllegalArgumentException.class, ()->atm.withdraw("test123", 50.3940685));



    }

    @Test
    void depositTest() throws IllegalArgumentException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000);
        atm.deposit("test123", 100);
        assertEquals(1100, atm.checkBalance("test123"));
        atm.deposit("test123", 1000);
        assertEquals(2100, atm.checkBalance("test123"));
        atm.deposit("test123", .19);
        assertEquals(2100.19, atm.checkBalance("test123"));

        //account not found deposits
        assertThrows(IllegalArgumentException.class, ()-> atm.deposit("test13", 50));
        assertThrows(IllegalArgumentException.class, ()-> atm.deposit("tes3", 100));
        //illegal deposits
        assertThrows(IllegalArgumentException.class, ()-> atm.deposit("test123", 50.53894329));
        assertThrows(IllegalArgumentException.class, ()-> atm.deposit("test123", .3894329));
        //check balance remains the same
        assertEquals(2100.19, atm.checkBalance("test123"));

    }

    @Test
    void transferTest() throws InsufficientFundsException, IllegalArgumentException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);
        teller.createAccount("test1", "a@b.com", "testpass", 10000);
        teller.createAccount("test2", "b@c.com", "testpass", 500);

        assertEquals(10000, atm.checkBalance("test1"));
        atm.transfer("test1", "test2", 500);
        //check legal transfers both ways
        assertEquals(9500,atm.checkBalance("test1"));
        assertEquals(1000,atm.checkBalance("test2"));
        atm.transfer("test1", "test2", 1500);
        assertEquals(8000, atm.checkBalance("test1"));
        assertEquals(2500,atm.checkBalance("test2"));
        atm.transfer("test2", "test1", 150.02);
        assertEquals(8150.02, atm.checkBalance("test1"));
        assertEquals(2349.98,atm.checkBalance("test2"));

        //check for illegal transfers
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test1", "test2", .5424506));
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test2", "test1", .506));
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test2", "test1", -.506));
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test1", "test2", 100.64345));
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test1", "test1", 10)); //transfer to same account
        assertThrows(IllegalArgumentException.class, ()-> atm.transfer("test2", "test2", 1093)); //transfer to same account


        //check for insufficient funds
        assertThrows(InsufficientFundsException.class, ()-> atm.transfer("test1", "test2", 10000));
        assertThrows(InsufficientFundsException.class, ()-> atm.transfer("test2", "test1", 2349.99));
        assertThrows(InsufficientFundsException.class, ()-> atm.transfer("test1", "test2", 8150.03));
        assertThrows(InsufficientFundsException.class, ()-> atm.transfer("test2", "test1", 8150.03));

    }

    @Test
    void transactionHistoryTest() throws InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        ATM atm = new ATM(bank);
        BankTeller teller = new BankTeller(bank);
        teller.createAccount("123","a@b.com", "testPass", 1000);
        teller.createAccount("456", "b@a.com", "testPass", 500);
        //none
        assertEquals("", atm.transactionHistory("123"));
        //deposit
        atm.deposit("123", 1);
        assertEquals("Deposited $1.0", atm.transactionHistory("123"));
        atm.deposit("123", 785.68);
        assertEquals("Deposited $1.0, Deposited $785.68", atm.transactionHistory("123"));
        //withdraw
        atm.withdraw("123", 20);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0", atm.transactionHistory("123"));
        atm.withdraw("123", 583.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13", atm.transactionHistory("123"));
        //transfers
        atm.transfer("123","456", 10);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456", atm.transactionHistory("123"));
        atm.transfer("456","123", 100.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456, Received $100.13 from Account 456", atm.transactionHistory("123"));

        assertEquals("Received $10.0 from Account 123, Transferred $100.13 to Account 123", atm.transactionHistory("456"));

    }







}
