package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.GBC;
import net.sourceforge.glsof.common.utils.GLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

class NetworkCommandDialog extends AbstractCommandDialog {

    private JTextField _address;
    private JTextField _port;
    private JComboBox _protocol;
    private JComboBox _ipv;

    public NetworkCommandDialog(LsofParameterType type) {
        super(type);
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        parent.setLayout(new GridBagLayout());

        parent.add(new GLabel("Address").tooltip(getTip("_address")).get(), new GBC().gridx(0).gridy(0).insets(new Insets(5, 5, 5, 5)).anchor(GridBagConstraints.WEST).get());
        _address = new JTextField(17);
        parent.add(_address, new GBC().gridx(1).gridy(0).weightx(1.0).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).fill(GridBagConstraints.HORIZONTAL).gridwidth(2).insets(new Insets(5, 5, 5, 5)).get());

        parent.add(new GLabel("Port").tooltip(getTip("_port")).get(), new GBC().gridx(0).gridy(1).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());
        _port = new JTextField(12);
        parent.add(_port, new GBC().gridx(1).gridy(1).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());

        _protocol = createComboEntry(parent, "Protocol", getTip("_protocol"), new String[]{" ", "TCP", "UDP"}, 0, 2);
        _ipv = createComboEntry(parent, "IPV", getTip("_ipv"), new String[]{" ", "4", "6"}, 0, 3);
    }

    private JComboBox createComboEntry(JPanel panel, String text, String tip, String[] items, int x, int y) {
        final JLabel label = new GLabel(NLS(text)).tooltip(tip).get();
        panel.add(label, new GBC().gridx(x).gridy(y).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());
        JComboBox combo = new JComboBox(items);
        panel.add(combo, new GBC().gridx(x + 1).gridy(y).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());
        return combo;
    }

    @Override
    protected boolean inputIsCorrect() {
        return true;
    }

    @Override
    public void copyValuesInTheMask(Filter filter) {
        final List<String> values = filter.getValues();
        _address.setText(values.get(0).trim());
        _port.setText(values.get(1).trim());
        _protocol.setSelectedIndex(values.get(2).equals(" ") ? 0 : (values.get(2).equals("TCP") ? 1 : 2));
        _ipv.setSelectedIndex(values.get(3).equals(" ") ? 0 : (values.get(3).equals("4") ? 1 : 2));
    }

    @Override
    protected List<String> getModelFilterValuesFromTheMask() {
        return Arrays.asList(
                _address.getText().trim().equals("") ? " " : _address.getText().trim(),
                _port.getText().trim().equals("") ? " " : _port.getText().trim(),
                _protocol.getSelectedItem().toString(),
                _ipv.getSelectedItem().toString());
    }

}