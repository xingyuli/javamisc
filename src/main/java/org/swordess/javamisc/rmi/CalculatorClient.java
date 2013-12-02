package org.swordess.javamisc.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {

	public void calculate(String expr) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			Calculator calculator = (Calculator) registry.lookup("Calculator");
			String result = calculator.calculate(expr);
			System.out.println(result);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CalculatorClient client = new CalculatorClient();
		client.calculate("hello world");
	}
	
}
