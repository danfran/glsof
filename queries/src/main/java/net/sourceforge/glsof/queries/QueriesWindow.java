/* This 1file is part of Glsof.

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
import net.sourceforge.glsof.common.main.AbstractMainWindow;
import net.sourceforge.glsof.queries.repository.QueriesUIConf;
import net.sourceforge.glsof.queries.repository.QueriesUIConfRepository;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import static javax.swing.SwingUtilities.invokeLater;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.common.i18n.Messages.initNLS;

public class QueriesWindow extends AbstractMainWindow {

    private static final String SETTINGS_DIR = ".glsof" + File.separator + "queries";
    private static final String ABOUT_LOGO = "/icons/glsof-banner-q.png";
    private static final String WEBSITE_URL = "http://glsof.sourceforge.net/";
    private static final String DOCUMENTATION_URL = "http://glsof.sourceforge.net/queries/";

    private QueriesUIConfRepository _queriesUIConfRepository;
    private PreferencesRepository _queryPreferencesRepository;
    private QueriesTree _queriesTree;
    private JMenuItem _statusBarItem;
    private JMenuItem _logItem;
    private JSplitPane _vForm;
    private JSplitPane _hForm;
    private int _queriesHeight;
    private QueriesUIConf _uiConf;

    @Override
    protected String version() {
        return "1.5.0";
    }

    public static void main(final String[] args) {
        initNLS("nl.common");
        invokeLater(new Runnable() {
            public void run() {
                new QueriesWindow();
            }
        });
    }

    @Override
    protected void initRepositories() {
        final File dir = createSettingsDir(SETTINGS_DIR);
        _queriesUIConfRepository = new QueriesUIConfRepository(dir.getParentFile());
        _queryPreferencesRepository = new PreferencesRepository(dir);
        _uiConf = _queriesUIConfRepository.read();
    }

    public QueriesWindow() {
        super(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0], NLS("GLSOF_Queries"));
    }

//    protected void createMenuItems() {
//        final JMenu fileMenu = createMenu(NLS("FFile"));
//        createExitItem(fileMenu);
//        final JMenu viewMenu = createMenu(NLS("VView"));
//        _logItem = createMenuItem(viewMenu, 1, NLS("LLogs_CtrlL"), KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK), "");
//        _statusBarItem = createMenuItem(viewMenu, 1, NLS("SStatus_Bar_CtrlS"), KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK), "");
//        createFullscreenItem(viewMenu);
//        final JMenu helpMenu = createMenu(NLS("HHelp"));
//        createAboutItem(helpMenu, ABOUT_LOGO, NLS("A_multi_LSOF-instances_manager"));
//    }

    protected void createContents(final JPanel main) {
        main.setLayout(new BorderLayout());
        setWindowBounds(new Rectangle(_uiConf.getPosX(), _uiConf.getPosY(), _uiConf.getWidth(), _uiConf.getHeight()));
        final JPanel searchBarContainer = new JPanel();
//        
//        searchBarContainer.add(_menuBar);
//        
//        main.add(searchBarContainer, BorderLayout.PAGE_START);
        _vForm = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        main.add(_vForm, BorderLayout.CENTER);
        _hForm = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel treeContainer = new JPanel(new BorderLayout());

        //create toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        treeContainer.add(toolBar, BorderLayout.PAGE_START);

        toolBar.add(_menuBar);

        JButton addItem = new JButton(new ImageIcon(getClass().getResource("/icons/gtk-new.png")));
        addItem.setToolTipText(NLS("Add_query"));
        toolBar.add(addItem);
        JButton expItem = new JButton(new ImageIcon(getClass().getResource("/icons/exp-all.png")));
        expItem.setToolTipText(NLS("Expand_all_the_queries"));
        toolBar.add(expItem);
        JButton colItem = new JButton(new ImageIcon(getClass().getResource("/icons/col-all.png")));
        colItem.setToolTipText(NLS("Collapse_all_the_queries"));
        toolBar.add(colItem);

        // create tree
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        //final JTree tree = new QueriesTree(treeModel);

        _hForm.setLeftComponent(treeContainer);
        JPanel tablesContainer = new JPanel(new CardLayout());
        _hForm.setRightComponent(tablesContainer);
        _vForm.setTopComponent(_hForm);

        // create status bar
        final JPanel statusBarContainer = new JPanel();
        main.add(statusBarContainer, BorderLayout.PAGE_END);
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statusBarContainer.add(label);

        _queriesTree = new QueriesTree(_queryPreferencesRepository, searchBarContainer, tablesContainer, label);
        treeContainer.add(
                new JScrollPane(_queriesTree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER);
        //_queriesTree.createLogger(_vForm, new ArrayList[]{conf.getLogStartColor(), conf.getLogStopColor(), conf.getLogErrColor()});

        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final String name = JOptionPane.showInputDialog(QueriesWindow.this, NLS("Enter_querys_name"));
                if (name != null && name.length() > 0 && _queriesTree.queryCreated(name)) {
                    DefaultMutableTreeNode queryNode = new DefaultMutableTreeNode(name);
                    treeModel.insertNodeInto(queryNode, rootNode, rootNode.getChildCount());
                    _queriesTree.scrollPathToVisible(new TreePath(queryNode.getPath()));
                }
            }
        });
        expItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                _queriesTree.expandAllQueries(true);
            }
        });
        colItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                _queriesTree.expandAllQueries(false);
            }
        });
        _hForm.setDividerLocation(_uiConf.getQueriesTreeWidth());
        _vForm.setDividerLocation(_uiConf.getQueriesHeight());

        _queriesHeight = _uiConf.getQueriesHeight();
//        _logItem.setSelected(conf.isMenuItemLogs());
//        _statusBarItem.setSelected(conf.isMenuItemStatusBar());
//        _logItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                if (_logItem.isSelected()) {
//                    _vForm.setDividerLocation(_queriesHeight);
//                } else {
//                    _queriesHeight = _vForm.getDividerLocation();
//                    _vForm.setDividerLocation(_vForm.getHeight());
//                }
//            }
//        });
//        _statusBarItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent event) {
//                statusBarContainer.setVisible(!_statusBarItem.isSelected());
//            }
//        });
//        if (_logItem.isSelected()) Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ActionEvent(_logItem, 0, ""));
//        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new ActionEvent(_statusBarItem, 0, ""));
    }

    protected void createMenuItems() {
        _menuBar.setBorder(BorderFactory.createEtchedBorder());//1,1,1,1,Color.BLACK));
        // provide an icon here pls
        final JMenu menu = createMenu(null, NLS("Menu"));
        menu.setMnemonic(KeyEvent.VK_M);
        createFullscreenItem(menu);
        createAboutItem(menu, ABOUT_LOGO, NLS("A_monitor_for_open_files_somewhere_in_the_system"), version(), WEBSITE_URL, DOCUMENTATION_URL);
        menu.addSeparator();
        createExitItem(menu);
    }

    protected void close() {
        _queriesUIConfRepository.save(
                getBounds(),
                _queriesTree.getLoggerColors(),
                _hForm.getDividerLocation(),
                _vForm.getDividerLocation() == 0 ? _queriesHeight : _vForm.getDividerLocation(),
                _logItem.isSelected(),
                _statusBarItem.isSelected());
        _queriesTree.stopAllTheQueries();
    }

}
