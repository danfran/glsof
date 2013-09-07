package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.common.utils.GCheckBox.createCheckBox;

class DirectoryCommandDialog extends PathCommandDialog {

    protected JCheckBox _fullDescentTreeButton;
    protected JCheckBox _linksButton;
    protected JCheckBox _mountPointsButton;

    public DirectoryCommandDialog(LsofParameterType type) {
        super(type);
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        parent.setLayout(new GridBagLayout());
        super.createDialogArea(parent);

        _fullDescentTreeButton = createCheckBox(NLS("Full_descent_tree"), getTip("_full_descent_tree"));
        parent.add(_fullDescentTreeButton, new GBC().gridx(1).gridy(1).anchor(GridBagConstraints.WEST).gridwidth(2).insets(new Insets(5, 5, 5, 5)).get());

        _mountPointsButton = createCheckBox(NLS("Follow_file_system_mount_points"), getTip("_mount_points"));
        parent.add(_mountPointsButton, new GBC().gridx(1).gridy(2).anchor(GridBagConstraints.WEST).gridwidth(2).insets(new Insets(5, 5, 5, 5)).get());

        _linksButton = createCheckBox(NLS("Follow_symbolic_links"), getTip("_symbolic_links"));
        parent.add(_linksButton, new GBC().gridx(1).gridy(3).anchor(GridBagConstraints.WEST).gridwidth(2).insets(new Insets(5, 5, 5, 5)).get());
    }

    @Override
    public void copyValuesInTheMask(Filter filter) {
        final List<String> values = filter.getValues();
        _directory.setText(values.get(0));
        _fullDescentTreeButton.setSelected(Boolean.valueOf(values.get(1)));
        _mountPointsButton.setSelected(Boolean.valueOf(values.get(2)));
        _linksButton.setSelected(Boolean.valueOf(values.get(3)));
    }

    @Override
    protected List<String> getModelFilterValuesFromTheMask() {
        return Arrays.asList(
                _directory.getText().trim(),
                String.valueOf(_fullDescentTreeButton.isSelected()),
                String.valueOf(_mountPointsButton.isSelected()),
                String.valueOf(_linksButton.isSelected()));
    }

}