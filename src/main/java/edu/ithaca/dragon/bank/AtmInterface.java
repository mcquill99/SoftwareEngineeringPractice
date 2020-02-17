package edu.ithaca.dragon.bank;


import java.util.Scanner;

public class AtmInterface {

    public static void main(String[] args){

        Scanner read = new Scanner(System.in);

        CentralBank bank = new CentralBank();
        BankSoftware bankSoftware = new BankSoftware(bank);
        AdvancedSoftware teller = new AdvancedSoftware(bank);
        AdminSoftware admin = new AdminSoftware(bank);

        teller.createAccount("11212", "a@b.com", "testpassword", 500, false);
        teller.createAccount("1234", "a215@gmail.com", "tester", 1000, false);
        teller.createAccount("5678", "a@b.com", "pass", 1000, false);
        admin.freezeAccount("5678");



        System.out.println("Hello, Please log in with your ID and password");
        String id = read.next();
        String pass = read.next();

        while (!bankSoftware.confirmCredentials(id, pass)) {
            System.out.println("Invalid login, try again");
            id = read.next();
            pass = read.next();
        }

        read.useDelimiter("\\n");
        String action;
        double amount;
        System.out.println("Your current balance is: " + bankSoftware.checkBalance(id));


        do {
            System.out.println("Please enter an action or type 'help' for a list of commands");
            action = read.next();
            action = action.toLowerCase();
            try{
                switch (action) {
                    case "help":
                        System.out.println("Your options are: " +
                                "\n withdraw " +
                                "\n deposit " +
                                "\n transfer " +
                                "\n logout " +
                                "\n check balance " +
                                "\n history \n");
                        break;
                    case "withdraw":
                        System.out.println("Enter amount");
                        amount = read.nextDouble();
                        bankSoftware.withdraw(id, amount);
                        System.out.println("Withdraw Complete amount in account now is " + bankSoftware.checkBalance(id));
                        break;


                    case "deposit":
                        System.out.println("Enter amount");
                        amount = read.nextDouble();
                        bankSoftware.deposit(id, amount);
                        System.out.println("Deposit Complete, amount in account now is " + bankSoftware.checkBalance(id));
                        break;

                    case "transfer":
                        System.out.println("Enter accountID To transfer to");
                        String id2 = read.next();
                        System.out.println("Enter the amount");
                        amount = read.nextDouble();
                        bankSoftware.transfer(id, id2, amount);
                        System.out.println("Transfer Complete, amount in account is now" + bankSoftware.checkBalance(id));

                    case "check balance":
                        System.out.println("Your current balance is " + bankSoftware.checkBalance(id));
                        break;


                    case "logout":
                        System.out.println("Have a nice day!");
                        break;

                    case "history":
                        System.out.println("Here's a list of your transaction history " + bankSoftware.transactionHistory(id));
                        break;

                    default:
                        System.out.println("Please enter a valid input");


                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please contact customer service at 1-888-555-1212 for further help");
            }


        } while (!action.equals("logout"));

    }
}






