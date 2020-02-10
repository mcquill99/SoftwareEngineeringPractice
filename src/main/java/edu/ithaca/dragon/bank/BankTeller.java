package edu.ithaca.dragon.bank;

import java.util.Collection;

public class BankTeller extends CentralBank implements AdminAPI  {

    //----------------- AdvancedAPI methods -------------------------//


    public void createAccount(String acctId, String email, String password, double startingBalance) {
        BankAccount account = new BankAccount(acctId, email, password, startingBalance);
        accountMap.put(acctId, account);

    }




    public void closeAccount(String acctId) {
        accountMap.remove(acctId);
    }

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

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, IllegalArgumentException, AccountFrozenException{
        if(!accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        if(accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }


        accountMap.get(acctId).withdraw(amount);
    }


    public void deposit(String acctId, double amount) throws IllegalArgumentException, AccountFrozenException {
        if(!accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with ID" + acctId);
        }

        if(accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        accountMap.get(acctId).deposit(amount);


    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        if(!accountMap.containsKey(acctIdToWithdrawFrom) || !accountMap.containsKey(acctIdToDepositTo) || acctIdToWithdrawFrom.equals(acctIdToDepositTo)){
            throw new IllegalArgumentException("Account does not exist with IDs given");

        }

        if(accountMap.get(acctIdToWithdrawFrom).getBalance() < amount){
            throw new InsufficientFundsException("Account does not have enough money");
        }
        if(accountMap.get(acctIdToWithdrawFrom).getIsFrozen() || accountMap.get(acctIdToDepositTo).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        accountMap.get(acctIdToWithdrawFrom).transfer(amount, accountMap.get(acctIdToDepositTo));



    }

    public String transactionHistory(String acctId) {

        BankAccount account = accountMap.get(acctId);
        return account.transactionHistory();
    }

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
