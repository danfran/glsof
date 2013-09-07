package net.sourceforge.glsof.filemonitor.thread;

import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;
import net.sourceforge.glsof.common.lsof.LsofParametersBuilder;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.rmi.RmiService;
import net.sourceforge.glsof.filemonitor.MonitorTable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static net.sourceforge.glsof.common.rmi.RmiUtils.initRmiService;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository.getCurrentPreferences;

public class RemoteMonitorTableUpdater extends AbstractMonitorTableUpdater {

    private static final long serialVersionUID = 1L;

    private RmiService _remoteService;

    public RemoteMonitorTableUpdater(MonitorTable table) {
        super(table);
    }

    @Override
    protected Void doInBackground() throws Exception {
        final Preferences preferences = getCurrentPreferences();
        _remoteService = initRmiService(new RmiClientObserver(), preferences.getLocation());
        final String lsofParameters = new LsofParametersBuilder().build(preferences);

        while (!isCancelled()) {
            _remoteService.fetch(lsofParameters);
        }

        return null;
    }

    @Override
    protected void stopLsofExecutor() {
        try {
            _remoteService.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class RmiClientObserver extends UnicastRemoteObject implements LsofExecutorObserver {

        protected RmiClientObserver() throws RemoteException {
            super();
        }

        @Override
        public void flush(String[] row) {
            RemoteMonitorTableUpdater.this.flush(row);
        }

        @Override
        public void finished() {
            RemoteMonitorTableUpdater.this.finished();
        }

        @Override
        public void stop() {
            RemoteMonitorTableUpdater.this.stop();
        }

        @Override
        public void displayErrorMessage(String text) {
            RemoteMonitorTableUpdater.this.displayErrorMessage(text);
        }
    }
}
