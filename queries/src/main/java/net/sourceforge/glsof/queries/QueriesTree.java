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

import net.sourceforge.glsof.common.io.PreferencesRepository;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.preferences.PreferencesDialog;
import net.sourceforge.glsof.common.utils.MessageDialog;
import net.sourceforge.glsof.queries.thread.Logger;
import net.sourceforge.glsof.queries.thread.ProcessTable;
import net.sourceforge.glsof.queries.thread.QueryNode;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class QueriesTree extends JTree implements TreeSelectionListener {

    private PreferencesRepository _queryPreferencesRepository;
    private DefaultMutableTreeNode _root;
    private JPanel _queryTablesContainer;
    private JPanel _searchBarContainer;
    private JLabel _statusBar;
    private Logger _logger;

    private String _selectedQuery;
    private String _selectedProcess;
    private QueryNode _queryNode;

    public QueriesTree(PreferencesRepository repository, JPanel searchBarContainer, JPanel queryTablesContainer, JLabel statusBar) {
        super();
        _queryPreferencesRepository = repository;
        setCellRenderer(new IconNodeRenderer());
        addTreeSelectionListener(this);
        setEditable(true);
        setRootVisible(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        _queryTablesContainer = queryTablesContainer;
        _searchBarContainer = searchBarContainer;
        _statusBar = statusBar;
        //new QueriesSearchBar(searchBarContainer, this, _queries);
        populateTree();
        //createLogger(_vForm, new ArrayList[]{conf.getLogStartColor(), conf.getLogStopColor(), conf.getLogErrColor()});
        addPopupMenu();
        queryNameEditor();
    }

    private void queryNameEditor() {
        setCellEditor(new DefaultCellEditor(new JTextField()) {
            private String _oldName;

            @Override
            public boolean isCellEditable(EventObject event) {
                if (!super.isCellEditable(event)) return false;
                if (event != null && event.getSource() instanceof JTree && event instanceof MouseEvent) {
                    MouseEvent mouseEvent = (MouseEvent) event;
                    JTree tree = (JTree) event.getSource();
                    TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
                    _oldName = (String) ((DefaultMutableTreeNode) (path.getLastPathComponent())).getUserObject();
                    return path.getPathCount() == 2; // depth
                }
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return queryRenamed(_oldName, (String) getCellEditorValue()) ? super.stopCellEditing() : true;
            }
        });
    }

    private void addPopupMenu() {
        final JPopupMenu popup = createPopupMenu();
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    TreePath tp = getClosestPathForLocation(e.getX(), e.getY());
                    if (tp != null) setSelectionPath(tp);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mousePressed(MouseEvent e) {
                doAction(e, popup);
            }

            private void doAction(MouseEvent e, JPopupMenu popup) {
                TreePath selPath = getPathForLocation(e.getX(), e.getY());
                if (selPath == null) return;
                else setSelectionPath(selPath);
                if (e.isPopupTrigger()) {
                    popup.show((JComponent) e.getSource(), e.getX(), e.getY());
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
                    if (node != null && !node.isLeaf())
                        popup.show((JComponent) e.getSource(), e.getX(), e.getY());
                }
            }
        });
    }

    public void createLogger(JSplitPane vForm, ArrayList<Integer>[] cols) {
        _logger = new Logger();
        _logger.create(vForm, cols[0], cols[1], cols[2]);
    }

    private void populateTree() {
        _root = new QueryNode("root");
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(_root);
        setModel(defaultTreeModel);
        for (String name : _queryPreferencesRepository.findAllNames()) {
            // aggiungere colore ROSSO-SCURO BOLD
            addNewQueryNode(name);
        }
        defaultTreeModel.reload();
    }

    public boolean queryCreated(String name) {
        if (queryIsExistent(name)) {
            MessageDialog.error(this, NLS("There_is_already_a_query_with_this_name"));
            return false;
        }
        if (openDialog(Preferences.from(name))) {
            addNewQueryNode(name);
            return true;
        }
        return false;
    }

    private void addNewQueryNode(String name) {
        _root.add(new QueryNode(this, _searchBarContainer, _queryTablesContainer, _logger, name));
    }

    public boolean queryRenamed(final String oldName, final String newName) {
        if (newName == null || newName.length() == 0) return false;
        if (_queryPreferencesRepository.rename(oldName, newName)) {
            _queryNode.setUserObject(newName);
            return true;
        }
        return false;
    }

    public void setColumnsViews(final List<Boolean> views, final int index) {
        for (JTable table : _queryNode.getTables().values()) {
            ((ProcessTable) table).showColumn(views.get(index), index);
        }
        _queryPreferencesRepository.saveColumns(views, _selectedQuery);
    }

    public List<Boolean> getColumnsViews() {
        return _queryPreferencesRepository.getColumns(_selectedQuery);
    }

    public void stopAllTheQueries() {
        for (int i = 0; i < _root.getChildCount(); i++) ((QueryNode) _root.getChildAt(i)).stop();
    }

    public boolean isSelectedQueryRunning() {
        return _queryNode.isRunning();
    }

    public boolean isSelectedQueryReady() {
        final QueryNode node = _queryNode;
        return node.getChildCount() > 0 && !node.isRunning();
    }

    public Map<String, Color> getLoggerColors() {
        return _logger.getColors();
    }

    private boolean queryIsExistent(String name) {
        for (int i = 0; i < _root.getChildCount(); i++)
            if (((QueryNode) _root.getChildAt(i)).getUserObject().toString().equals(name)) return true;
        return false;
    }

    public void expandAllQueries(final boolean expand) {
        for (int i = 0; i < _root.getChildCount(); i++) {
            final TreePath path = new TreePath(((QueryNode) _root.getChildAt(i)).getPath());
            if (expand) expandPath(path);
            else collapsePath(path);
        }
    }

    // POPUP MENU

    private JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        initMenuItem(menu, NLS("Edit_query"), new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                openDialog(_queryPreferencesRepository.read(_selectedQuery));
            }
        });
        initMenuItem(menu, NLS("Remove_query"), new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                _queryPreferencesRepository.remove(_selectedQuery);
                ((DefaultTreeModel) getModel()).removeNodeFromParent(_queryNode);
            }
        });
        if (getSelectionPath() != null && isSelectedQueryRunning()) {
            initMenuItem(menu, NLS("Stop_query"), new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    _queryNode.stop();
                }
            });
        } else {
            initMenuItem(menu, NLS("Run_query"), new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    _queryNode.removeAllChildren();
                    ((DefaultTreeModel) getModel()).reload();
                    _queryNode.runLsof(_queryPreferencesRepository.read(_selectedQuery), _queryPreferencesRepository.getColumns(_selectedQuery));
                }
            });
        }
        menu.add(new JPopupMenu.Separator());
