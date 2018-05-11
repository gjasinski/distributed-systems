package com.gjasinski.ds.bank;

import com.gjasinski.ds.slice.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryImpl implements Factory {
    private final ConcurrentHashMap<String, UserImpl> map = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private ObjectAdapter adapter;
    private ExchangeSystem exchangeSystem;

    public FactoryImpl(ObjectAdapter adapter, ExchangeSystem exchangeSystem) {
        this.adapter = adapter;
        this.exchangeSystem = exchangeSystem;
    }

    @Override
    public String createAccount(UserInfo userInfo, Current current) {
        String guid = generateGUID();
        UserImpl userImpl = new UserImpl(userInfo, exchangeSystem);
        map.put(guid, userImpl);
        adapter.add(userImpl, new Identity(userImpl.hashCode() + "", "userImpl"));
        return guid;
    }

    private String generateGUID(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
//
//    @Override
//    public String createAccount(UserInfo userInfo, Current current) {
//        return null;
//    }

    @Override
    public UserPrx login(String id, Current current) throws AccessDenied {
        return null;
    }
}
