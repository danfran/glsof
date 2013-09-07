package net.sourceforge.glsof.common.preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton _button;
    private String _label;
    protected JTable _table;

    public ButtonEditor(JTable table, JCheckBox checkBox) {
        super(checkBox);
        _table = table;
        _button = new JButton();
        _button.setOpaque(true);
        _button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            _button.setForeground(table.getSelectionForeground());
            _button.setBackground(table.getSelectionBackground());
        } else {
            _button.setForeground(table.getForeground());
            _button.setBackground(table.getBackground());
        }
        _label = (value == null) ? "" : value.toString();
        _button.setText(_label);
        return _button;
    }

    public Object getCellEditorValue() {
        return new String(_label);
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}