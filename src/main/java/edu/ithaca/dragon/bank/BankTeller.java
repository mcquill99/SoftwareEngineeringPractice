package edu.ithaca.dragon.bank;

import java.util.Collection;

public class BankTeller extends ATM implements AdvancedAPI  {

    //----------------- AdvancedAPI methods -------------------------//


    public BankTeller(CentralBank bank){
        super(bank);
    }
    public void createAccount(String acctId, String email, String password, double startingBalance) {
        BankAccount account = new BankAccount(acctId, email, password, startingBalance);
        bank.accountMap.put(acctId, account);

    }




    public void closeAccount(String acctId) {
        bank.accountMap.remove(acctId);
    }


}
