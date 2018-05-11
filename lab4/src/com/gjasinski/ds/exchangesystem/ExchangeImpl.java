package com.gjasinski.ds.exchangesystem;

import com.gjasinski.ds.proto.CurrencyCollection;
import com.gjasinski.ds.proto.ExchangeServiceGrpc;
import com.gjasinski.ds.proto.ExchangeStatus;
import io.grpc.stub.StreamObserver;


import java.util.HashMap;

public class ExchangeImpl extends ExchangeServiceGrpc.ExchangeServiceImplBase
{
	private final HashMap<StreamObserver<ExchangeStatus>, CurrencyCollection> map = new HashMap<>();
	private final ExchangeRepository exchangeRepository = new ExchangeRepository();

	public ExchangeImpl() {
		exchangeRepository.setObserver(this);
		new Thread(exchangeRepository).start();
	}

	@Override
	public void getExchangeStatusStream(CurrencyCollection request, StreamObserver<ExchangeStatus> responseObserver) {
		map.put(responseObserver, request);
		notifyObserver(responseObserver, request);
	}

	public void notifyObservers(){
		map.entrySet().forEach(entry -> notifyObserver(entry.getKey(), entry.getValue()));
	}

	private void notifyObserver(StreamObserver<ExchangeStatus> observer, CurrencyCollection collection){
		try {
			collection.getCurrencyList().forEach(currency -> {
				ExchangeStatus build = ExchangeStatus.newBuilder().setCurrency(currency).setBuy(exchangeRepository.getBuyForCurrency(currency)).setSell(exchangeRepository.getSellForCurrency(currency)).build();
				observer.onNext(build);
			});
		}catch (Exception e){
			System.out.println(e.getCause() + observer.toString());
			map.remove(observer);
		}
	}
}
