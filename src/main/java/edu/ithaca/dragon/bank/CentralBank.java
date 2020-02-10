package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentralBank implements AdvancedAPI, AdminAPI {

    //----------------- BasicAPI methods -------------------------//


    public Map<String, BankAccount>accountMap = new HashMap<>();
    public boolean isFrozen;


    public boolean confirmCredentials(String acctId, String password){

        if(accountMap.containsKey(acctId)){
            return accountMap.get(acctId).getPassword().equals(password);
        }



        return false;
    }


    public double checkBalance(String acctId) throws IllegalArgumentException {
        if(!accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        return accountMap.get(acctId).getBalance();
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, IllegalArgumentException {
        if(!accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }

        accountMap.get(acctId).withdraw(amount);
    }


    public void deposit(String acctId, double amount) throws IllegalArgumentException {
        if(!accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with ID" + acctId);
        }

        accountMap.get(acctId).deposit(amount);


    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws IllegalArgumentException, InsufficientFundsException {
        if(!accountMap.containsKey(acctIdToWithdrawFrom) || !accountMap.containsKey(acctIdToDepositTo) || acctIdToWithdrawFrom.equals(acctIdToDepositTo)){
            throw new IllegalArgumentException("Account does not exist with IDs given");

        }

        if(accountMap.get(acctIdToWithdrawFrom).getBalance() < amount){
            throw new InsufficientFundsException("Account does not have enough money");
        }


        accountMap.get(acctIdToWithdrawFrom).transfer(amount, accountMap.get(acctIdToDepositTo));



    }

    public String transactionHistory(String acctId) {

        BankAccount account = accountMap.get(acctId);
        return account.transactionHistory();
    }


    //----------------- AdvancedAPI methods -------------------------//


    public void createAccount(String acctId, String email, String password, double startingBalance) {
        BankAccount account = new BankAccount(acctId, email, password, startingBalance);
        accountMap.put(acctId, account);

    }




    public void closeAccount(String acctId) {
        accountMap.remove(acctId);





    }


    //------------------ AdminAPI methods -------------------------//
    public Map<String, BankAccount>suspiciousMap = new HashMap<>();


    public double calcTotalAssets() {
        double total = 0;

        for(BankAccount account : accountMap.values()){
            total = total + account.getBalance();
        }

        return total;
    }

    public Map<String, BankAccount> getSuspiciousMap() {
        return suspiciousMap;
    }

    public Map<String, BankAccount> findAcctIdsWithSuspiciousActivity() {
        return null;
    }

    public void freezeAccount(String acctId) {
        accountMap.get(acctId).freeze();
    }

    public void unfreezeAcct(String acctId) {
        accountMap.get(acctId).unfreeze();
    }

    public boolean getIsFrozen(String acctId){
        return accountMap.get(acctId).getIsFrozen();
    }



}
