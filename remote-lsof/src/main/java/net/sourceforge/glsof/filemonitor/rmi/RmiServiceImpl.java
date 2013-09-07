package net.sourceforge.glsof.filemonitor.rmi;

import net.sourceforge.glsof.common.lsof.LsofExecutor;
import net.sourceforge.glsof.common.lsof.LsofExecutorImpl;
import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;
import net.sourceforge.glsof.common.rmi.RmiService;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServiceImpl implements RmiService {

    private LsofExecutorObserver _observer;

    private LsofExecutor _lsofExecutor;

    @Override
    public void addObserver(LsofExecutorObserver observer) throws RemoteException {
        _observer = observer;
        _lsofExecutor = new LsofExecutorImpl(observer);
    }

    @Override
    public void fetch(String lsofParameters)  throws RemoteException {
        try {
            Process process = Runtime.getRuntime().exec(lsofParameters);
            _lsofExecutor.fetch(process.getInputStream());
            checkErrors(_lsofExecutor.parseErrors(process.getErrorStream()));
            process.destroy();
        } catch (IOException e) {
//            checkErrors(NLS("Lsof_not_found"));
            checkErrors("Lsof not found");
        }
    }

    @Override
    public void stop() throws RemoteException {
        _lsofExecutor.stop();
    }

    private void checkErrors(String errors) {
        if (errors.isEmpty()) return;

        try {
            _observer.displayErrorMessage(errors);
            stop();
            _observer.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new RMISecurityManager());
        try {
            final int port = findPort(args);
            final Registry rmiRegistry = LocateRegistry.createRegistry(port);
            final RmiService rmiService = (RmiService) UnicastRemoteObject.exportObject(new RmiServiceImpl(), port);
            rmiRegistry.bind("lsofExecutor", rmiService);
        } catch (NumberFormatException e) {
            usage();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

		System.out.println("RMI Lsof Executor service started.");
	}

    private static int findPort(String[] args) throws NumberFormatException {
        int port = (args.length < 1) ? 1099 : Integer.parseInt(args[0]);
        if (port < 0 || port > 65535) throw new NumberFormatException();
        return port;
    }

    private static void usage() {
		System.out.println("Usage: java -Djava.security.policy=path/server-policy.txt -jar remote-lsof.jar <service-port>");
		System.out.println("<service-port> = 2000, etc. 1099 is default.");
		System.out.println("Example: java -Djava.rmi.server.hostname=192.168.1.20 -Djava.security.policy=path/server-policy.txt -jar remote-lsof.jar 2000");
		System.exit(1);
	}
}
