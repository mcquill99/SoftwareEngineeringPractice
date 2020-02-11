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
        return bank.totalBalance();
    }

    public Collection<String> findAcctIdsWithSuspiciousActivity() {
        return bank.suspiciousAccounts();
    }

    public void freezeAccount(String acctId) {

        bank.getAccount(acctId).toFreeze(true);
    }

    public void unfreezeAcct(String acctId){

        bank.getAccount(acctId).toFreeze(false);
    }

    public boolean getIsFrozen(String acctId){
        return bank.getAccount(acctId).getIsFrozen();
    }



}
