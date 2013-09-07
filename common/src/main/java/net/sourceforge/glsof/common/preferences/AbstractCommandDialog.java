package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.about.AbstractButtonsDialog;
import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.MessageDialog;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

abstract class AbstractCommandDialog extends AbstractButtonsDialog {

    private LsofParameterType _type;

    public abstract void copyValuesInTheMask(Filter filter);

    protected abstract List<String> getModelFilterValuesFromTheMask();

    protected abstract boolean inputIsCorrect();

    public AbstractCommandDialog(LsofParameterType type) {
        super(new Dimension(320, 200), type.getLabel());
        _type = type;
    }

    protected boolean inputIsCorrect(final String text, final String errMsg) {
        if (text == null || text.length() == 0) {
            MessageDialog.warning(this, errMsg);
            return false;
        }
        return true;
    }

    protected JLabel createLabel() {
        return new JLabel(_type.getLabel());
    }

    protected String getTip (String label) {
        return NLS(_type + label + "_tip");
    }

    public Filter getModelData() {
        return new Filter(_type.getId(), getModelFilterValuesFromTheMask());
    }

}