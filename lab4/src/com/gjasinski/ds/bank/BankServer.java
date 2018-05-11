package com.gjasinski.ds.bank;

import com.gjasinski.ds.proto.Currency;
import com.gjasinski.ds.proto.CurrencyCollection;

public class BankServer {
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("usage: port currency1 [currency2]");
        }
        else {
            CurrencyCollection collection = CurrencyCollection.newBuilder().addCurrency(Currency.PLN)
                    .addCurrency(Currency.GBP)
                    .addCurrency(Currency.EUR)
                    .build();
            new ExchangeSystem(collection);
            new BankServer(args[0], collection);
        }
    }

    private CurrencyCollection collection;
    private ExchangeSystem exchangeSystem;

    public BankServer(String port, CurrencyCollection collection) {
        this.collection = collection;
        exchangeSystem = new ExchangeSystem(collection);
        new Thread(exchangeSystem).start();
        new BankImpl(Integer.valueOf(port), exchangeSystem);
    }
}
