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
package net.sourceforge.glsof.queries;

import net.sourceforge.glsof.common.main.AbstractFilterBar;
import net.sourceforge.glsof.queries.thread.QueryNode;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueriesSearchBar extends AbstractFilterBar {

    //    private final QuerySearchProcessColumn _searchProcessColumn;
//    private final QuerySearchTablesColumns _searchTablesColumns;
//    private final QuerySearchEverywhere _searchEverywhere;
    private Map<String, QueryNode> _queries;
    private JTree _queryTree;
    private int _lastSelectedColumn;

    private int _countSearchBarView = 0;

    public QueriesSearchBar(final JPanel parent, JTree queryTree, Map<String, QueryNode> queries) {
        super();
        _queryTree = queryTree;
        _queries = queries;
//        final TextMatcherInfo textMatcherInfo = new TextMatcherInfo();
//        _searchProcessColumn = new QuerySearchProcessColumn(textMatcherInfo);
//        _searchTablesColumns = new QuerySearchTablesColumns(textMatcherInfo);
//        _searchEverywhere = new QuerySearchEverywhere(textMatcherInfo);
        parent.addPropertyChangeListener("enabled", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (++_countSearchBarView == 1) {
//                    enableWidgets(false);
                    resetAll();
                } else if (--_countSearchBarView == 0) {
//                    enableWidgets(true);
                }
            }
        });
    }

    protected void search(final JComboBox comboColumnsList, final JTextField comboSearch, final JCheckBox buttonCase) {
        final int selectedColumn = comboColumnsList.getSelectedIndex();
        if (selectedColumn != _lastSelectedColumn) {
            _lastSelectedColumn = selectedColumn;
            resetAll();
        }
        search(comboColumnsList, comboSearch, buttonCase, selectedColumn);
    }

    private void resetAll() {
//        _searchProcessColumn.reset();
//        _searchTablesColumns.reset();
//        _searchEverywhere.reset();
    }

    private void search(final JComboBox comboColumnsList, final JTextField comboSearch, final JCheckBox buttonCase, int selectedColumn) {
        if (selectedColumn == 0) {
            runSearchEverywhere(comboSearch.getText(), buttonCase.isSelected());
        } else if (selectedColumn == 1) {
            runSearchProcessColumn(comboSearch.getText(), buttonCase.isSelected());
        } else {
            runSearchTablesColumns(comboSearch.getText(), comboColumnsList.getSelectedIndex() - 2, buttonCase.isSelected());
        }
    }

    public void runSearchProcessColumn(final String text, final boolean caseSensitive) {
//        _searchProcessColumn.init(text, caseSensitive);
//        runSearch(_searchProcessColumn, text);
    }

    public void runSearchTablesColumns(final String text, final int columnIndex, final boolean caseSensitive) {
//        _searchTablesColumns.init(text, columnIndex, caseSensitive);
//        runSearch(_searchTablesColumns, text);
    }

    public void runSearchEverywhere(final String text, final boolean caseSensitive) {
//        _searchEverywhere.init(text, caseSensitive);
//        runSearch(_searchEverywhere, text);
    }

//    private void runSearch(final QuerySearchInterface search, final String text) {
//        if (search.hasFoundMatch(retrieveAllTables(), _queryTree, text)) {
//            highlightQueryProcess(search.getQueryIndex(), search.getProcessIndex());
//        } else if (true){//getMessageBox().open() == SWT.YES) {
//            search.reset();
//            runSearch(search, text);
//        } else if (search instanceof QuerySearchEverywhere) { // that's horrible!
//            ((QuerySearchEverywhere) search).restoreReset();
//        }
//    }

    private void highlightQueryProcess(int queryIndex, int processIndex) {
        /*TreeItem treeItem = getQueryTreeItem(queryIndex);
        treeItem.setExpanded(true);
        TreeItem processTreeItem = treeItem.getItems()[processIndex];
        treeItem.getParent().setSelection(processTreeItem);
        Event event = new Event();
        event.item = processTreeItem;
        treeItem.getParent().notifyListeners(SWT.Selection, event);*/
    }

    private ArrayList<Map<String, JTable>> retrieveAllTables() {
        ArrayList<Map<String, JTable>> queriesTables = new ArrayList<Map<String, JTable>>();
        for (QueryNode query : _queries.values())
            queriesTables.add(query.getTables() == null ? new HashMap<String, JTable>() : query.getTables());
        return queriesTables;
    }

    @Override
    protected void filter() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void createColumnsList() {
        // TODO Auto-generated method stub

    }

    /*private TreeItem getQueryTreeItem(int queryIndex) {
        return (new LinkedList<QueryNode>(_queries.values())).get(queryIndex).getQueryTreeItem();
    } */

}