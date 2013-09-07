package net.sourceforge.glsof.common.utils;

import javax.swing.*;

public class GCheckBox {

    private JCheckBox checkBox;

    public GCheckBox(String text) {
        checkBox = new JCheckBox(text);
    }

    public GCheckBox(String text, boolean status) {
        checkBox = new JCheckBox(text, status);
    }

    public JCheckBox get() {
        return checkBox;
    }

    public GCheckBox tooltip(String text) {
        checkBox.setToolTipText(text);
        return this;
    }

    public static JCheckBox createCheckBox(String text, String tip) {
        return new GCheckBox(text).tooltip(tip).get();
    }

}
