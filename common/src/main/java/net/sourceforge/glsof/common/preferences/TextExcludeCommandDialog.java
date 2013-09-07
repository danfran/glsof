package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

class TextExcludeCommandDialog extends AbstractCommandDialog {

    protected JTextField _text = new JTextField(20);
    protected JCheckBox _exclude = new JCheckBox(NLS("Exclude"));

    public TextExcludeCommandDialog(LsofParameterType type) {
        super(type);
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        parent.add(createLabel(), new GBC().gridx(0).gridy(0).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());
        parent.add(_text, new GBC().gridx(1).gridy(0).weightx(1.0).fill(GridBagConstraints.HORIZONTAL).insets(new Insets(5, 5, 5, 5)).gridwidth(3).get());
        parent.add(_exclude, new GBC().gridx(4).gridy(0).anchor(GridBagConstraints.LINE_START).insets(new Insets(5, 5, 5, 5)).get());
    }

    @Override
    protected boolean inputIsCorrect() {
        return inputIsCorrect(_text.getText(), NLS("You_cannot_entry_an_empty_value"));
    }

    @Override
    protected List<String> getModelFilterValuesFromTheMask() {
        return Arrays.asList(_text.getText().trim(), String.valueOf(_exclude.isSelected()));
    }

    @Override
    public void copyValuesInTheMask(Filter filter) {
        final List<String> values = filter.getValues();
        _text.setText(values.get(0).trim());
        _exclude.setSelected(Boolean.valueOf(values.get(1)));
    }

}