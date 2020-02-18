package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CentralBankTest {
/*
    @Test
    public void addAccountTest(){
        CentralBank bank = new CentralBank();
        CheckingAccount acct1 = new CheckingAccount("1234", "a@b.com", "pass", 1000);
        bank.addAccount("1234", acct1);
        CheckingAccount acct2 = bank.getAccount("1234");
        assertEquals(acct1, acct2);

    }

    public void removeAccountTest(){
        CentralBank bank = new CentralBank();
        CheckingAccount acct1 = new CheckingAccount("1234", "a@b.com", "pass", 1000);
        bank.addAccount("1234", acct1);
        bank.removeAccount("1234");
        assertEquals(null, bank.getAccount("1234"));

    }*/ //Tests already covered in admin and teller tests

    public static CentralBank buildTestObject(){
        CentralBank bank = new CentralBank();
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        AdminSoftware admin = new AdminSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testpassword", 500, false);
        teller.createAccount("1234", "a215@gmail.com", "tester", 1000, false);
        teller.createAccount("5678", "a@b.com", "pass", 1500, false);
        admin.freezeAccount("5678");

        return bank;
    }
}
