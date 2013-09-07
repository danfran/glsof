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
package net.sourceforge.glsof.filemonitor;

import net.sourceforge.glsof.common.main.AbstractMainWindow;
import net.sourceforge.glsof.common.main.ActionButton;
import net.sourceforge.glsof.common.main.Observer;
import net.sourceforge.glsof.common.utils.GBC;
import net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository;
import net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConf;
import net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConfRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.awt.GridBagConstraints.*;
import static javax.swing.SwingUtilities.invokeLater;
import static net.sourceforge.glsof.common.i18n.Messages.*;
import static net.sourceforge.glsof.common.utils.MessageDialog.warning;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository.getCurrentPreferences;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConfRepository.getUiConf;

public class FileMonitorWindow extends AbstractMainWindow implements Observer {

    private static final String START_BUTTON = "/icons/fm-start.png";
    private static final String STOP_BUTTON = "/icons/fm-stop.png";
    private static final String AUTOSCROLL_BUTTON = "/icons/autoscroll.png";
    private static final String AUTOSCROLL_STOP_BUTTON = "/icons/autoscroll-stop.png";
    private static final String CLEAR_BUTTON = "/icons/fm-clear.png";
    private static final String SETTINGS_DIR = ".glsof" + File.separator + "filemonitor";
    private static final String MENU_ICON = "/icons/menu.png";
    private static final String WEBSITE_URL = "http://glsof.sourceforge.net/";
    private static final String DOCUMENTATION_URL = "http://glsof.sourceforge.net/filemonitor/";

    private FileMonitorPreferencesRepository _preferencesRepository;
    private FileMonitorUIConfRepository _uiConfRepository;
    private JMenuItem _showPreferencesMenuItem;
    private List<Observer> _observers;

    @Override
    protected String version() {
        return "2.4.0";
    }

    public static void main(String[] args) {
        initNLS("nl.common");
        invokeLater(new Runnable() {
            public void run() {
                new FileMonitorWindow();
            }
        });
    }

    public FileMonitorWindow() {
        super(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0], NLS("GLSOF_Filemonitor"));
    }

    @Override
    protected void initRepositories() {
        final File settingsDir = createSettingsDir(SETTINGS_DIR);
        _uiConfRepository = new FileMonitorUIConfRepository(settingsDir.getParentFile());
        _preferencesRepository = new FileMonitorPreferencesRepository(settingsDir);
    }

    @Override
    protected void createContents(JPanel mainContainer) {

        final FileMonitorUIConf uiConf = _uiConfRepository.read();

        final MonitorTable monitorTable = new MonitorTable(_preferencesRepository);
        final PreferencesTable preferencesTable = new PreferencesTable(monitorTable, _preferencesRepository);
        final MonitorPreferencesContainer hForm = new MonitorPreferencesContainer(monitorTable.pack(), preferencesTable.pack());
        final FilterBar filterBar = new FilterBar(monitorTable);

        _observers = new LinkedList<Observer>(Arrays.asList(this, monitorTable, preferencesTable, hForm, filterBar));

        final JButton autoscrollButton = new ActionButton(_observers, uiConf.isAutoscroll() ? AUTOSCROLL_STOP_BUTTON : AUTOSCROLL_BUTTON, "Autoscroll") {
            @Override
            protected void execute() {
                final boolean autoscroll = !uiConf.isAutoscroll();
                uiConf.setAutoscroll(autoscroll);
                getButton().setIcon(getIcon(autoscroll ? AUTOSCROLL_STOP_BUTTON : AUTOSCROLL_BUTTON));
                notify(NOTIFY.AUTOSCROLL, autoscroll);
            }
        }.getButton();

        final JButton clearButton = new ActionButton(_observers, CLEAR_BUTTON, "Clear") {
            @Override
            protected void execute() {
                notify(NOTIFY.CLEAR);
            }
        }.getButton();

        final JButton startButton = new ActionButton(_observers, START_BUTTON, START) {
            @Override
            protected void execute() {
                if (getButton().getToolTipText().equals(START)) {
                    try {
                        if (readyToStart()) {
                            notify(Observer.NOTIFY.START);
                            changeStartButton(STOP, STOP_BUTTON);
                        }
                    } catch (Exception e) {
                        warning(null, NLS("Start_problem"));
                    }
                } else {
                    notify(Observer.NOTIFY.STOP);
                    changeStartButton(START, START_BUTTON);
                }
            }

            private void changeStartButton(String text, String buttonIcon) {
                getButton().setToolTipText(text);
                getButton().setIcon(getIcon(buttonIcon));
            }

            private boolean readyToStart() {
                if (preferencesTable.noPreferences()) {
                    warning(FileMonitorWindow.this, NLS("Create_Preference_Before_Start"));
                    return false;
                }
                if (preferencesTable.noSelectedPreferences()) {
                    warning(FileMonitorWindow.this, NLS("Select_Preference_Before_Start"));
                    return false;
                }
                return true;
            }
        }.getButton();

        mainContainer.add(filterBar, new GBC().gridx(0).gridy(0).fill(HORIZONTAL).weightx(1.0).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(new JSeparator(JSeparator.VERTICAL), new GBC().gridx(1).gridy(0).fill(BOTH).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(autoscrollButton, new GBC().gridx(2).gridy(0).fill(NONE).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(clearButton, new GBC().gridx(3).gridy(0).fill(NONE).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(startButton, new GBC().gridx(4).gridy(0).fill(NONE).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(_menuBar, new GBC().gridx(5).gridy(0).fill(HORIZONTAL).insets(new Insets(5, 5, 5, 5)).get());
        mainContainer.add(hForm, new GBC().gridx(0).gridy(1).gridwidth(6).fill(BOTH).weightx(1.0).weighty(1.0).insets(new Insets(5, 5, 5, 5)).get());

        setWindowBounds(uiConf.getWindowBounds());
        hForm.pack();
    }

    @Override
    protected void createMenuItems() {
        final ImageIcon icon = new ImageIcon(getClass().getResource(MENU_ICON));
        _menuBar.setBorder(BorderFactory.createEtchedBorder());
        _menuBar.setMinimumSize(new Dimension(icon.getIconWidth() + 16, icon.getIconHeight()));
        final JMenu menu = createMenu(icon, NLS("Menu"));
        menu.setMnemonic(KeyEvent.VK_M);
        createShowPreferencesItem(menu);
        createFullscreenItem(menu);
        createAboutItem(menu, ABOUT_LOGO, NLS("Description"), version(), WEBSITE_URL, DOCUMENTATION_URL);
        menu.addSeparator();
        createExitItem(menu);
    }

    private void createShowPreferencesItem(JMenu menu) {
        _showPreferencesMenuItem = createMenuItem(menu, "show-preferences", NLS("Preferences"), KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK), null);
        _showPreferencesMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                FileMonitorWindow.this.notify(NOTIFY.TOGGLE, getWidth());
            }
        });
    }

    @Override
    public void update(NOTIFY notify, Object... params) {
        if (notify == NOTIFY.START) {
            setTitle(NLS("Current_preference") + getCurrentPreferences().getName());
            _showPreferencesMenuItem.setEnabled(false);
        } else if (notify == NOTIFY.STOP) {
            _showPreferencesMenuItem.setEnabled(true);
        }
    }

    private void notify(Observer.NOTIFY notify, Object... params) {
        for (Observer observer : _observers) observer.update(notify, params);
    }

    @Override
    protected void close() {
        notify(NOTIFY.CLOSE);
        getUiConf().setWindowBounds(getBounds());
        _uiConfRepository.save(getUiConf());
    }
}