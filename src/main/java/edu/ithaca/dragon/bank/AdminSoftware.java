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
        Collection<BankAccount> acctsInBank = bank.accountMap.values();

        Iterator<BankAccount> iterator = acctsInBank.iterator();

        while (iterator.hasNext()) {
            if(iterator.next().getIsSus() == true){
                susAccts.add(iterator.next().getAcctId());
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
