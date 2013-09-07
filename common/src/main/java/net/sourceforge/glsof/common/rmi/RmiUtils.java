package net.sourceforge.glsof.common.rmi;

import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;
import net.sourceforge.glsof.common.model.Location;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiUtils {

    public static RmiService initRmiService(Location location) throws RemoteException, NotBoundException, MalformedURLException {
        return initRmiService(new SimpleRmiClientObserver(), location);
    }

    public static RmiService initRmiService(LsofExecutorObserver observer, Location location) throws RemoteException, NotBoundException, MalformedURLException {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        RmiService remoteService = (RmiService) Naming.lookup(location.getRmiAddress());
        remoteService.addObserver(observer);
        return remoteService;
    }

    private static class SimpleRmiClientObserver extends UnicastRemoteObject implements LsofExecutorObserver {

        protected SimpleRmiClientObserver() throws RemoteException {
            super();
        }

        @Override
        public void flush(String[] row) {}

        @Override
        public void finished() {}

        @Override
        public void stop() {}

        @Override
        public void displayErrorMessage(String text) {}
    }

}
