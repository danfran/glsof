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
package net.sourceforge.glsof.queries.stats;

public class QueriesChartSortListener {//implements Listener {

    /*private static final Map<String, SortComparator> _comparators;

    static {
        _comparators = new HashMap<String, SortComparator>();
        _comparators.put("nup", new NumericSortUpComparator()); 
        _comparators.put("ndown", new NumericSortDownComparator()); 
        _comparators.put("tup", new TextSortUpComparator()); 
        _comparators.put("tdown", new TextSortDownComparator()); 
    }

    private Table _table;

    private String _sortName;

    private String[] _values;

    private int _columnIndex;

    public QueriesChartSortListener(Table table, String sortName, int columns, int columnIndex) {
        _table = table;
        _sortName = sortName;
        _columnIndex = columnIndex;
        _values = new String[columns-1];
    }

    public void handleEvent(Event e) {
        TableItem[] items = _table.getItems();
        final String direction = _table.getSortDirection() == SWT.UP ? "up" : "down";  
        final SortComparator comparator = _comparators.get(_sortName + direction);
        for (int i = 1; i < items.length; i++) {
            String value1 = items[i].getText(_columnIndex);
            for (int j = 0; j < i; j++) {
                String value2 = items[j].getText(_columnIndex);
                if (comparator.compare(value1, value2)) {
                    for (int k=0; k<_values.length; k++) _values[k] = items[i].getText(k);
                    items[i].dispose();
                    TableItem item = new TableItem(_table, SWT.NONE, j);
                    item.setText(_values);
                    items = _table.getItems();
                    break;
                }
            }
        }
        _table.setSortColumn((TableColumn) e.widget);
        _table.setTopIndex(0);
        if (direction.equals("up")) _table.setSortDirection(SWT.DOWN); 
        else _table.setSortDirection(SWT.UP);
    }
      */
}
