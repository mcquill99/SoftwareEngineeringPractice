package edu.ithaca.dragon.bank;

import java.util.Collection;

public class BankTeller extends ATM implements AdvancedAPI  {

    //----------------- AdvancedAPI methods -------------------------//


    public BankTeller(CentralBank bank){
        super(bank);
    }

    /***
     *Description: creates new bank Account and adds it to account Map in Central bank
     * @param acctId
     * @param email
     * @param password
     * @param startingBalance
     * return : none
     */
    public void createAccount(String acctId, String email, String password, double startingBalance, boolean isSavings) {
        if(isSavings){
            SavingsAccount account = new SavingsAccount(acctId, email, password, startingBalance, 5, 500);
            bank.addAccount(acctId, account);
        }
        else{
            BankAccount account = new CheckingAccount(acctId, email, password, startingBalance);
            bank.addAccount(acctId, account);
        }

    }


    /**
     * Descriptions: Closes account and removes it from central bank map
     * @param acctId
     * return : none
     */
    public void closeAccount(String acctId) {
        bank.removeAccount(acctId);
    }


}
