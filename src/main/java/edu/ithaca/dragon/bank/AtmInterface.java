package edu.ithaca.dragon.bank;


import java.sql.SQLOutput;
import java.util.Scanner;

public class AtmInterface {

    public static void main(String [] args) throws InsufficientFundsException, AccountFrozenException {

        Scanner read = new Scanner(System.in);

        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testpassword", 500, false);

        teller.createAccount("1234", "a215@gmail.com", "tester", 1000, false);


        System.out.println("Hello, Please log in");

        while (!bankSoftware.confirmCredentials(read.next(), read.next())) {
            System.out.println("Invalid login, try again");
        }

        System.out.println("Please enter action or help for options");


        String action = read.nextLine();

            while (!action.equals("logout")) {
                String stuff = read.nextLine();

                    if (stuff.equals("help")) {
                        System.out.println("Your options are: " +
                                "\n 1. withdraw " +
                                "\n 2. deposit " +
                                "\n 3. transfer " +
                                "\n 4. logout \n ");
                        System.out.println("Please enter action or logout");

                    }

                    if (stuff.equals("withdraw")) {
                        System.out.println("Enter accountID");
                        String acctId = read.nextLine();
                        System.out.println("Enter amount");
                        double amount = read.nextDouble();
                        bankSoftware.withdraw(acctId, amount);
                        System.out.println("Withdraw Complete amount in account now is " + bankSoftware.checkBalance(acctId));
                        System.out.println("Please enter action or logout");
                    }

                    if (stuff.equals("deposit")) {
                        System.out.println("Enter accountID");
                        String acctId = read.nextLine();
                        System.out.println("Enter amount");
                        double amount = read.nextDouble();
                        bankSoftware.deposit(acctId, amount);
                        System.out.println("Deposit Complete, amount in account now is " + bankSoftware.checkBalance(acctId));
                        System.out.println("Please enter action or logout");
                    }

                    if (stuff.equals("transfer")) {
                        System.out.println("Enter accountID to transfer from");
                        String acctId = read.nextLine();
                        System.out.println("Enter accountID To transfer to");
                        String acctId2 = read.nextLine();
                        System.out.println("Enter the amount");
                        double amount = read.nextDouble();
                        bankSoftware.transfer(acctId, acctId2, amount);
                        System.out.println("Transfer Complete, amount in account is now" + bankSoftware.checkBalance(acctId));
                        System.out.println("Please enter action or logout");
                    }

                    if(stuff.equals("logout")){
                        break;
                    }


                }

            }
        }






