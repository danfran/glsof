package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.util.Arrays.asList;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;

class PathCommandDialog extends AbstractCommandDialog {

    protected JTextField _directory;

    public PathCommandDialog(LsofParameterType type) {
        super(type);
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        parent.add(createLabel(), new GBC().gridx(0).gridy(0).anchor(GridBagConstraints.WEST).insets(new Insets(5, 5, 5, 5)).get());
        _directory = new JTextField(20);
        parent.add(_directory, new GBC().gridx(1).gridy(0).weightx(1.0).anchor(GridBagConstraints.WEST).fill(GridBagConstraints.HORIZONTAL).gridwidth(3).insets(new Insets(5, 5, 5, 5)).get());

        final JButton button = new JButton(NLS("Browse"));
        parent.add(button, new GBC().gridx(4).gridy(0).anchor(GridBagConstraints.LINE_START).insets(new Insets(5, 5, 5, 5)).get());
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(DIRECTORIES_ONLY);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button && fc.showOpenDialog(PathCommandDialog.this) == APPROVE_OPTION)
                    _directory.setText(fc.getSelectedFile().getAbsolutePath());
            }
        });
    }

    @Override
    protected boolean inputIsCorrect() {
        return inputIsCorrect(_directory.getText(), NLS("You_cannot_entry_an_empty_directory_value"));
    }

    @Override
    public void copyValuesInTheMask(Filter filter) {
        _directory.setText(filter.getValues().get(0));
    }

    @Override
    protected List<String> getModelFilterValuesFromTheMask() {
        return asList(_directory.getText().trim());
    }

}