/* This file is part of Glsof.

   Glsof is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Glsof is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Glsof.  If not, see <http://www.gnu.org/licenses/>. */
package net.sourceforge.glsof.filemonitor.thread;

import com.google.common.base.Equivalence;
import net.sourceforge.glsof.common.lsof.LsofExecutorObserver;
import net.sourceforge.glsof.common.utils.MessageDialog;
import net.sourceforge.glsof.filemonitor.MonitorTable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;


public abstract class AbstractMonitorTableUpdater extends SwingWorker<Void, Object[]> implements LsofExecutorObserver {

    private static String OPEN = NLS("OPEN");
    private static String CLOSED = NLS("CLOSED");

    private MonitorTable _table;

    private final Map<String, StatusDetector> _detectors = new HashMap<String, StatusDetector>();

    protected abstract void stopLsofExecutor();

    public AbstractMonitorTableUpdater(MonitorTable table) {
        _table = table;
    }

    public void finished() {
        for (StatusDetector detector : _detectors.values()) {

            for (Equivalence.Wrapper<String[]> row : detector.getOldCache())
                publishClosedRow(row.get());

            detector.swapCaches();
        }
    }

    public void flush(String[] row) {
        StatusDetector detector = getStatusDetector(row[0]);
        detector.add(row);
        if (detector.isOpenRow(row)) {
            publishOpenRow(row);
        }
    }

    private StatusDetector getStatusDetector(String processName) {
        StatusDetector statusDetector = _detectors.get(processName);
        if (statusDetector == null) {
            statusDetector = new StatusDetector();
            _detectors.put(processName, statusDetector);
        }
        return statusDetector;
    }

    private void publishClosedRow(String[] row) {
        publish(appendStatusToRow(row, CLOSED, RED));
    }

    private void publishOpenRow(String[] row) {
        publish(appendStatusToRow(row, OPEN, BLUE));
    }

    private Object[] appendStatusToRow(String[] row, String status, Color color) {
        Object[] newRow = new Object[row.length + 1];
        System.arraycopy(row, 0, newRow, 0, row.length);
        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(color);
        newRow[row.length] = statusLabel;
        return newRow;
    }

    @Override
    protected void process(List<Object[]> rows) {
        _table.addRows(rows);
    }

    public void stop() {
        stopLsofExecutor();
        cancel(true);
    }

    public void displayErrorMessage(final String text) {
        MessageDialog.error(_table, text);
    }

}
