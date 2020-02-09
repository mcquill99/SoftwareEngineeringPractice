package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class BankAccount {

    private String email;
    private double balance;
    private String password;
    private String acctId;
    private boolean isFrozen;
    private ArrayList<String> transactionHistory = new ArrayList<String>();



    public BankAccount(String email, double startingBalance) {
        this("", email, "", startingBalance);
    }

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String acctId, String email, String password, double startingBalance){
        if (isEmailValid(email)){
            if(isAmountValid(startingBalance)){
                this.acctId = acctId;
                this.email = email;
                this.balance = startingBalance;
                this.password = password;
                this.isFrozen = false;
            }
            else{
                throw new IllegalArgumentException("starting Balance of " + startingBalance + "is an invalid amount to add");
            }
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * throws InsufficientFundsException if the amount is larger than the balance
     * If balance is negative or has more than 2 decimal places, throws IllegalArgumentException
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException  {
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount must have 2 decimal places and must be positive ");
        }
        if (balance >= amount && amount >= 0) {
            balance -= amount;

            transactionHistory.add("Withdrew $" + amount);
        }
        else{
            throw new InsufficientFundsException("Amount requested is more than in your account by " + (amount - balance));
        }

    }

    /**
     * @post adds to the balance by amount if amount is non-negative and has 2 or less decimal places
     * @throws IllegalArgumentException if amount is negative or has more than 2 decimal places
     */
    public void deposit(double amount) throws IllegalArgumentException{
        if(!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount must have 2 or less decimal places and must be positive");
        }
        balance += amount;

        transactionHistory.add("Deposited $" + amount);
    }

    /**
     * @post transfers funds from one bank account to the one passed in, as long as amount is non-negative and has 2 or less decimal places
     * @throws IllegalArgumentException if amount is negative or has more than 2 decimal places, or if bankAccount to transfer to is the current one
     * @throws InsufficientFundsException if amount is larger than current bank account balance
     */
    public void transfer(double amount, BankAccount toTransfer) throws InsufficientFundsException, IllegalArgumentException{
            if(!isAmountValid(amount) || toTransfer == this){
                throw new IllegalArgumentException("Amount must be positive, have 2 decimals or less, and transfer to a new bank account");
            }
            this.withdraw(amount);
            toTransfer.deposit(amount);

            transactionHistory.remove(transactionHistory.size()-1);
            toTransfer.transactionHistory.remove(toTransfer.transactionHistory.size()-1);

            transactionHistory.add("Transferred $" + amount + " to Account " + toTransfer.acctId);
            toTransfer.transactionHistory.add("Received $" + amount + " from Account " + acctId);
    }

    /**
     * @post checks to see if a double is a valid input to be withdrawn
     * Returns false if double has more than 2 decimal places, or is negative
     */
    public static boolean isAmountValid(double amount){
        double positiveRoundOff = Math.abs(Math.round(amount * 100.0) / 100.0);
        if(amount != positiveRoundOff){

            String doubleCheck = Double.toString(Math.abs(amount));
            int integerPlaces = doubleCheck.indexOf('.');
            int decimalPlaces = doubleCheck.length() - integerPlaces - 1;
            return (decimalPlaces <= 2 || doubleCheck.indexOf('E') != -1) && !(amount < 0);
        }
        return true;
    }

    /**
     * @post checks if an email is valid based on specific guidelines
     * @return true if email is valid, false if not
     */
    public static boolean isEmailValid(String email){
        return email.matches("(\\w)+((_|\\.|-)+\\w+)*@(\\w)+((-)?\\w+)*\\.\\w{2,}$");
    }

    /**
     * a function to return the password of the account
     * @return account password
     */
    public String getPassword() {
        return password;
    }

    /**
     * freezes the account, preventing transactions
     */
    public void freeze(){
        isFrozen = true;
    }

    /**
     * unfreezes the account
     */
    public void unfreeze(){
        isFrozen = false;
    }

    /**
     * returns the current frozen state of the account
     * @return isFrozen
     */
    public boolean getIsFrozen(){
        return isFrozen;
    }

    /**
     * returns all transactions that have occurred on the account
     * This includes deposits, withdraws, and transfers
     * @return a string of all transactions
     */
    public String transactionHistory(){
        if(transactionHistory.size() == 0){
            return "";
        }

        String transactionString  = transactionHistory.toString();
        return(transactionString.substring(1, transactionString.length()-1)); //removes brackets

    }
}
