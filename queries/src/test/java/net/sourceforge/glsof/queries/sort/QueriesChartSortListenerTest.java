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
package net.sourceforge.glsof.queries.sort;

public class QueriesChartSortListenerTest {

    private final static String[][] _stats = {
            {"A proc", "3000"},
            {"a proc", "1200"},
            {"foo-proc", "1021"},
            {"proc-bar", "2002"},
            {"123 Proc", "8009"}
    };

/*    private Table _table;

    private TableColumn _column1;

    private TableColumn _column2;

    @Before
    public void setUp() {
        _table = new Table(new Shell(), SWT.NONE);
        _column1 = new TableColumn(_table, SWT.NONE);
        _column1.setText("processes");
        _column2 = new TableColumn(_table, SWT.NONE);
        _column2.setText("rows");

        for (String[] row : _stats) {
            TableItem item = new TableItem(_table, SWT.NONE);
            item.setText(0, row[0]);
            item.setText(1, row[1]);
        }

    }

    @Test
    public void sortProcessesColumn() {
        QueriesChartSortListener sortTextListener = new QueriesChartSortListener(_table, "t", 3, 0);
        _column1.addListener(SWT.Selection, sortTextListener);
        _table.setSortColumn(_column1);

        final String[][] sortUp = {
                {"123 Proc", "8009"},
                {"a proc", "1200"},
                {"A proc", "3000"},
                {"foo-proc", "1021"},
                {"proc-bar", "2002"}
        };

        Event event = new Event();
        event.widget = _column1;
        sortTextListener.handleEvent(event);
        checkOrder(sortUp);

        final String[][] sortDown = {
                {"proc-bar", "2002"},
                {"foo-proc", "1021"},
                {"A proc", "3000"},
                {"a proc", "1200"},
                {"123 Proc", "8009"}
        };

        sortTextListener.handleEvent(event);
        checkOrder(sortDown);
    }

    @Test
    public void sortRowsColumn() {
        QueriesChartSortListener sortNumericListener = new QueriesChartSortListener(_table, "n", 3, 1);
        _column2.addListener(SWT.Selection, sortNumericListener);
        _table.setSortColumn(_column2);

        final String[][] sortUp = {
                {"foo-proc", "1021"},
                {"a proc", "1200"},
                {"proc-bar", "2002"},
                {"A proc", "3000"},
                {"123 Proc", "8009"}
        };

        Event event = new Event();
        event.widget = _column2;
        sortNumericListener.handleEvent(event);
        checkOrder(sortUp);

        final String[][] sortDown = {
                {"123 Proc", "8009"},
                {"A proc", "3000"},
                {"proc-bar", "2002"},
                {"a proc", "1200"},
                {"foo-proc", "1021"}
        };

        sortNumericListener.handleEvent(event);
        checkOrder(sortDown);
    }

    private void checkOrder(String[][] sort) {
        TableItem[] items = _table.getItems();
        for (int i = 0; i < items.length; i++) {
            assertEquals(sort[i][0], items[i].getText(0));
            assertEquals(sort[i][1], items[i].getText(1));
        }
    }*/
}
