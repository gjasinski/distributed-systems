package com.gjasinski.ds.client;
// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.gjasinski.ds.slice.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client 
{
	private static FactoryPrx factory;
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Communicator communicator = null;
	private static String port;
	public static void main(String[] args) 
	{
		if(args.length < 1){
			System.out.println("Podaj port");
			return;
		}
		int status = 0;

		try {
			port = args[0];
			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			// 2. Uzyskanie referencji obiektu na podstawie linii w pliku konfiguracyjnym
			//Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
			// 2. To samo co powy�ej, ale mniej �adnie
			ObjectPrx base = communicator.stringToProxy("factory/factory:tcp -h localhost -p " + args[0] + ":udp -h localhost -p " + args[0]);

			// 3. Rzutowanie, zaw�anie
			factory = FactoryPrx.checkedCast(base);
			if (factory == null) throw new Error("Invalid proxy");

			char input = ' ';
			while(input != 'q'){
				switch (input){
					case '1': register();
						break;
					case '2': login();
						break;
					default:
						System.out.println(" 1 - register, 2 login");
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String s = br.readLine();
				if(s.length() == 0){
					input = 'd';
				}
				else {
					input= s.charAt(0);
				}
			}


		}
		catch (Exception e){e.printStackTrace();}
		System.exit(status);
	}

	private static void register() throws IOException {
		try {
			System.out.println("imie nazwisko pesel income");
			String name = br.readLine();
			String surname = br.readLine();
			String pesel = br.readLine();
			int income = Integer.valueOf(br.readLine());
			String account = factory.ice_twoway().createAccount(new UserInfo(name, surname, pesel, income));
			System.out.println("Your account ID: " + account);
		}catch (NumberFormatException format){
			System.out.println("not valid input format1");
		}
	}

	private static void login() throws IOException {
		try {
			System.out.println("id:");
			String id = br.readLine();
			UserPrx account = UserPrx.checkedCast(factory.ice_twoway().login(id));
			System.out.println(account);
			System.out.println("logged in");
			UserPrx base = UserPrx.checkedCast(communicator.stringToProxy("userImpl/" + id + ":tcp -h localhost -p " + port + ":udp -h localhost -p " + port));
			char input = ' ';
			while(input != 'q'){
				switch (input){
					case '1': account_status(base, id);
						break;
					case '2': loan_info(base);
						break;
					default:
						System.out.println(" 1 - account status, 2 loan info");
				}
				String s = br.readLine();
				if(s.length() == 0){
					input = 'd';
				}
				else {
					input= s.charAt(0);
				}
			}
			System.out.println("logged out");
		} catch (AccessDenied accessDenied) {
			System.out.println("Acces denied");
		}
	}

	private static void account_status(UserPrx user, String id) throws AccessDenied {
		System.out.println("account status: " + user.ice_twoway().checkAccountStatus(id));
	}

	private static void loan_info(UserPrx user) throws IOException {
		System.out.println("currency and value");
		String currency = br.readLine();
		try {
			double value = Double.valueOf(br.readLine());
			LoanInfo loanInfo = user.ice_twoway().getLoanInfo(CurrencyType.valueOf(currency), value);
			System.out.println("exchange rate: " + loanInfo.exchangeRate);
			System.out.println("base curr: " + loanInfo.loanBaseCurrency);
			System.out.println("foreign curr" + loanInfo.loanForeignCurrency);
			System.out.println("loan value " + loanInfo.loanValue);
			System.out.println("curr type " + loanInfo.currencyType);
		} catch (OperationNotPermitted operationNotPermitted) {
			System.out.println("user is not premium user");

		}
		catch (IllegalArgumentException format){
			System.out.println("not vlaid input");
		}
	}
}