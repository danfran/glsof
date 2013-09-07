package net.sourceforge.glsof.common.lsof;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LsofExecutorObserver extends Remote {

    public abstract void flush(String[] row) throws RemoteException;

    public abstract void finished() throws RemoteException;

    public abstract void stop() throws RemoteException;

    public abstract void displayErrorMessage(final String text) throws RemoteException;

}
