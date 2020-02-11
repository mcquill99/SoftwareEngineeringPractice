package edu.ithaca.dragon.bank;

import java.util.HashMap;
import java.util.Map;

public class ATM implements BasicAPI{

    //----------------- BasicAPI methods -------------------------//


    public boolean isFrozen;
    protected CentralBank bank;

    public ATM(CentralBank bank){
        this.bank = bank;
    }
    public boolean confirmCredentials(String acctId, String password){

        if(bank.accountMap.containsKey(acctId)){
            return bank.accountMap.get(acctId).getPassword().equals(password);
        }



        return false;
    }


    public double checkBalance(String acctId) throws IllegalArgumentException {
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        return bank.accountMap.get(acctId).getBalance();
    }

    public void withdraw(String acctId, double amount) throws InsufficientFundsException, IllegalArgumentException, AccountFrozenException{
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        if(bank.accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }


        bank.accountMap.get(acctId).withdraw(amount);
    }


    public void deposit(String acctId, double amount) throws IllegalArgumentException, AccountFrozenException {
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with ID" + acctId);
        }

        if(bank.accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        bank.accountMap.get(acctId).deposit(amount);


    }

    public void transfer(String acctIdToWithdrawFrom, String acctIdToDepositTo, double amount) throws IllegalArgumentException, InsufficientFundsException, AccountFrozenException {
        if(!bank.accountMap.containsKey(acctIdToWithdrawFrom) || !bank.accountMap.containsKey(acctIdToDepositTo) || acctIdToWithdrawFrom.equals(acctIdToDepositTo)){
            throw new IllegalArgumentException("Account does not exist with IDs given");

        }

        if(bank.accountMap.get(acctIdToWithdrawFrom).getBalance() < amount){
            throw new InsufficientFundsException("Account does not have enough money");
        }
        if(bank.accountMap.get(acctIdToWithdrawFrom).getIsFrozen() || bank.accountMap.get(acctIdToDepositTo).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        bank.accountMap.get(acctIdToWithdrawFrom).transfer(amount, bank.accountMap.get(acctIdToDepositTo));



    }

    public String transactionHistory(String acctId) {

        BankAccount account = bank.accountMap.get(acctId);
        return account.transactionHistory();
    }
}
