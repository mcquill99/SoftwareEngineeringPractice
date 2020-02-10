package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AdminSoftware extends CentralBank implements AdminAPI {



    //------------------ AdminAPI methods -------------------------//

    public double calcTotalAssets() {
        double total = 0;

        for(BankAccount account : accountMap.values()){
            total = total + account.getBalance();
        }

        return total;
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        return null;
    }

    public void freezeAccount(String acctId) {

        accountMap.get(acctId).freeze();
    }

    public void unfreezeAcct(String acctId){

        accountMap.get(acctId).unfreeze();
    }

    public boolean getIsFrozen(String acctId){
        return accountMap.get(acctId).getIsFrozen();
    }



}
