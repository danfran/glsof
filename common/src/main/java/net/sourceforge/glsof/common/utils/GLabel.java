package net.sourceforge.glsof.common.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class GLabel {

    private JLabel label;

    public GLabel(String text) {
        label = new JLabel(text);
    }

    public GLabel(Icon image) {
        label = new JLabel(image);
    }

    public JLabel get() {
        return label;
    }

    public GLabel name(String name) {
        label.setName(name);
        return this;
    }

    public GLabel tooltip(String text) {
        label.setToolTipText(text);
        return this;
    }

    public GLabel foreground(Color color) {
        label.setForeground(color);
        return this;
    }

    public GLabel alignmentX(float x) {
        label.setAlignmentX(x);
        return this;
    }

    public GLabel mouseListener(MouseListener listener) {
        label.addMouseListener(listener);
        return this;
    }

}
