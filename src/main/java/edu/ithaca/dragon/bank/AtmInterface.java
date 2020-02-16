package edu.ithaca.dragon.bank;


import java.util.Scanner;

public class AtmInterface {

    public static void main(String [] args) throws InsufficientFundsException, AccountFrozenException {

        Scanner read = new Scanner(System.in);

        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testpassword", 500,false);

        teller.createAccount("1234", "a215@gmail.com", "tester", 1000,false);


        System.out.println("Hello, Please log in");

//        if(!bankSoftware.confirmCredentials(read.next(), read.next())){
//            System.out.println("Invalid login, try again");
//            bankSoftware.confirmCredentials(read.next(), read.next());
//        }

        while(bankSoftware.confirmCredentials(read.next(), read.next())){
            System.out.println("What would you like to do? press help for all options");

            String action = read.next();
            if (!action.equals("logout")) {
                String stuff = read.next();

                if (stuff.equals("help")) {
                    System.out.println("Your options are:" +
                            "1. withdraw" +
                            "2. deposit" +
                            "3. transfer" +
                            "4. logout");
                }

                if (stuff.equals("withdraw")) {
                    System.out.println("Enter accountID and amount");
                    bankSoftware.withdraw(read.next(), read.nextDouble());
                    System.out.println("Withdraw Complete");
                }

                if (stuff.equals("deposit")) {
                    System.out.println("Enter accountID and amount");
                    bankSoftware.deposit(read.next(), read.nextDouble());
                    System.out.println("Deposit Complete");
                }

                if (stuff.equals("transfer")) {
                    System.out.println("Enter accountID to transfer from, accountID To transfer to and the amount");
                    bankSoftware.transfer(read.next(), read.next(), read.nextDouble());
                    System.out.println("Transfer Complete");
                }


            }

        }
        }



    }


