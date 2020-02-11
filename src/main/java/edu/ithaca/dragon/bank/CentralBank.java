package edu.ithaca.dragon.bank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CentralBank  {


    public Map<String, BankAccount>accountMap = new HashMap<>();
    public Collection<BankAccount>susAccounts = new ArrayList<>();

    public ATM atm = new ATM(this);
    public BankTeller teller = new BankTeller(this);
    public AdminSoftware admin = new AdminSoftware(this);





}
