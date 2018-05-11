package com.gjasinski.ds.bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class BankImpl {
    public BankImpl(int port, ExchangeSystem exchangeSysteme) {
        int status = 0;
        Communicator communicator = null;
        try
        {
            // 1. Inicjalizacja ICE - utw poorzenie communicatora
            communicator = Util.initialize();

            // 2. Konfiguracja adaptera
            // METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
            //Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

            // METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie �r�d�owym
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter1", "tcp -h localhost -p " + port +":udp -h localhost -p " + port);

            // 3. Stworzenie serwanta/serwant�w
            FactoryImpl factory = new FactoryImpl(adapter, exchangeSysteme);

            // 4. Dodanie wpis�w do tablicy ASM
            adapter.add(factory, new Identity("factory", "factory"));

            // 5. Aktywacja adaptera i przej�cie w p�tl� przetwarzania ��da�
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        }
        catch (Exception e)
        {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null)
        {
            // Clean up
            //
            try
            {
                communicator.destroy();
            }
            catch (Exception e)
            {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }
}
