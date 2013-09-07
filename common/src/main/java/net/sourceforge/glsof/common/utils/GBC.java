package net.sourceforge.glsof.common.utils;

import java.awt.*;

public class GBC {

    private GridBagConstraints gbc;

    public GBC() {
        gbc = new GridBagConstraints();
    }

    public GridBagConstraints get() {
        return gbc;
    }

    public GBC anchor(int anchor) {
        gbc.anchor = anchor;
        return this;
    }

    public GBC weightx(double weightx) {
        gbc.weightx = weightx;
        return this;
    }

    public GBC weighty(double weighty) {
        gbc.weighty = weighty;
        return this;
    }

    public GBC fill(int fill) {
        gbc.fill = fill;
        return this;
    }

    public GBC gridx(int gridx) {
        gbc.gridx = gridx;
        return this;
    }

    public GBC gridy(int gridy) {
        gbc.gridy = gridy;
        return this;
    }

    public GBC insets(Insets insets) {
        gbc.insets = insets;
        return this;
    }

    public GBC gridwidth(int gridwidth) {
        gbc.gridwidth = gridwidth;
        return this;
    }

}
