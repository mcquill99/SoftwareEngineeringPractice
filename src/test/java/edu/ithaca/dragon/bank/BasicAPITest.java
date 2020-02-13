package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BasicAPITest {

    @Test
    void confirmCredentialsTest(){

        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testingPassword", 500,false);
        teller.createAccount("11BFWGG", "tester@gmail.com", "singleLetter", 1000,false);

        assertFalse(bankSoftware.confirmCredentials("11212", "test"));
        assertFalse( bankSoftware.confirmCredentials("112", "testingPassword"));
        assertTrue( bankSoftware.confirmCredentials("11212", "testingPassword"));

        assertFalse(bankSoftware.confirmCredentials("11bfwgg", "singleLetter"));
        assertFalse(bankSoftware.confirmCredentials("11bfwgg", "SingleLetter"));
        assertTrue(bankSoftware.confirmCredentials("11BFWGG", "singleLetter"));

    }

    @Test
    void checkBalanceTest() throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000,false);
        assertEquals(1000, bankSoftware.checkBalance("test123"));
        //different account names
        bankSoftware.withdraw("test123", 1);
        assertEquals(999, bankSoftware.checkBalance("test123"));
        bankSoftware.withdraw("test123", 250.47);
        assertEquals(748.53, bankSoftware.checkBalance("test123"));
        bankSoftware.withdraw("test123", 748.53);
        assertEquals(0, bankSoftware.checkBalance("test123"));

        teller.createAccount("test456", "b@a.com", "testpass", 3290.57,false);
        assertEquals(3290.57, bankSoftware.checkBalance("test456"));

        teller.createAccount("test789", "b@a.com", "testpass", 999999999.99,false);
        assertEquals(999999999.99, bankSoftware.checkBalance("test789"));



    }

    @Test
    void withdrawTest() throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000,false);
        assertEquals(1000, bankSoftware.checkBalance("test123"));
        //legal withdraws
        bankSoftware.withdraw("test123", 1);
        assertEquals(999, bankSoftware.checkBalance("test123"));
        bankSoftware.withdraw("test123", 250.47);
        assertEquals(748.53, bankSoftware.checkBalance("test123"));
        bankSoftware.withdraw("test123", 748.53);
        assertEquals(0, bankSoftware.checkBalance("test123"));
        //illegal withdraws
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", -1));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", -500.89));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", -1000000.45));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", -1.96646));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", -1.9648690548065809546));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.withdraw("test123", 50.3940685));



    }

    @Test
    void depositTest() throws IllegalArgumentException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("test123", "a@b.com", "testpass", 1000, false);
        bankSoftware.deposit("test123", 100);
        assertEquals(1100, bankSoftware.checkBalance("test123"));
        bankSoftware.deposit("test123", 1000);
        assertEquals(2100, bankSoftware.checkBalance("test123"));
        bankSoftware.deposit("test123", .19);
        assertEquals(2100.19, bankSoftware.checkBalance("test123"));

        //account not found deposits
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.deposit("test13", 50));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.deposit("tes3", 100));
        //illegal deposits
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.deposit("test123", 50.53894329));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.deposit("test123", .3894329));
        //check balance remains the same
        assertEquals(2100.19, bankSoftware.checkBalance("test123"));

    }

    @Test
    void transferTest() throws InsufficientFundsException, IllegalArgumentException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("test1", "a@b.com", "testpass", 10000,false);
        teller.createAccount("test2", "b@c.com", "testpass", 500,false);

        assertEquals(10000, bankSoftware.checkBalance("test1"));
        bankSoftware.transfer("test1", "test2", 500);
        //check legal transfers both ways
        assertEquals(9500, bankSoftware.checkBalance("test1"));
        assertEquals(1000, bankSoftware.checkBalance("test2"));
        bankSoftware.transfer("test1", "test2", 1500);
        assertEquals(8000, bankSoftware.checkBalance("test1"));
        assertEquals(2500, bankSoftware.checkBalance("test2"));
        bankSoftware.transfer("test2", "test1", 150.02);
        assertEquals(8150.02, bankSoftware.checkBalance("test1"));
        assertEquals(2349.98, bankSoftware.checkBalance("test2"));

        //check for illegal transfers
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test1", "test2", .5424506));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test2", "test1", .506));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test2", "test1", -.506));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test1", "test2", 100.64345));
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test1", "test1", 10)); //transfer to same account
        assertThrows(IllegalArgumentException.class, ()-> bankSoftware.transfer("test2", "test2", 1093)); //transfer to same account


        //check for insufficient funds
        assertThrows(InsufficientFundsException.class, ()-> bankSoftware.transfer("test1", "test2", 10000));
        assertThrows(InsufficientFundsException.class, ()-> bankSoftware.transfer("test2", "test1", 2349.99));
        assertThrows(InsufficientFundsException.class, ()-> bankSoftware.transfer("test1", "test2", 8150.03));
        assertThrows(InsufficientFundsException.class, ()-> bankSoftware.transfer("test2", "test1", 8150.03));

    }

    @Test
    void transactionHistoryTest() throws InsufficientFundsException, AccountFrozenException {
        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("123","a@b.com", "testPass", 1000,false);
        teller.createAccount("456", "b@a.com", "testPass", 500,false);
        //none
        assertEquals("", bankSoftware.transactionHistory("123"));
        //deposit
        bankSoftware.deposit("123", 1);
        assertEquals("Deposited $1.0", bankSoftware.transactionHistory("123"));
        bankSoftware.deposit("123", 785.68);
        assertEquals("Deposited $1.0, Deposited $785.68", bankSoftware.transactionHistory("123"));
        //withdraw
        bankSoftware.withdraw("123", 20);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0", bankSoftware.transactionHistory("123"));
        bankSoftware.withdraw("123", 583.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13", bankSoftware.transactionHistory("123"));
        //transfers
        bankSoftware.transfer("123","456", 10);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456", bankSoftware.transactionHistory("123"));
        bankSoftware.transfer("456","123", 100.13);
        assertEquals("Deposited $1.0, Deposited $785.68, Withdrew $20.0, Withdrew $583.13, Transferred $10.0 to Account 456, Received $100.13 from Account 456", bankSoftware.transactionHistory("123"));

        assertEquals("Received $10.0 from Account 123, Transferred $100.13 to Account 123", bankSoftware.transactionHistory("456"));

    }







}
