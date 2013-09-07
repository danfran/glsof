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
package net.sourceforge.glsof.common.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicTable extends JTable {
	
	private TableColumnModel _tcm;
	
	private String[] _columnNames;
	
	private Map<String, Integer> _positions = new HashMap<String, Integer>();
	
	private Map<String, ColumnData> _hiddens = new HashMap<String, ColumnData>();

    public BasicTable(DefaultTableModel tableModel, String[] columnNames) {
    	super(tableModel);
        _columnNames = columnNames;
//        setAutoResizeMode(AUTO_RESIZE_OFF);
        for (int i = 0; i < columnNames.length; i++) {
        	tableModel.addColumn(columnNames[i]);
        	_positions.put(columnNames[i], i);
        }
        _tcm = getColumnModel();
    }

    public void showColumns(final List<Boolean> views) {
		for (int i = 0; i < _columnNames.length-1; i++) {
			showColumn(views.get(i), i);
		}
	}
    
    public void showColumn(boolean show, int index) {
    	final String name = _columnNames[index];
        if (show) show(name);
        else hide(name);
    }
    
    private void hide(String name) {
        try {
            int index = _tcm.getColumnIndex(name);
            TableColumn column = _tcm.getColumn(index);
            ColumnData cd = new ColumnData(column, index);
            if (_hiddens.put(name, cd) != null) {
                throw new IllegalArgumentException("Duplicate column name");
            }
            _tcm.removeColumn(column);
        } catch (IllegalArgumentException iae) {
        }
    }

    private void show(String name) {
    	ColumnData cd = _hiddens.remove(name);
        if (cd != null) {
        	_tcm.addColumn(cd.column);
            int lastColumn = _tcm.getColumnCount() - 1;
            if (cd.index < lastColumn) {
            	_tcm.moveColumn(lastColumn, getSortedPosition(name));
            }
        }
    }

	private int getSortedPosition(String name) {
		final int pos = _positions.get(name);
		for (int i=0; i<getColumnCount(); i++) {
			String colName = _tcm.getColumn(i).getHeaderValue().toString();
			int absPosition = _positions.get(colName);
			if (pos < absPosition) {
				return _tcm.getColumnIndex(colName);
			}
		}
		return 0;
	}

    public void clear() {
        ((DefaultTableModel)getModel()).setNumRows(0);
    }
    
    private class ColumnData {
    	
    	private int index;
    	
    	private TableColumn column;
    	
    	public ColumnData (TableColumn column, int index) {
    		this.column = column;
    		this.index = index;
    	}
    }

}
