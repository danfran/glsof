package net.sourceforge.glsof.filemonitor.rmi;

import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;
import net.sourceforge.glsof.common.lsof.LsofParametersBuilder;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.rmi.RmiService;
import org.junit.Test;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiServerIntegrationTest {

    @Test
    public void testRmiClient() throws RemoteException {
        final Preferences preferences = Preferences.from("test");
        final String params = new LsofParametersBuilder().build(preferences);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        RmiService remoteService = null;
        try {
            remoteService = (RmiService) Naming.lookup("//localhost:3000/lsofExecutor");
            remoteService.addObserver(new MockRmiClientObserver());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final String lsofParameters = new LsofParametersBuilder().build(preferences);

        remoteService.fetch(lsofParameters);
    }

    private class MockRmiClientObserver extends UnicastRemoteObject implements LsofExecutorObserver {

        protected MockRmiClientObserver() throws RemoteException {
            super();
        }

        @Override
        public void flush(String[] row) {
            for (String value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        @Override
        public void finished() {
            System.out.println("finished");
        }

        @Override
        public void stop() {
            System.out.println("stop");
        }

        @Override
        public void displayErrorMessage(String text) {
            System.out.println(text);
        }
    }

}
