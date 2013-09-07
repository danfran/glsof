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
package net.sourceforge.glsof.filemonitor;

import net.sourceforge.glsof.common.main.BasicTable;
import net.sourceforge.glsof.common.main.Observer;
import net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository;
import net.sourceforge.glsof.filemonitor.thread.AbstractMonitorTableUpdater;
import net.sourceforge.glsof.filemonitor.thread.LocalMonitorTableUpdater;
import net.sourceforge.glsof.filemonitor.thread.RemoteMonitorTableUpdater;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository.getCurrentPreferences;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConfRepository.getUiConf;

public class MonitorTable extends BasicTable implements Observer {

    public final static String[] COLUMN_NAMES = {
            NLS("PROCESS"), "PID", "TID", "PGID", "PPID", NLS("USER"), "FD", NLS("TYPE"), NLS("DEVICE"), NLS("SIZE"), "NLINK", NLS("NODE"), NLS("NAME"), NLS("STATUS")
    };

    private boolean _autoscroll;

    private AbstractMonitorTableUpdater _threadUpdater;

    private FileMonitorPreferencesRepository _preferencesRepository;

    public MonitorTable(FileMonitorPreferencesRepository preferencesRepository) {
        super(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }, COLUMN_NAMES);

        _preferencesRepository = preferencesRepository;

        getColumn(COLUMN_NAMES[COLUMN_NAMES.length - 1]).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        return (JComponent) value;
                    }
                });
        setRowSorter(new TableRowSorter<TableModel>(getModel()));

//      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFillsViewportHeight(true);
        getTableHeader().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JPopupMenu menu = new JPopupMenu();
                    columns(menu);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        if (getCurrentPreferences() != null)
            showColumns(getColumns());
        setAutoscroll(getUiConf().isAutoscroll());
    }

    JPanel pack() {
        JPanel tablePanel = new JPanel(new GridLayout(0, 1));
        tablePanel.add(new JScrollPane(this));
        return tablePanel;
    }

    private void columns(JPopupMenu columns) {
        final List<Boolean> views = getColumns();
        for (int i = 0; i < COLUMN_NAMES.length - 1; i++) {
            final JCheckBoxMenuItem item = new JCheckBoxMenuItem(COLUMN_NAMES[i]);
            columns.add(item);
            item.setSelected(views.get(i));
            final int index = i;
            item.addActionListener(new ActionListener() {
                final int _index = index;

                public void actionPerformed(ActionEvent e) {
                    views.set(_index, item.isSelected());
                    showColumn(views.get(index), index);
                    _preferencesRepository.updateColumns(views);
                }
            });
        }
    }

    private List<Boolean> getColumns() {
        List<Boolean> columns = getCurrentPreferences().getColumns();

        int diff = COLUMN_NAMES.length - columns.size() - 1;
        while (diff > 0) {
            columns.add(true);
            diff--;
        }

        return columns;
    }

    public void addRows(List<Object[]> rows) {
        final DefaultTableModel model = (DefaultTableModel) getModel();
        for (Object[] row : rows)
            model.addRow(row);
        if (_autoscroll)
            scrollRectToVisible(getCellRect(getRowCount() - 1, 0, true));
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        final int hitRowIndex = rowAtPoint(e.getPoint());
        return (hitRowIndex == -1) ? "" : getRowToolTipText(hitRowIndex).toString();
    }

    private StringBuilder getRowToolTipText(int hitRowIndex) {

        List<Boolean> views = getCurrentPreferences().getColumns();

        StringBuilder tip = new StringBuilder("<html>");

        int visibleIndex = 0;

        for (int i = 0; i < views.size(); i++) {
            if (!views.get(i)) continue;
            TableCellRenderer renderer = getCellRenderer(hitRowIndex, visibleIndex);
            String cellText = ((DefaultTableCellRenderer.UIResource) prepareRenderer(renderer, hitRowIndex, visibleIndex)).getText();
            tip.append(COLUMN_NAMES[i]).append(": ").append(cellText).append("<br>");
            visibleIndex++;
        }

        TableCellRenderer renderer = getCellRenderer(hitRowIndex, getColumnCount() - 1);
        String cellText = ((JLabel) prepareRenderer(renderer, hitRowIndex, getColumnCount() - 1)).getText();
        tip.append(COLUMN_NAMES[COLUMN_NAMES.length - 1]).append(": ").append(cellText);
        return tip;
    }

    private void setAutoscroll(boolean autoscroll) {
        _autoscroll = autoscroll;
    }

    @Override
    public void update(NOTIFY notify, Object... params) {
        if (notify == NOTIFY.CLEAR) {
            clear();
        } else if (notify == NOTIFY.AUTOSCROLL) {
            setAutoscroll((Boolean) params[0]);
        } else if (notify == NOTIFY.START) {
            _threadUpdater = getCurrentPreferences().getLocation().isRemote()
                ? new RemoteMonitorTableUpdater(this)
                : new LocalMonitorTableUpdater(this);
            _threadUpdater.execute();
        } else if (notify == NOTIFY.STOP || notify == NOTIFY.CLOSE) {
            if (_threadUpdater != null) _threadUpdater.stop();
        }
    }
}
