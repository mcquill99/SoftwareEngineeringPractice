package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdvancedAPITest {

    @Test
    void createAccountTest(){
        CentralBank bank = new CentralBank();
        bank.teller.createAccount("1245", "a1@hello.com", "testpassword", 500);
        assertNull( bank.accountMap.get("12466"));
        assertEquals(500, bank.accountMap.get("1245").getBalance());
        assertEquals("a1@hello.com", bank.accountMap.get("1245").getEmail());
        assertEquals("testpassword", bank.accountMap.get("1245").getPassword());


        bank.teller.createAccount("BH8525", "atest3@gmail.com", "funny", 1000);
        assertNull(bank.accountMap.get("BH85425"));
        assertEquals(1000, bank.accountMap.get("BH8525").getBalance());
        assertEquals("atest3@gmail.com", bank.accountMap.get("BH8525").getEmail());
        assertEquals("funny", bank.accountMap.get("BH8525").getPassword());

    }


    @Test
    void closeAccountTest() {
        CentralBank bank = new CentralBank();
        bank.teller.createAccount("1245", "a1@hello.com", "testpassword", 500);
        assertEquals("a1@hello.com", bank.accountMap.get("1245").getEmail());
        bank.teller.closeAccount("1245");
        assertNull(bank.accountMap.get("1245"));

        bank.teller.createAccount("BH8525", "atest3@gmail.com", "funny", 1000);
        assertEquals("atest3@gmail.com", bank.accountMap.get("BH8525").getEmail());
        bank.teller.closeAccount("BH8525");
        assertNull(bank.accountMap.get("BH8525"));

    }

}
