package com.gjasinski.ds.bank;

import com.gjasinski.ds.proto.Currency;
import com.gjasinski.ds.proto.CurrencyCollection;
import com.gjasinski.ds.proto.ExchangeServiceGrpc;
import com.gjasinski.ds.proto.ExchangeStatus;
import com.gjasinski.ds.slice.CurrencyType;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Iterator;

public class ExchangeSystem implements Runnable{
    private final ManagedChannel channel;
    private ExchangeServiceGrpc.ExchangeServiceBlockingStub exchangeServiceStub;
    private final CurrencyCollection currencyCollection;
    private final HashMap<Currency, Double> buy = new HashMap<>();
    private final HashMap<Currency, Double> sell = new HashMap<>();

    public ExchangeSystem(CurrencyCollection currencyCollection) {
        channel  = ManagedChannelBuilder.forAddress("localhost", 50051)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
                .usePlaintext(true)
                .build();
        this.currencyCollection = currencyCollection;
    }

    @Override
    public void run() {
        try {
            exchangeServiceStub = ExchangeServiceGrpc.newBlockingStub(channel);
            while (true) {
                Iterator<ExchangeStatus> exchangeStatusStream = exchangeServiceStub.getExchangeStatusStream(currencyCollection);

                exchangeStatusStream.forEachRemaining(exchangeStatus -> {
                    buy.put(exchangeStatus.getCurrency(), exchangeStatus.getBuy());
                    sell.put(exchangeStatus.getCurrency(), exchangeStatus.getSell());
                    System.out.println(exchangeStatus);
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    double getSell(CurrencyType currencyType){
        Currency currency = Currency.valueOf(currencyType.toString());
        return sell.get(currency);
    }

    double getBuy(CurrencyType currencyType){
        Currency currency = Currency.valueOf(currencyType.toString());
        return buy.get(currency);
    }
}
