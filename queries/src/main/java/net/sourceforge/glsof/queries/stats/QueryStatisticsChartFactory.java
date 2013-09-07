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


class QueryStatisticsChartFactory {

    private final static int WIDTH_RATE_COLUMN_1 = 4;
    private final static int WIDTH_RATE_COLUMN_2 = 4;
    private final static int WIDTH_RATE_COLUMN_3 = 2;

    /*final private Shell _shell;

    public QueryStatisticsChartFactory(Shell shell) {
        _shell = shell;
    }

    private FillLayout createFillLayout() {
        FillLayout f = new FillLayout();
        f.marginHeight=0;
        f.marginWidth=0;
        f.spacing=0;
        return f;
    }

    public Composite create(final Composite chartsContainer, final String[] columnsNames, final Map<String, Integer> stats, final int totalRowsCount) {
        final Composite comp = new Composite(chartsContainer, SWT.BORDER);
        comp.setLayout(new FillLayout());

        final Table table = new Table(comp, SWT.BORDER);
        table.setLayout(new FillLayout());
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        final TableColumn column1 = new TableColumn(table, SWT.NONE);
        column1.setText(columnsNames[0]);
        final TableColumn column2 = new TableColumn(table, SWT.NONE);
        column2.setText(columnsNames[1]);
        final TableColumn column3 = new TableColumn(table, SWT.NONE);
        column3.setText(columnsNames[2]);

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            Integer rows = entry.getValue();
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(0, entry.getKey());
            item.setText(1, Integer.toString(rows));
        }

        column1.addListener(SWT.Selection, new QueriesChartSortListener(table, "t", 3, 0)); 
        column2.addListener(SWT.Selection, new QueriesChartSortListener(table, "n", 3, 1)); 
        table.setSortColumn(column1);
        table.setSortColumn(column2);
        table.setSortDirection(SWT.UP); // it should be SWT.DOWN... fix this

        Event e = new Event();
        e.widget = column2;
        column2.notifyListeners(SWT.Selection, e);

        table.addListener(SWT.PaintItem, new Listener() {
            final Display display = _shell.getDisplay();
            public void handleEvent(Event event) {
                if (event.index == 2) {
                    GC gc = event.gc;
                    TableItem item = (TableItem) event.item;
                    double percent = Double.parseDouble(item.getText(1)) * 100 / totalRowsCount;
                    Color foreground = gc.getForeground();
                    Color background = gc.getBackground();
                    gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
                    gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
                    int width = (column3.getWidth() - 1) * (int) percent / 100;
                    gc.fillGradientRectangle(event.x, event.y, width, event.height, true);
                    gc.drawRectangle(new Rectangle(event.x, event.y, width - 1, event.height - 1));
                    gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
                    String text = new DecimalFormat("###.##").format(percent) + "%";  
                    Point size = event.gc.textExtent(text);
                    int offset = Math.max(0, (event.height - size.y) / 2);
                    gc.drawText(text, event.x, event.y + offset, true);
                    gc.setForeground(background);
                    gc.setBackground(foreground);
                }
            }
        });

        _shell.addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                final int width = table.getBounds().width - table.getBorderWidth()*2;
                column1.setWidth(width / WIDTH_RATE_COLUMN_1);
                column2.setWidth(width / WIDTH_RATE_COLUMN_2);
                column3.setWidth(width / WIDTH_RATE_COLUMN_3);
            }
        });

        return comp;
    } */
}
