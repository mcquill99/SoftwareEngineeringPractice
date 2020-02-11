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

        if(bank.getAccount(acctId) != null){
            return bank.getAccount(acctId).getPassword().equals(password);
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
        if(bank.getAccount(acctId) == null){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        return bank.getAccount(acctId).getBalance();
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
        if(bank.getAccount(acctId) == null){
            throw new IllegalArgumentException("Account does not exist with name: " + acctId);
        }
        if(bank.getAccount(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }


        bank.getAccount(acctId).withdraw(amount);
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
        if(bank.getAccount(acctId) == null){
            throw new IllegalArgumentException("Account does not exist with ID" + acctId);
        }

        if(bank.getAccount(acctId).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        bank.getAccount(acctId).deposit(amount);


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
        if(bank.getAccount(acctIdToWithdrawFrom) == null || bank.getAccount(acctIdToDepositTo) == null){
            throw new IllegalArgumentException("Account does not exist with IDs given");

        }
        if(bank.getAccount(acctIdToWithdrawFrom).getIsFrozen() || bank.getAccount(acctIdToDepositTo).getIsFrozen()){
            throw new AccountFrozenException("Account is frozen and cannot complete transaction");
        }

        bank.getAccount(acctIdToWithdrawFrom).transfer(amount, bank.getAccount(acctIdToDepositTo));



    }

    /**
     * Description: Shows all transactions made by a user based on its acctId since the beginning of time
     * @param acctId
     * @return String of all transactions made
     */
    public String transactionHistory(String acctId) {

        BankAccount account = bank.getAccount(acctId);
        return account.transactionHistory();
    }
}
