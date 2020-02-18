package edu.ithaca.dragon.bank;


import java.util.Scanner;

public class AtmUI {
    private BasicAPI atmAPI;
    private Scanner read = new Scanner(System.in);
    private String id, pass;

    public AtmUI(BasicAPI atmAPI){
        this.atmAPI = atmAPI;
    }

    public void loginPage(){
        read.reset();
        System.out.println("Hello, Please log in with your ID and password");
        id = read.next();
        pass = read.next();

        while (!atmAPI.confirmCredentials(id, pass)) {
            System.out.println("Invalid login, try again");
            id = read.next();
            pass = read.next();
        }

        loggedIn();
    }

    public void loggedIn(){
        read.useDelimiter("\\n");
        String action;
        double amount;
        System.out.println("Your current balance is: " + atmAPI.checkBalance(id));


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
                                "\n check balance" +
                                "\n logout \n");
                        break;
                    case "withdraw":
                        System.out.println("Enter amount");
                        amount = read.nextDouble();
                        atmAPI.withdraw(id, amount);
                        System.out.println("Withdraw Complete amount in account now is " + atmAPI.checkBalance(id));
                        break;


                    case "deposit":
                        System.out.println("Enter amount");
                        amount = read.nextDouble();
                        atmAPI.deposit(id, amount);
                        System.out.println("Deposit Complete, amount in account now is " + atmAPI.checkBalance(id));
                        break;

                    case "transfer":
                        System.out.println("Enter accountID To transfer to");
                        String id2 = read.next();
                        System.out.println("Enter the amount");
                        amount = read.nextDouble();
                        atmAPI.transfer(id, id2, amount);
                        System.out.println("Transfer Complete, amount in account is now" + atmAPI.checkBalance(id));

                    case "check balance":
                        System.out.println("Your current balance is " + atmAPI.checkBalance(id));
                        break;


                    case "logout":
                        System.out.println("Have a nice day!");
                        break;

                    default:
                        System.out.println("Please enter a valid input");


                }
            }
            catch(AccountFrozenException e){
                System.out.println(e.getMessage());
                System.out.println("Please contact customer service at 1-888-555-1212 for further help");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }


        } while (!action.equals("logout"));
        loginPage();

    }
}






