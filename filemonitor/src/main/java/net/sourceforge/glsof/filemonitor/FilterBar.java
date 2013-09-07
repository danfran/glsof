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

import net.sourceforge.glsof.common.main.AbstractFilterBar;
import net.sourceforge.glsof.common.main.Observer;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FilterBar extends AbstractFilterBar implements Observer {

    private TableRowSorter<TableModel> _sorter;

    public FilterBar(MonitorTable table) {
        super();
        _sorter = (TableRowSorter<TableModel>) table.getRowSorter();
    }

    @Override
    protected void createColumnsList() {
        final String[] columnsNames = new String[MonitorTable.COLUMN_NAMES.length + 1];
        columnsNames[0] = "";
        System.arraycopy(MonitorTable.COLUMN_NAMES, 0, columnsNames, 1, MonitorTable.COLUMN_NAMES.length);
        _comboColumnsList = new JComboBox(columnsNames);
        _comboColumnsList.setSelectedIndex(0);
    }

    protected void filter() {
        _sorter.setRowFilter(getRowFilter());
    }

    private RowFilter getRowFilter() {
        String text = _comboFilter.getText();

        if (text.length() == 0)
            return null;

        if (!_buttonCase.isSelected())
            text = "(?i)" + text;

        final int selectedColumn = _comboColumnsList.getSelectedIndex();

        return (selectedColumn == 0)
                ? RowFilter.regexFilter(text)
                : RowFilter.regexFilter(text, selectedColumn - 1);
    }

    @Override
    public void update(NOTIFY notify, Object... params) {
        if (notify == NOTIFY.CLEAR) {
            _sorter.setRowFilter(null);
            _comboColumnsList.setSelectedIndex(0);
            _comboFilter.setText("");
        }
    }
}
