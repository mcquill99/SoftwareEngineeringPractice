package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        ArrayList<String> susAccts;
        ArrayList<BankAccount> acctsInBank = accountMap.values();

        for(int i=0; i <= acctsInBank; i++){
            if(acctsInBank.get(i).getIsSus() == true){
                susAccts.add(acctsInBank.getId());
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
