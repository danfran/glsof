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
package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.about.AbstractButtonsDialog;
import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.model.Location;
import net.sourceforge.glsof.common.model.OtherPreferences;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.utils.GBC;
import net.sourceforge.glsof.common.utils.GCheckBox;
import net.sourceforge.glsof.common.utils.MessageDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NORTHWEST;
import static java.util.Arrays.asList;
import static javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.common.preferences.LsofParameterType.FD;
import static net.sourceforge.glsof.common.preferences.LsofParameterType.getParameterType;
import static net.sourceforge.glsof.common.rmi.RmiUtils.initRmiService;

public class PreferencesDialog extends AbstractButtonsDialog {

    private static final int TYPE = 0, VALUE = 1, ACTION = 2, EDIT = 3, REMOVE = 4, OBJ_REF = 5;

    private Preferences _prefs;

    protected JTable _table;

    private JCheckBox _remote;
    private JComponent[] _address;
    private JComponent[] _port;
    private JButton _test;

    private ButtonGroup _idLogin;
    private ButtonGroup _sizeOffset;
    private JComponent[] _timeout;
    private JComponent[] _links;
    private JCheckBox _avoid;
    private JCheckBox _ipFormat;
    private JCheckBox _nfs;
    private JCheckBox _ports;
    private JCheckBox _sockets;
    private JCheckBox _and;

    public PreferencesDialog(Preferences prefs, String name) {
        super(new Dimension(640, 400), NLS("Preference") + " " + name);
        if (prefs.getLocation() == null) prefs.setLocation(new Location());
        _prefs = prefs;
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        final JPanel main = (JPanel) parent.getParent();

        final JPanel panel = new JPanel(new BorderLayout());
        main.add(panel, BorderLayout.CENTER);

        final JPanel left = new JPanel(new BorderLayout());
        left.add(createGeneralOptions(), BorderLayout.NORTH);
        panel.add(left, BorderLayout.WEST);

        final JPanel right = new JPanel(new BorderLayout());
        right.setBorder(new EmptyBorder(5, 5, 5, 5));
        right.add(createFilterTable(), BorderLayout.NORTH);
        right.add(createFilterButtons(), BorderLayout.CENTER);
        panel.add(right, BorderLayout.CENTER);
    }

    private JScrollPane createFilterTable() {

        final Vector<Vector<Object>> model = populateTableModel();
        final Vector<String> columnNames = new Vector<String>(asList("Type", "Value", "Action", "", "", ""));

        _table = new JTable(model, columnNames) {

            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                if (col == VALUE) {
                    final String value = (String) getValueAt(row, col);
                    if (value.length() > 0) ((JComponent) comp).setToolTipText(value);
                }
                return comp;
            }
        };
        _table.getTableHeader().setReorderingAllowed(false);
        final TableColumn editColumn = _table.getColumnModel().getColumn(EDIT);
        final TableColumn removeColumn = _table.getColumnModel().getColumn(REMOVE);
        editColumn.setCellRenderer(new ButtonRenderer());
        removeColumn.setCellRenderer(new ButtonRenderer());
        _table.removeColumn(_table.getColumnModel().getColumn(OBJ_REF));
        _table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        removeColumn.setCellEditor(new ButtonEditor(_table, new JCheckBox()) {
            protected void fireEditingStopped() {
                super.fireEditingStopped();
                ((DefaultTableModel) _table.getModel()).removeRow(_table.getSelectedRow());
            }
        });

        editColumn.setCellEditor(new ButtonEditor(_table, new JCheckBox()) {
            protected void fireEditingStopped() {
                super.fireEditingStopped();
                try {
                    final Filter currentFilter = (Filter) _table.getModel().getValueAt(_table.getSelectedRow(), OBJ_REF);
                    final LsofParameterType parameterType = getParameterType(currentFilter.getType());
                    AbstractCommandDialog dialog = parameterType.createDialog();
                    dialog.create();
                    dialog.copyValuesInTheMask(currentFilter);
                    dialog.setVisible(true);
                    if (dialog.getButtonClickedId() == SAVE_ID && dialog.inputIsCorrect()) {
                        final Object[] data = parameterType.toTableRow(dialog.getModelData());
                        final DefaultTableModel model = (DefaultTableModel) _table.getModel();
                        final int row = _table.getSelectedRow();
                        for (int i = 0; i < data.length; i++) {
                            model.setValueAt(data[i], row, i);
                        }
                        setSameValueForFDsAction(data);
                    }
                } catch (Exception ce) {}
            }
        });

