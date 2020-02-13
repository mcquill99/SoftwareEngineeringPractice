package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentralBank {


    private Map<String, CheckingAccount>accountMap = new HashMap<>();



    public CheckingAccount getAccount(String acctId){
        return accountMap.get(acctId);
    }

    public void addAccount(String acctId, CheckingAccount account){
        accountMap.put(acctId, account);
    }

    public void removeAccount(String acctId){
        accountMap.remove(acctId);
    }

    public double totalBalance(){
        double total = 0;
        for(CheckingAccount account : accountMap.values()){
            total = total + account.getBalance();
        }

        return total;

    }

    public Collection<String> suspiciousAccounts(){
        ArrayList<String> suspiciousAccounts = new ArrayList<>();

        for(CheckingAccount account : accountMap.values()){
            if(account.getIsSus()){
                suspiciousAccounts.add(account.getAcctId());
            }
        }

        return suspiciousAccounts;
    }




}
