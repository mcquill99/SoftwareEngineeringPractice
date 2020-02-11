package edu.ithaca.dragon.bank;

public class CheckingAccount extends BankAccount {
    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public CheckingAccount(String acctId, String email, String password, double startingBalance) {
        super(acctId, email, password, startingBalance);
    }
}
