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
package net.sourceforge.glsof.queries.thread;

import net.sourceforge.glsof.common.main.BasicTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class ProcessTable extends BasicTable {
	
	public final static String[] COLUMN_NAMES = {
        "PID", "PGID", "PPID", NLS("USER"), "FD", NLS("TYPE"), NLS("DEVICE"), NLS("SIZE"), "NLINK", NLS("NODE"), NLS("NAME")};

    public ProcessTable(DefaultTableModel model, final List<Boolean> views) {
        super(model, COLUMN_NAMES);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFillsViewportHeight(true);
        setShowHorizontalLines(true);
        for (int i = 0; i < COLUMN_NAMES.length-1; i++) {
            if (!views.get(i)) showColumn(false, i);
        }
    }
}
