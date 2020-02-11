package edu.ithaca.dragon.bank;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentralBank  {


    public Map<String, BankAccount>accountMap = new HashMap<>();

    public ATM atm = new ATM(this);
    public BankTeller teller = new BankTeller(this);
    public AdminSoftware admin = new AdminSoftware(this);





}
