package org.swordess.javamisc.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer implements Calculator {

	@Override
	public String calculate(String expr) throws RemoteException {
		return expr;
	}
	
	public void start() throws RemoteException {
		Calculator stub = (Calculator) UnicastRemoteObject.exportObject(this, 3322);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("Calculator", stub);
	}
	
	public static void main(String[] args) throws RemoteException {
		CalculatorServer server = new CalculatorServer();
		server.start();
	}

}
