package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

public class AdminSoftware  implements AdminAPI {



    //------------------ AdminAPI methods -------------------------//

    private CentralBank bank;

    //constructor
    public AdminSoftware(CentralBank bank){
        this.bank = bank;
    }

    /**
     * calculates the total of every balance for each bank account in the bank
     * @return total
     */
    public double calcTotalAssets() {
        double total = 0;

        for(BankAccount account : bank.accountMap.values()){
            total = total + account.getBalance();
        }

        return total;
    }

    /**
     * finds all accounts that have been labeled as having suspicious activity
     * and return a collection those accounts
     * @return susAccts
     */
    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        Collection<String> susAccts = new ArrayList<>();
        Iterator item = bank.accountMap.entrySet().iterator();

        while (item.hasNext()) {
            Map.Entry entry = (Map.Entry) item.next();
            BankAccount accountToLookAt = (BankAccount) entry.getValue();
            String keyToLookAt = (String) entry.getKey();
            if(accountToLookAt.getIsSus() == true){
                susAccts.add(keyToLookAt);
            }
        }
        return susAccts;
    }

    /**
     * freezes a given account
     * @param acctId
     */
    public void freezeAccount(String acctId) {

        bank.accountMap.get(acctId).freeze();
    }

    /**
     * unfreezes a given account
     * @param acctId
     */
    public void unfreezeAcct(String acctId){

        bank.accountMap.get(acctId).unfreeze();
    }

    /**
     * returns the frozen status of a given account
     * @param acctId
     * @return value of isFrozen
     */
    public boolean getIsFrozen(String acctId){
        return bank.accountMap.get(acctId).getIsFrozen();
    }



}
