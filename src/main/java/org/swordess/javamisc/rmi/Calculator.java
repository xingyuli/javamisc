package org.swordess.javamisc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

	public String calculate(String expr) throws RemoteException;
	
}
