package net.sourceforge.glsof.filemonitor;

import net.sourceforge.glsof.common.main.ActionButton;
import net.sourceforge.glsof.common.main.Observer;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.preferences.PreferencesDialog;
import net.sourceforge.glsof.common.utils.GBC;
import net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.common.utils.MessageDialog.input;
import static net.sourceforge.glsof.common.utils.MessageDialog.warning;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConfRepository.getUiConf;

class PreferencesTable extends JTable implements Observer {

    private MonitorTable _monitorTable;

    private FileMonitorPreferencesRepository _preferencesRepository;

    private PreferencesToolBar _toolBar;

    PreferencesTable(MonitorTable monitorTable, FileMonitorPreferencesRepository repository) {
        _monitorTable = monitorTable;

        _preferencesRepository = repository;

        final String selectedPreference = getUiConf().getSelectedPreference();

        repository.load(selectedPreference);

        _toolBar = new PreferencesToolBar();

        setModel(new PreferencesTableModel(repository.findAllNames(), selectedPreference));

        getColumn("#").setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });
        getColumn("#").setMaxWidth(20);
        getColumn("#").setMinWidth(20);
        getColumn("#").setCellEditor(new RadioButtonEditor());

        setBorder(null);
        setOpaque(false);

        setSelectionMode(SINGLE_SELECTION);
        setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
        setCellSelectionEnabled(true);
        putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        ListSelectionModel lsm = (ListSelectionModel) event.getSource();
                        if (lsm.isSelectionEmpty())
                            _toolBar.enableToolBarButtons(true, false, false, false);
                        else {
                            changeSelection(lsm.getMinSelectionIndex(), 1, false, false);
                            requestFocus();
                            _toolBar.enableToolBarButtons(true, true, true, true);
                        }
                    }
                }
        );

        getColumn(NLS("Preferences")).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {

                final String newProfileName = ((JTextField) getComponent()).getText();
                final String oldProfileName = getTableModel().getNameAt(getEditingRow());

                return _preferencesRepository.rename(newProfileName, oldProfileName) && super.stopCellEditing();
            }
        });
    }

    private PreferencesTableModel getTableModel() {
        return (PreferencesTableModel) getModel();
    }

    JPanel pack() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(_toolBar, new GBC().gridx(0).gridy(0).weightx(0).weighty(0).fill(GridBagConstraints.HORIZONTAL).anchor(GridBagConstraints.LINE_START).get());
        panel.add(new JScrollPane(this), new GBC().gridx(0).gridy(1).weightx(1).weighty(1).fill(GridBagConstraints.BOTH).anchor(GridBagConstraints.NORTH).get());
        return panel;
    }

    @Override
    public void update(NOTIFY notify, Object... params) {
        if (notify == NOTIFY.CLOSE) {
            getUiConf().setSelectedPreference(getRunningPreferencesName());
        }
    }

    private class PreferencesToolBar extends JToolBar {

        private PreferencesToolBar() {
            setFloatable(false);
            setBorder(new EtchedBorder());
            setOpaque(false);

            add(new ActionButton("/icons/add.png", "Add_preference") {
                @Override
                protected void execute() {
                    addNewPreferences();
                }
            }.getButton());

            add(new ActionButton("/icons/edit-properties.png", "Edit_preference") {
                @Override
                protected void execute() {
                    editPreference();
                }
            }.getButton());

            add(new ActionButton("/icons/edit-copy.png", "Copy_preference") {
                @Override
                protected void execute() {
                    copyPreference();
                }
            }.getButton());

            add(new ActionButton("/icons/remove.png", "Remove_preference") {
                @Override
                protected void execute() {
                    removePreference();
                }
            }.getButton());

            enableToolBarButtons(true, false, false, false);
        }

        void enableToolBarButtons(boolean... show) {
            for (int i = 0; i < getComponents().length; i++)
                getComponent(i).setEnabled(show[i]);
        }

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        repaint();
    }

    void addNewPreferences() {
        Preferences prefs;
        String newPreferencesName = input(null, NLS("Add_preference"), NLS("Name"));
        if (newPreferencesName != null) {
            if (newPreferencesName.trim().isEmpty()) {
                warning(null, NLS("Name_empty"));
                addNewPreferences();
            } else if (_preferencesRepository.fileExists(newPreferencesName)) {
                warning(null, NLS("Name_already_used"));
                addNewPreferences();
            } else if ((prefs = openDialog(Preferences.from(newPreferencesName))) != null) {
                if (getRowCount() == 0)
                    _preferencesRepository.load(newPreferencesName);
                addNewRow(prefs.getName());
            }
        }
    }

    void editPreference() {
        final String name = getSelectedProfileName();
        if (openDialog(_preferencesRepository.read(name)) != null)
            _preferencesRepository.loadIfCurrentPreferenceName(name);
    }

    void copyPreference() {
        addNewRow(_preferencesRepository.copyPreferenceFrom(getSelectedProfileName()));
    }

    void removePreference() {
        final int selectedRow = getSelectedRow();
        if (selectedRow == -1)
            return;
        _preferencesRepository.delete(getSelectedProfileName());
        getTableModel().removeRow(selectedRow);
        selectRow(selectedRow + 1 > getRowCount() ? getRowCount() : selectedRow + 1);
    }

    private void addNewRow(String value) {
        getTableModel().addNewRow(value);
        selectRow(getRowCount());
    }

    private void selectRow(int row) {
        if (row > 0) {
            getSelectionModel().setSelectionInterval(row - 1, row - 1);
        }
    }

    private Preferences openDialog(final Preferences prefs) {
        final PreferencesDialog dialog = new PreferencesDialog(prefs, prefs.getName());
        dialog.create();
        dialog.setVisible(true);
        if (dialog.getButtonClickedId() == PreferencesDialog.SAVE_ID) {
            _preferencesRepository.save(prefs);
            return prefs;
        }
        return null;
    }

    private String getSelectedProfileName() {
        return getTableModel().getNameAt(getSelectedRow());
    }

    public boolean noSelectedPreferences() {
        return getTableModel().getRunningPreferencesName().equals("");
    }

    public boolean noPreferences() {
        return getTableModel().getRowCount() == 0;
    }

    public String getRunningPreferencesName() {
        return getTableModel().getRunningPreferencesName();
    }

    private class PreferencesTableModel extends DefaultTableModel {

        private ButtonGroup _groupRadios = new ButtonGroup();

        private PreferencesTableModel(List<String> preferences, String selectedPreferences) {
            addColumn("#");
            addColumn(NLS("Preferences"));

            for (String name : preferences) {
                JRadioButton button = new JRadioButton();
                _groupRadios.add(button);
                addRow(new Object[]{button, name});
                if (name.equals(selectedPreferences))
                    button.setSelected(true);
            }
        }

        String getRunningPreferencesName() {
            for (int i = 0; i < getRowCount(); i++) {
                if (((JRadioButton) getValueAt(i, 0)).isSelected())
                    return (String) getValueAt(i, 1);
            }
            return "";
        }

        void addNewRow(String value) {
            final JRadioButton radioButton = new JRadioButton();
            _groupRadios.add(radioButton);
            if (getRowCount() == 0) radioButton.setSelected(true);
            getTableModel().addRow(new Object[]{radioButton, value});
        }

        String getNameAt(int row) {
            return (String) getValueAt(row, 1);
        }
    }

    private class RadioButtonEditor extends DefaultCellEditor implements ItemListener {

        private JRadioButton button;

        RadioButtonEditor() {
            super(new JCheckBox());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value == null) return null;
            button = (JRadioButton) value;
            button.addItemListener(this);
            return (Component) value;
        }

        public Object getCellEditorValue() {
            button.removeItemListener(this);
            return button;
        }

        public void itemStateChanged(ItemEvent e) {
            super.fireEditingStopped();
            final List<Boolean> columns = _preferencesRepository.loadColumns(getTableModel().getRunningPreferencesName());
            _monitorTable.showColumns(columns);
        }
    }

}