//        columns(menu);
//        stats(menu);
        return menu;
    }

    private boolean openDialog(Preferences prefs) {
        final PreferencesDialog dialog = new PreferencesDialog(prefs, "name preference here please");
        dialog.create();
        dialog.setVisible(true);
        if (dialog.getButtonClickedId() == PreferencesDialog.SAVE_ID) {
            _queryPreferencesRepository.save(prefs);
        }
//        else if (dialog.getButtonClickedId() == PreferencesDialog.OK_RESTART_ID) {
//        	_queryPreferencesRepository.save(prefs);
//            stopAllThreads();
//            _searchBar.resetAll();
//            _table.clear();
//        }
        /*PreferencesUIDialog dialog = new PreferencesUIDialog(queryPreferences);
        if (dialog.isClickedOk()) {
            save(queryPreferences);
            return true;
        } */
        return false;
    }

    private void stats(JPopupMenu menu) {
        final JMenuItem item = initMenuItem(menu, NLS("Statistics"), new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                final String name = _selectedQuery;
                final QueryNode node = _queryNode;
                /*new QueryStatisticsDialog(
                            node.getTables(),
                            node.get,
                            name
                            ).create();*/
            }
        });
        item.setEnabled(isSelectedQueryReady());
    }

    private void columns(JPopupMenu menu) {
        final JMenu columns = new JMenu(NLS("Columns"));
        menu.add(columns);
        if (isSelectedQueryReady()) {
            final List<Boolean> views = getColumnsViews();
//            for (int i = 2; i < COLUMN_TITLE_TEXT.length; i++) {
//                final JCheckBoxMenuItem item = new JCheckBoxMenuItem(COLUMN_TITLE_TEXT[i]);
//                columns.add(item);
//                item.setSelected(views.get(i - 2));
//                final int index = i - 2;
//                item.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent event) {
//                        views.set(index, item.isSelected());
//                        setColumnsViews(views, index);
//                    }
//                });
//            }
        } else columns.setEnabled(false);
    }

    private JMenuItem initMenuItem(final JPopupMenu menu, final String itemName, final ActionListener listener) {
        final JMenuItem item = new JMenuItem(itemName);
        menu.add(item);
        item.addActionListener(listener);
        return item;
    }

    @Override
    public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
        if (getLastSelectedPathComponent() == null) return;
        _queryTablesContainer.removeAll();
        if (getLastSelectedPathComponent() instanceof QueryNode) {
            _queryNode = (QueryNode) getLastSelectedPathComponent();
            _selectedQuery = _queryNode.getUserObject().toString();
            _selectedProcess = null;
            _statusBar.setText(NLS("Query") + " <" + _selectedQuery + ">. " + NLS("Processes") + ": " + _queryNode.getChildCount());
            _queryTablesContainer.updateUI();
        } else {
            final String name = ((DefaultMutableTreeNode) getLastSelectedPathComponent()).getUserObject().toString();
            JTable table = _queryNode.getTableForProcess(name);
            _statusBar.setText(NLS("Process") + " <" + name + "> " + NLS("lines") + ": " + table.getRowCount());
            _selectedProcess = name;
            _selectedQuery = null;
            _queryTablesContainer.add(name, new JScrollPane(table));
        }
    }
}

class IconNodeRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (!(value instanceof QueryNode)) return this;
        Icon icon = ((QueryNode) value).getIcon();
        if (icon != null) setIcon(icon);
        return this;
    }
}