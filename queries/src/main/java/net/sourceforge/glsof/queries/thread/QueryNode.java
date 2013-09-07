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

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.sourceforge.glsof.common.model.Preferences;

public class QueryNode extends DefaultMutableTreeNode implements Observer {

    private static final String LOADING_QUERY = "/icons/loading.gif";

    private Map<String, JTable> _tables = new LinkedHashMap<String, JTable>();
    private List<Boolean> _columnsViews;
    private JPanel _searchBarContainer;
    private JPanel _tablesContainer;
    private Logger _logger;
    private QueriesLsofExecutorThread _parser;
    private ImageIcon _icon;
    private DefaultTreeModel _treeModel;
    private JTree _tree;

    public QueryNode(String name) { super(name); }

    public QueryNode(JTree tree, JPanel searchBarContainer, JPanel tablesContainer, Logger logger, String name) {
        super(name);
        _tree = tree;
        _treeModel = (DefaultTreeModel)tree.getModel();
        _searchBarContainer = searchBarContainer;
        _tablesContainer = tablesContainer;
        _logger = logger;
    }

    public Map<String, JTable> getTables() { return _tables; }

    // Observer pattern
    //TODO REVIEW ALL THE QUERY PART
    public void update(Observable observed, final Object buffer) {
//        if (observed instanceof LsofOutputParser) {
//            if (buffer == null) { // this means lsof has finished
//                _icon = null;
//                _searchBarContainer.setEnabled(true);
//                _tree.expandPath(new TreePath(getPath()));
////                _logger.logMessage(NLS("Query") + " <" + getUserObject() + ">. " + NLS("Processes") + ": " + getChildCount(), STOP_COLOR);
//                return;
//            }
//            displayLsofOutput((String[]) buffer);
//            return;
//        }
//        _logger.logMessage(NLS("Query") + " <" + getUserObject() + ">. " + NLS("Error") + ": " + buffer, ERR_COLOR);
    }

    private void displayLsofOutput(String[] buffer) {
        final boolean newItem = _tables.get(buffer[0]) == null;
        Object[] newRow = new Object[buffer.length - 1];
        System.arraycopy(buffer, 1, newRow, 0, newRow.length);
        ((DefaultTableModel) getTableForProcess(buffer[0]).getModel()).addRow(newRow);
        if (newItem) _treeModel.insertNodeInto(new DefaultMutableTreeNode(buffer[0]), this, getChildCount());
    }

    public void runLsof(Preferences prefs, List<Boolean> columnsViews) {
        if (_parser == null || !_parser.isRunning()) {
            _tables.clear();
            _columnsViews = columnsViews;
//            _logger.logMessage(NLS("Query") + " <" + getUserObject() + ">. " + NLS("Parameters") + ": " + lsofParameters, START_COLOR);
            _parser = new QueriesLsofExecutorThread(this, prefs);
            _searchBarContainer.setEnabled(false);
            createLoaderIcon();
            new Thread(_parser).start();
        }
    }

    public Icon getIcon() { return _icon; }

    private void createLoaderIcon() {
        _icon = new ImageIcon(getClass().getResource(LOADING_QUERY));
        _icon.setImageObserver(new ImageObserver() {
            public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
                if ((flags & (FRAMEBITS | ALLBITS)) != 0) {
                    final TreePath path = new TreePath(_treeModel.getPathToRoot(QueryNode.this));
                    final Rectangle rect = _tree.getPathBounds(path);
                    if (rect != null) _tree.repaint(rect);
                }
                return (flags & (ALLBITS | ABORT)) == 0;
            }
        });
    }

    public void stop() {
        if (_parser != null) {
            _parser.stop();
        }
    }

    public boolean isRunning() { return _parser == null ? false : _parser.isRunning(); }

    public JTable getTableForProcess(String name) {
        JTable table = _tables.get(name);
        return table != null ? table : createNewTable(name);
    }

    private JTable createNewTable(String name) {
        final DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new ProcessTable(model, _columnsViews);
        _tables.put(name, table);
//        _tablesContainer.add(name, new JScrollPane(table));
        return table;
    }

}