        return new JScrollPane(_table, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private Vector<Vector<Object>> populateTableModel() {
        Vector<Vector<Object>> model = new Vector<Vector<Object>>();
        List<Filter> filters = _prefs.getFilters();
        for (int i = 0; i < filters.size(); i++) {
            final LsofParameterType parameterType = getParameterType(filters.get(i).getType());
            model.add(new Vector<Object>(asList(parameterType.toTableRow(filters.get(i)))));
        }
        return model;
    }

    private JPanel createFilterButtons() {
        final JPanel main = new JPanel();
        main.setLayout(new GridLayout(2,4));

        final GridBagConstraints gbc = new GBC().gridx(0).gridy(0).fill(HORIZONTAL).get();

        for (final LsofParameterType parameterType : LsofParameterType.values()) {
            final JButton button = parameterType.createButton();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AbstractCommandDialog dialog = parameterType.createDialog();
                        dialog.create();
                        dialog.setVisible(true);
                        if (dialog.getButtonClickedId() == SAVE_ID && dialog.inputIsCorrect()) {
                            final Object[] data = parameterType.toTableRow(dialog.getModelData());
                            ((DefaultTableModel) _table.getModel()).addRow(data);
                            setSameValueForFDsAction(data);
                        }
                    } catch (Exception ce) {}
                }
            });
            main.add(button, gbc);
            gbc.gridy++;
        }

        return main;
    }

    private void enableLocationComponents(boolean enabled) {
        _address[1].setEnabled(enabled);
        _port[1].setEnabled(enabled);
        _test.setEnabled(enabled);
    }

    private void setSameValueForFDsAction(Object[] data) {
        final Filter filter = (Filter) data[OBJ_REF];
        if (filter.getType().equals(FD.getId())) {
            changeFDsAction(filter.getValues().get(1));
        }
    }

    private void changeFDsAction(final String action) {
        final DefaultTableModel model = (DefaultTableModel) _table.getModel();
        for (int i = 0; i < model.getRowCount(); i++)
            if (model.getValueAt(i, TYPE).equals(FD.getLabel())) {
                model.setValueAt(FD.getInclusionLabel(action), i, ACTION);
                Filter filter = (Filter) model.getValueAt(i, OBJ_REF);
                filter.getValues().set(1, action);
            }
    }

    private JPanel createLocationArea() {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(NLS("Location")));

        final Location location = _prefs.getLocation();

        _remote = createCheckButton("Remote", location.isRemote(), panel, 0);

        _address = new JComponent[]{new JLabel(NLS("Address")), new JTextField(location.getAddress(), 30)};
        packTwoElements(panel, _address[0], _address[1], 1, new Insets(0, 15, 0, 0), new Insets(5, 5, 5, 5));

        _port = new JComponent[]{new JLabel(NLS("Port")), new JSpinner(new SpinnerNumberModel(location.getPort(), 1, 65535, 1))};
        packTwoElements(panel, _port[0], _port[1], 2, new Insets(0, 15, 0, 0), new Insets(5, 5, 5, 5));

        _test = new JButton(NLS("Test"));

        enableLocationComponents(location.isRemote());

        _remote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                enableLocationComponents(((JCheckBox) event.getSource()).isSelected());
            }
        });

        _test.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final Location location = new Location();
                location.setAddress(((JTextField) _address[1]).getText());
                location.setPort((Integer) ((JSpinner) _port[1]).getValue());
                try {
                    initRmiService(location);
                    MessageDialog.info(PreferencesDialog.this, location.getRmiAddress() + "\n\n" + NLS("Remote_connect_ok"));
                } catch (Exception ex) {
                    MessageDialog.warning(PreferencesDialog.this, location.getRmiAddress() + "\n\n" + NLS("Remote_connect_ko"));
                }
            }
        });

        panel.add(_test, new GBC().gridx(0).gridy(3).anchor(NORTHWEST).weighty(0).insets(new Insets(5, 20, 5, 5)).get());

        return panel;
    }

    private JPanel createGeneralOptions() {
        final JPanel main = new JPanel(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        main.add(panel, BorderLayout.NORTH);

        panel.add(createLocationArea(), new GBC().gridx(0).gridy(0).anchor(NORTHWEST).weighty(0).insets(new Insets(5, 5, 5, 5)).get());

        final OtherPreferences page = _prefs.getOtherPreferences();

        _and = createCheckButton("AND_all_settings", page.isAnd(), panel, 1);
        _avoid = createCheckButton("Avoid", page.isAvoid(), panel, 2);
        _ipFormat = createCheckButton("Show_addresses_in_IP-format", page.isIpFormat(), panel, 3);
        _nfs = createCheckButton("NFS_files", page.isNfs(), panel, 4);
        _ports = createCheckButton("Show_port-numbers", page.isPortNumbers(), panel, 5);
        _sockets = createCheckButton("UNIX_domain_socket_files", page.isDomainSocket(), panel, 6);

        _idLogin = createRadioButtons(new String[]{"ID_Number", "Login_Name"}, page.isIdNumber());
        Enumeration<AbstractButton> buttons = _idLogin.getElements();
        packTwoElements(panel, buttons.nextElement(), buttons.nextElement(), 7, new Insets(5, 5, 5, 5), new Insets(0, 0, 0, 0));

        _sizeOffset = createRadioButtons(new String[]{"File_Size", "File_Offset"}, page.isSize());
        buttons = _sizeOffset.getElements();
        packTwoElements(panel, buttons.nextElement(), buttons.nextElement(), 8, new Insets(5, 5, 5, 5), new Insets(0, 0, 0, 0));

        _links = createCheckButtonAndSpinner("Max_number_of_links_for_a_file", page.getLinksFileValue(), 0, page.isLinksFile());
        packTwoElements(panel, _links[0], _links[1], 9, new Insets(5, 5, 5, 5), new Insets(0, 0, 0, 0));

        _timeout = createCheckButtonAndSpinner("Timeout_s", page.getTimeoutValue(), 2, page.isTimeout());
        packTwoElements(panel, _timeout[0], _timeout[1], 10, new Insets(5, 5, 5, 5), new Insets(0, 0, 0, 0));

        return main;
    }

    private void packTwoElements(JPanel panel, Component c1, Component c2, int gridy, Insets insets, Insets inner) {
        JPanel sub = new JPanel(new GridBagLayout());
        sub.add(c1, new GBC().gridx(0).gridy(0).anchor(NORTHWEST).insets(inner).weighty(0).get());
        sub.add(c2, new GBC().gridx(1).gridy(0).anchor(NORTHWEST).insets(inner).weighty(0).get());
        panel.add(sub, new GBC().gridx(0).gridy(gridy).anchor(NORTHWEST).weighty(0).insets(insets).gridwidth(2).get());
    }

    private JCheckBox createCheckButton(final String text, final boolean state, JPanel panel, int y) {
        JCheckBox button = new GCheckBox(NLS(text), state).tooltip(NLS(text + "_tip")).get();
        panel.add(button, new GBC().gridx(0).gridy(y).anchor(NORTHWEST).insets(new Insets(5, 5, 5, 5)).get());
        return button;
    }

    private ButtonGroup createRadioButtons(final String[] texts, final boolean state) {
        final ButtonGroup group = new ButtonGroup();
        final JRadioButton button1 = new JRadioButton(NLS(texts[0]));
        final JRadioButton button2 = new JRadioButton(NLS(texts[1]));
        button1.setToolTipText(NLS(texts[0] + "_tip"));
        button2.setToolTipText(NLS(texts[1] + "_tip"));
        group.add(button1);
        group.add(button2);
        group.setSelected(state ? button1.getModel() : button2.getModel(), true);
        return group;
    }

    private JComponent[] createCheckButtonAndSpinner(final String text, final int value, final int min, final boolean state) {
        final JComponent[] controls = new JComponent[2];
        controls[0] = new GCheckBox(NLS(text), state).tooltip(NLS(text + "_tip")).get();
        controls[1] = new JSpinner(new SpinnerNumberModel(value, min, 10000000, 1));
        controls[1].setEnabled(state);
        ((JCheckBox) controls[0]).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controls[1].setEnabled(((JCheckBox) event.getSource()).isSelected());
            }
        });
        return controls;
    }

    @Override
    protected void buttonPressed(int i) {
        if (i == SAVE_ID) {

            final List<Filter> filters = _prefs.getFilters();
            filters.clear();
            final DefaultTableModel model = (DefaultTableModel) _table.getModel();
            for (int j = 0; j < model.getRowCount(); j++) {
                filters.add((Filter) model.getValueAt(j, OBJ_REF));
            }

            final OtherPreferences page = _prefs.getOtherPreferences();
            page.setIdNumber(_idLogin.getElements().nextElement().isSelected());
            page.setSize(_sizeOffset.getElements().nextElement().isSelected());
            page.setTimeout(((JCheckBox) _timeout[0]).isSelected());
            page.setTimeoutValue((Integer) ((JSpinner) _timeout[1]).getValue());
            page.setLinksFile(((JCheckBox) _links[0]).isSelected());
            page.setLinksFileValue((Integer) ((JSpinner) _links[1]).getValue());
            page.setAvoid(_avoid.isSelected());
            page.setIpFormat(_ipFormat.isSelected());
            page.setNfs(_nfs.isSelected());
            page.setPortNumbers(_ports.isSelected());
            page.setDomainSocket(_sockets.isSelected());
            page.setAnd(_and.isSelected());

            final Location location = _prefs.getLocation();
            location.setRemote(_remote.isSelected());
            location.setAddress(((JTextField) _address[1]).getText());
            location.setPort((Integer) ((JSpinner) _port[1]).getValue());
        }
        dispose();
    }

}