package net.sourceforge.glsof.common.rmi;

import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiService extends Remote {

    void addObserver(LsofExecutorObserver observer) throws RemoteException;

    void fetch(String parameters) throws RemoteException;

    void stop() throws RemoteException;

}
