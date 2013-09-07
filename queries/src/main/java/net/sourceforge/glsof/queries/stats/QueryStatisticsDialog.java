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

public class QueryStatisticsDialog { /*extends AbstractButtonsDialog {

    private QueryStatistics _queryStatistics;

    private String _queryName;

    private boolean _showFirstChart = true;

    public QueryStatisticsDialog(Map<String, JTable> tables, TreeItem[] items, String queryName) {
        super(shell, NLS("Statistics_for_query")); 
        _queryStatistics = new QueryStatistics();
        _queryStatistics.calculate(items, tables);
        _queryName = queryName;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        getShell().setMinimumSize(800,600);

        final Composite main = new Composite(parent, SWT.NONE);
        main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        main.setLayout(new GridLayout(2, true));

        final Label label = new Label(main, SWT.NONE);
        label.setText(_queryName);
        final FontData labelFontData = new FontData("Arial", 14, SWT.BOLD); 
        Font font = new Font(getShell().getDisplay(), labelFontData);
        label.setFont(font);
        font.dispose();

        final Button pageButton = new Button(main, SWT.PUSH);
        pageButton.setText(NLS("Change_chart")); 
        pageButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));

        final Composite chartsContainer = new Composite(main, SWT.NONE);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.horizontalSpan = 2;
        chartsContainer.setLayoutData(data);
        final StackLayout stackLayout = new StackLayout();
        chartsContainer.setLayout(stackLayout);

        final QueryStatisticsChartFactory factory = new QueryStatisticsChartFactory(getShell());
        final Composite firstChart =
                factory.create(
                        chartsContainer,
                        new String[] {NLS("Processes"), NLS("Rows"), NLS("Percent")},   
                        _queryStatistics.getProcesses(),
                        _queryStatistics.getTotalRowsCount());
        final Composite secondChart =
                factory.create(
                        chartsContainer,
                        new String[] {NLS("Users"), NLS("Rows"), NLS("Percent")},   
                        _queryStatistics.getUsers(),
                        _queryStatistics.getTotalRowsCount());

        pageButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                _showFirstChart = !_showFirstChart;
                stackLayout.topControl = _showFirstChart ? firstChart : secondChart;
                chartsContainer.layout();
            }
        });

        stackLayout.topControl = firstChart;
        chartsContainer.layout();
        chartsContainer.pack();

        return main;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, CLOSE_ID, NLS("Close")); 
    }

    @Override
    protected void buttonPressed(int buttonId) {
        close();
    }
                                       */
}
