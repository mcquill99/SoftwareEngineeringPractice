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


    /***
     * Description : make sure acct is included within the Central Bank map and confirm password
     * to make transactions
     * @param acctId
     * @param password
     * @return boolean if credentials match
     */
    public boolean confirmCredentials(String acctId, String password){

        if(bank.accountMap.containsKey(acctId)){
            return bank.accountMap.get(acctId).getPassword().equals(password);
        }



        return false;
    }

    /**
     * Description : Checks balance if account is in map
     * @param acctId
     * @return balance for account
     * @throws IllegalArgumentException
     */
    public double checkBalance(String acctId) throws IllegalArgumentException {
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        return bank.accountMap.get(acctId).getBalance();
    }


    /**
     * Description : User withdraws money from account and subtracts from total balance
     * @param acctId
     * @param amount
     * @throws InsufficientFundsException
     * @throws IllegalArgumentException
     * @throws AccountFrozenException
     * return: none
     */
    public void withdraw(String acctId, double amount) throws InsufficientFundsException, IllegalArgumentException, AccountFrozenException{
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        if(bank.accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }


        bank.accountMap.get(acctId).withdraw(amount);
    }


    /**
     * Description: Deposit money to users overall balance and will increase
     * @param acctId
     * @param amount
     * @throws IllegalArgumentException
     * @throws AccountFrozenException
     * @return none
     */
    public void deposit(String acctId, double amount) throws IllegalArgumentException, AccountFrozenException {
        if(!bank.accountMap.containsKey(acctId)){
            throw new IllegalArgumentException("Account does not exist with ID" + acctId);
        }

        if(bank.accountMap.get(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        bank.accountMap.get(acctId).deposit(amount);


    }

    /**
     * Description: finds two accounts and withdraws money from one account and adds it to another account
     * @param acctIdToWithdrawFrom
     * @param acctIdToDepositTo
     * @param amount
     * @throws IllegalArgumentException
     * @throws InsufficientFundsException
     * @throws AccountFrozenException
     */

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

    /**
     * Description: Shows all transactions made by a user based on its acctId since the beginning of time
     * @param acctId
     * @return String of all transactions made
     */
    public String transactionHistory(String acctId) {

        BankAccount account = bank.accountMap.get(acctId);
        return account.transactionHistory();
    }
}
