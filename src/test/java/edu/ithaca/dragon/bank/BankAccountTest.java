package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {


    @Test
    void getBalanceTest() throws InsufficientFundsException, IllegalArgumentException {
        //classes - fresh account, after withdrawal, after unsuccessful withdrawal
        CheckingAccount checkingAccount = new CheckingAccount("123","a@b.com", "pass",1000);

        //fresh account
        assertEquals(1000, checkingAccount.getBalance());               //equivalence class starting balance and not border cas
        //after withdrawal
        checkingAccount.withdraw(100);
        assertEquals(900, checkingAccount.getBalance());                //equivalence class of less than and not border case
        checkingAccount.withdraw(500);
        assertEquals(400, checkingAccount.getBalance());                //equivalence class of more than and not border case
        checkingAccount.withdraw(400);
        assertEquals(0, checkingAccount.getBalance());                  //equivalence class of zero and border case

        //after unsuccessful withdrawal
        CheckingAccount unsuccessful = new CheckingAccount("123","a@b.com", "pass",1000);
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(1100));       //equivalence class of greater than and border case
        assertEquals(1000, unsuccessful.getBalance());
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(2000));       //equivalence class of middle value and not border case
        assertEquals(1000, unsuccessful.getBalance());
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(Integer.MAX_VALUE));  //equivalence class of Max Value and border case
        assertEquals(1000, unsuccessful.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException, IllegalArgumentException{
        //classes - sufficient funds, insufficient funds, negative funds
        CheckingAccount checkingAccount = new CheckingAccount("123","a@b.com", "pass", 1000);
        //sufficient funds
        checkingAccount.withdraw(0);
        assertEquals(1000, checkingAccount.getBalance());
        checkingAccount.withdraw(100);
        assertEquals(900, checkingAccount.getBalance());            //equivalence class of less than and not border case
        checkingAccount.withdraw(500);
        assertEquals(400, checkingAccount.getBalance());            //equivalence class of more than and not border case
        checkingAccount.withdraw(400);
        assertEquals(0, checkingAccount.getBalance());              //equivalence class of zero and border case
        //insufficient funds
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(300));
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(max));
        assertThrows(InsufficientFundsException.class, () -> checkingAccount.withdraw(1));
        //negative numbers
        CheckingAccount negative = new CheckingAccount("123","a@b.com","pass", 1000);
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-100));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-500));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and not border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(min));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        //numbers with more than 2 decimals
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(300.001));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(1000.04940));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and not border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(50.1029384958674950));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        //negative numbers with more than 2 decimals
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-100.001));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-100.10239485));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and not border case
        assertThrows(IllegalArgumentException.class, () -> checkingAccount.withdraw(-100.1029384758493815));
        assertEquals(1000, negative.getBalance());          //equivalence class of negative balance and border case




    }

    @Test
    void isEmailValidTest(){
        //one @ symbol class
        assertTrue(CheckingAccount.isEmailValid( "a.b.c@b.com"));                   //equivalence class of one @ and not border case
        assertFalse(CheckingAccount.isEmailValid("abc@def@mail.com"));          //equivalence class of multiple @ and border case
        assertFalse(CheckingAccount.isEmailValid("abc@d@ef@mail.com"));         //equivalence class of multiple @ and border case
        assertFalse(CheckingAccount.isEmailValid("abc@d@ef@ma@il.com"));        //equivalence class of multiple @ and border case
        assertFalse( CheckingAccount.isEmailValid(""));                         //equivalence class of one no @ and border case
        //valid special characters in prefix
        assertFalse(CheckingAccount.isEmailValid("abc-@mail.com"));             //equivalence class  of one valid special characters and not border case
        assertFalse(CheckingAccount.isEmailValid("abc..@mail.com"));            //equivalence class  of two valid special characters and not border case
        assertFalse(CheckingAccount.isEmailValid(".abc@mail.com"));             //equivalence class  of valid special characters and not border case
        //invalid characters in prefix
        assertFalse(CheckingAccount.isEmailValid("abc#def@mail.com"));          //equivalence class  of one invalid characters and border case
        assertFalse(CheckingAccount.isEmailValid("abc#de!f@mail.com"));         //equivalence class  of two invalid characters and border case
        assertTrue(CheckingAccount.isEmailValid("abc.def@mail.com"));           //equivalence class  of one invalid characters and not border case
        //invalid suffix characters
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail#archive.com"));  //equivalence class  of invalid suffix characters and border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail!ar%chive.com")); //equivalence class  of invalid suffix characters and border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail!ar%chi.ve.com"));    //equivalence class  of invalid suffix characters and border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail--archive.com")); //equivalence class  of invalid suffix characters and border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail-arc!h.ive.com"));    //equivalence class  of invalid suffix characters and border case
        //valid domain
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail.c"));            //equivalence class  of invalid domain and not border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail"));              //equivalence class  of no domain and border case
        assertFalse(CheckingAccount.isEmailValid("abc.def@mail..com"));         //equivalence class  of two . in domain and border case
        assertTrue(CheckingAccount.isEmailValid("abc.def@mail.cc"));            //equivalence class  of invalid domain and not border case

    }

    @Test
    void constructorTest() {
        CheckingAccount checkingAccount = new CheckingAccount("123","a@b.com","pass", 200);

        assertEquals("a@b.com", checkingAccount.getEmail());
        assertEquals(200, checkingAccount.getBalance());
        //check for exception thrown correctly for email
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("", "100", "pass", 100));
        //checks if it throws an argument for negative numbers
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", -1));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com", "pass", -150));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","passs", -10000000));
        //checks if it throws an argument for numbers with more than 2 decimal places
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", 100.001));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", 150.01020495));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", -123.1029384758495837));
        //checks if it throws an argument for numbers that are negative and have more than 2 decimal places
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com", "pass", -1.001));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", -120.123453));
        assertThrows(IllegalArgumentException.class, ()-> new CheckingAccount("123","a@b.com","pass", -100.102938456744854));
    }

    @Test
    void isAmountValidTest(){
        //valid number, no decimals
        assertTrue(CheckingAccount.isAmountValid(0));
        assertTrue(CheckingAccount.isAmountValid(1));
        assertTrue(CheckingAccount.isAmountValid(500));
        assertTrue(CheckingAccount.isAmountValid(678));
        assertTrue(CheckingAccount.isAmountValid(Integer.MAX_VALUE));
        //valid number, 1 decimal
        assertTrue(CheckingAccount.isAmountValid(500.0));
        assertTrue(CheckingAccount.isAmountValid(500.1));
        assertTrue(CheckingAccount.isAmountValid(500.5));
        assertTrue(CheckingAccount.isAmountValid(500.9));
        //valid number, 2 decimals
        assertTrue(CheckingAccount.isAmountValid(500.00));
        assertTrue(CheckingAccount.isAmountValid(500.01));
        assertTrue(CheckingAccount.isAmountValid(500.10));
        assertTrue(CheckingAccount.isAmountValid(500.62));
        assertTrue(CheckingAccount.isAmountValid(500.99));
        //invalid number, more than 2 decimals
        assertTrue(CheckingAccount.isAmountValid(500.00000000));
        assertFalse(CheckingAccount.isAmountValid(500.001));
        assertFalse(CheckingAccount.isAmountValid(500.597));
        assertFalse(CheckingAccount.isAmountValid(500.56690930452));
        assertFalse(CheckingAccount.isAmountValid(500.999));
        assertFalse(CheckingAccount.isAmountValid(500.2048675849586746));
        //invalid number, negative with 0 decimals
        assertFalse(CheckingAccount.isAmountValid(-1));
        assertFalse(CheckingAccount.isAmountValid(-100));
        assertFalse(CheckingAccount.isAmountValid(Integer.MIN_VALUE));
        //invalid number, negative with 1 decimal
        assertFalse(CheckingAccount.isAmountValid(-1.0));
        assertFalse(CheckingAccount.isAmountValid(-100.7));
        assertFalse(CheckingAccount.isAmountValid(-999999.9));
        //invalid number, negative with 2 decimals
        assertFalse(CheckingAccount.isAmountValid(-1.00));
        assertFalse(CheckingAccount.isAmountValid(-100.59));
        assertFalse(CheckingAccount.isAmountValid(-999999999999.99));
        //invalid number, negative with more than 2 decimals
        assertFalse(CheckingAccount.isAmountValid(-100.001));
        assertFalse(CheckingAccount.isAmountValid(-100.5689));
        assertFalse(CheckingAccount.isAmountValid(-100.5784939576859));
        assertFalse(CheckingAccount.isAmountValid(-999.9999999999999999));

    }

    @Test
    void depositTest(){
        CheckingAccount checkingAccount = new CheckingAccount("123","a@b.com","pass", 1000);
        //deposit valid integer
        checkingAccount.deposit(1);
        assertEquals(1001, checkingAccount.getBalance());
        checkingAccount.deposit(100);
        assertEquals(1101, checkingAccount.getBalance());
        checkingAccount.deposit(10000);
        assertEquals(11101, checkingAccount.getBalance());
        //deposit valid double with < 1 decimal
        checkingAccount.deposit(1.1);
        assertEquals(11102.1, checkingAccount.getBalance());
        checkingAccount.deposit(10.5);
        assertEquals(11112.6, checkingAccount.getBalance());
        checkingAccount.deposit(10.9);
        assertEquals(11123.5, checkingAccount.getBalance());
        //deposit valid with 2 decimals
        checkingAccount.deposit(1.00);
        assertEquals(11124.50, checkingAccount.getBalance());
        checkingAccount.deposit(1.11);
        assertEquals(11125.61, checkingAccount.getBalance());
        checkingAccount.deposit(1.57);
        assertEquals(11127.18, checkingAccount.getBalance());
        checkingAccount.deposit(1.99);
        assertEquals(11129.17, checkingAccount.getBalance());
        //invalid number, negative
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-500));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(Integer.MIN_VALUE));
        assertEquals(11129.17, checkingAccount.getBalance());
        //invalid number, negative, one decimal
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.1));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.5));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.9));
        assertEquals(11129.17, checkingAccount.getBalance());
        //invalid number, negative, 2 decimals
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.00));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.57));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.99));
        assertEquals(11129.17, checkingAccount.getBalance());
        //invalid number, more than 2 decimals
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(1.001));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(-1.585976));
        assertEquals(11129.17, checkingAccount.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount.deposit(50.102938475960794));
        assertEquals(11129.17, checkingAccount.getBalance());


    }

    @Test
    void transferTest() throws InsufficientFundsException,IllegalArgumentException{
        CheckingAccount checkingAccount1 = new CheckingAccount("123","a@b.com","pass", 1000);
        CheckingAccount checkingAccount2 = new CheckingAccount("123","b@a.com", "pass", 0);
        //checking initial balances
        assertEquals(1000, checkingAccount1.getBalance());
        assertEquals(0, checkingAccount2.getBalance());
        //transfer from 1 to 2, integers, valid amount
        checkingAccount1.transfer(1, checkingAccount2);
        assertEquals(999, checkingAccount1.getBalance());
        assertEquals(1, checkingAccount2.getBalance());
        checkingAccount1.transfer(99, checkingAccount2);
        assertEquals(900, checkingAccount1.getBalance());
        assertEquals(100, checkingAccount2.getBalance());
        checkingAccount1.transfer(900, checkingAccount2);
        assertEquals(0, checkingAccount1.getBalance());
        assertEquals(1000, checkingAccount2.getBalance());
        //transfer from 2 to 1, 1 decimal, valid amount
        checkingAccount2.transfer(0.1, checkingAccount1);
        assertEquals(0.1, checkingAccount1.getBalance());
        assertEquals(999.9, checkingAccount2.getBalance());
        checkingAccount2.transfer(99.5, checkingAccount1);
        assertEquals(99.6, checkingAccount1.getBalance());
        assertEquals(900.4, checkingAccount2.getBalance());
        //transfer from 2 to 1, 2 decimals, valid amount
        checkingAccount2.transfer(50.41, checkingAccount1);
        assertEquals(150.01, checkingAccount1.getBalance());
        assertEquals(849.99, checkingAccount2.getBalance());
        checkingAccount2.transfer(50.11, checkingAccount1);
        assertEquals(200.12, checkingAccount1.getBalance());
        assertEquals(799.88, checkingAccount2.getBalance());
        checkingAccount2.transfer(50.99, checkingAccount1);
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        //invalid transfer, same bank
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(50, checkingAccount1));
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount2.transfer(50, checkingAccount2));
        //invalid transfer, negative numbers
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(-1, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(-50.8, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(-500.99, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        //invalid transfer, 3 or more decimals
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(50.001, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(50.9484930, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        assertThrows(IllegalArgumentException.class, ()-> checkingAccount1.transfer(-50.102938495869503, checkingAccount2));
        assertEquals(251.11, checkingAccount1.getBalance());
        assertEquals(748.89, checkingAccount2.getBalance());
        //invalid transfer, insufficient funds
        checkingAccount1.transfer(251.11, checkingAccount2);

        assertThrows(InsufficientFundsException.class, ()-> checkingAccount1.transfer(1, checkingAccount2));
        assertEquals(0, checkingAccount1.getBalance());
        assertEquals(1000, checkingAccount2.getBalance());
        assertThrows(InsufficientFundsException.class, ()-> checkingAccount1.transfer(246, checkingAccount2));
        assertEquals(0, checkingAccount1.getBalance());
        assertEquals(1000, checkingAccount2.getBalance());
        assertThrows(InsufficientFundsException.class, ()-> checkingAccount1.transfer(Integer.MAX_VALUE, checkingAccount2));
        assertEquals(0, checkingAccount1.getBalance());
        assertEquals(1000, checkingAccount2.getBalance());

    }

    @Test
    void SavingsAccountTest() throws IllegalArgumentException, InsufficientFundsException{
        //constructor test
        SavingsAccount savingsAccount = new SavingsAccount("123","a@b.com","pass", 1000, 5, 500);

        //invalid interest
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123","a@b.com", "pass", 1000, -0.1, 500));
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123", "a@b.com","pass", 1000, -50.6, 500));
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123","a@b.com", "pass", 1000, -150.6, 500));

        //invalid maxWithdraw
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123","a@b.com", "pass", 1000, 5.0, 0));
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123","a@b.com", "pass",1000, 5.0, -1));
        assertThrows(IllegalArgumentException.class, ()-> new SavingsAccount("123","a@b.com", "pass",1000, 5.0, -500.495));

        //Compound interest
        savingsAccount.compoundInterest();
        assertEquals(1050, savingsAccount.getBalance());
        savingsAccount.compoundInterest();
        assertEquals(1102.5, savingsAccount.getBalance());

        //Overridden Withdraw
        savingsAccount.withdraw(102.5);
        assertEquals(1000, savingsAccount.getBalance());

        assertThrows(IllegalArgumentException.class, () -> savingsAccount.withdraw(501));
        assertThrows(IllegalArgumentException.class, () -> savingsAccount.withdraw(750.59));
        assertThrows(IllegalArgumentException.class, () -> savingsAccount.withdraw(1000.75));
    }



}