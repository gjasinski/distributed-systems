package com.gjasinski.ds.bank;

import com.gjasinski.ds.slice.*;
import com.zeroc.Ice.*;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryImpl implements Factory {
    private final ConcurrentHashMap<String, UserImpl> map = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private ObjectAdapter adapter;
    private ExchangeSystem exchangeSystem;
    private Communicator communicator;
    private String port;

    public FactoryImpl(ObjectAdapter adapter, ExchangeSystem exchangeSystem, Communicator communicator, String port) {
        this.adapter = adapter;
        this.exchangeSystem = exchangeSystem;
        this.communicator = communicator;
        this.port = port;
    }

    @Override
    public String createAccount(UserInfo userInfo, Current current) {
        String guid = generateGUID();
        UserImpl userImpl = new UserImpl(userInfo, exchangeSystem);
        map.put(guid, userImpl);
        // 3. Stworzenie serwanta/serwant�w

        adapter.add(userImpl, new Identity(guid, "userImpl"));
        System.out.println(guid);
        return guid;
    }

    private String generateGUID(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++){
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
        if (map.containsKey(id)){
            UserImpl user = map.get(id);
            String s1 = user.getPesel() + "/userImpl:tcp -h localhost -p " + port + ":udp -h localhost -p " + port;
            ObjectPrx base = communicator.stringToProxy(s1);
            // System.out.println(base.toString());
            return UserPrx.uncheckedCast(base);
        }
        throw new AccessDenied();


    }
}
