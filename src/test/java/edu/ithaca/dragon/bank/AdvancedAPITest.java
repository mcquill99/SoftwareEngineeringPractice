package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvancedAPITest {

    @Test
    void createAccountTest(){
        CentralBank bank = new CentralBank();
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("1245", "a1@hello.com", "testpassword", 500, false);
        assertNull( bank.getAccount("12466"));
        assertEquals(500, bank.getAccount("1245").getBalance());
        assertEquals("a1@hello.com", bank.getAccount("1245").getEmail());
        assertEquals("testpassword", bank.getAccount("1245").getPassword());


        teller.createAccount("BH8525", "atest3@gmail.com", "funny", 1000, true);
        assertNull(bank.getAccount("BH85425"));
        assertEquals(1000, bank.getAccount("BH8525").getBalance());
        assertEquals("atest3@gmail.com", bank.getAccount("BH8525").getEmail());
        assertEquals("funny", bank.getAccount("BH8525").getPassword());
        assertThrows(IllegalArgumentException.class, ()-> bank.getAccount("BH8525").withdraw(700));
        bank.getAccount("BH8525").compoundInterest();
        assertEquals(1050, bank.getAccount("BH8525").getBalance());

    }


    @Test
    void closeAccountTest() {
        CentralBank bank = new CentralBank();
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        teller.createAccount("1245", "a1@hello.com", "testpassword", 500, false);
        assertEquals("a1@hello.com", bank.getAccount("1245").getEmail());
        teller.closeAccount("1245");
        assertNull(bank.getAccount("1245"));

        teller.createAccount("BH8525", "atest3@gmail.com", "funny", 1000, false);
        assertEquals("atest3@gmail.com", bank.getAccount("BH8525").getEmail());
        teller.closeAccount("BH8525");
        assertNull(bank.getAccount("BH8525"));

    }

}
