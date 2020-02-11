package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

public class AdminSoftware  implements AdminAPI {



    //------------------ AdminAPI methods -------------------------//

    private CentralBank bank;

    public AdminSoftware(CentralBank bank){
        this.bank = bank;
    }
    public double calcTotalAssets() {
        double total = 0;

        for(BankAccount account : bank.accountMap.values()){
            total = total + account.getBalance();
        }

        return total;
    }

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

    public void freezeAccount(String acctId) {

        bank.accountMap.get(acctId).freeze();
    }

    public void unfreezeAcct(String acctId){

        bank.accountMap.get(acctId).unfreeze();
    }

    public boolean getIsFrozen(String acctId){
        return bank.accountMap.get(acctId).getIsFrozen();
    }



}
