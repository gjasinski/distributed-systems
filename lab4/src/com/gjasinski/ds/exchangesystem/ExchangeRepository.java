package com.gjasinski.ds.exchangesystem;

import com.gjasinski.ds.proto.Currency;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ExchangeRepository implements Runnable {
    private final HashMap<Currency, Double> buy = new HashMap<>();
    private final HashMap<Currency, Double> sell = new HashMap<>();
    private ExchangeImpl observer;

    public ExchangeRepository(){
        initializeExchange();
    }

    private void initializeExchange() {
        Random random = new Random();
        System.out.println("CHANGE EXCHANGES DATA");
        Arrays.stream(Currency.values()).forEach(currency -> {
            double randomValue = random.nextDouble();
            buy.put(currency, randomValue);
            sell.put(currency, 1.1 * randomValue);
            System.out.println(currency + " buy: " + randomValue + " sell: " + randomValue * 1.1);
        });
        buy.put(Currency.PLN, 1.0);
        sell.put(Currency.PLN, 1.0);
    }

    public void setObserver(ExchangeImpl observer) {
        this.observer = observer;
    }

    public double getBuyForCurrency(Currency c){
        return buy.get(c);
    }

    public double getSellForCurrency(Currency c){
        return sell.get(c);
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(10000);
                initializeExchange();
                observer.notifyObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
