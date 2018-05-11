package com.gjasinski.ds.bank;

import com.gjasinski.ds.slice.*;
import com.zeroc.Ice.Current;

import java.util.Random;

public class UserImpl implements User {
    private static final int PREMIUM_TRESHOLD = 10000;
    private final String firstname;
    private final String lastname;
    private final String pesel;
    private final long income;
    private final long accountStatus = new Random().nextLong();
    ExchangeSystem exchangeSystem;

    public UserImpl(UserInfo userInfo, ExchangeSystem exchangeSystem) {
        this.firstname = userInfo.firstname;
        this.lastname = userInfo.lastname;
        this.pesel = userInfo.pesel;
        this.income = userInfo.income;
        this.exchangeSystem = exchangeSystem;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPesel() {
        return pesel;
    }

    public boolean isPremium(){
        return income > PREMIUM_TRESHOLD;
    }

    @Override
    public double checkAccountStatus(String id, Current current) throws AccessDenied {
        return accountStatus;
    }

    @Override
    public LoanInfo getLoanInfo(CurrencyType currencyType, double loanValue, Current current) throws OperationNotPermitted {
        if(!isPremium()){
            throw new OperationNotPermitted();
        }
        else {
            double buyRate = exchangeSystem.getBuy(currencyType);
            return new LoanInfo(1.1 * loanValue, 1.1 * loanValue / buyRate, currencyType, buyRate, loanValue);
        }
    }
}
