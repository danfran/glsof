package net.sourceforge.glsof.common.utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class GButton {

    private JButton button;

    public GButton(String text) {
        button = new JButton(text);
    }

    public GButton(Icon image) {
        button = new JButton(image);
    }

    public JButton get() {
        return button;
    }

    public GButton tooltip(String text) {
        button.setToolTipText(text);
        return this;
    }

    public GButton focusPainted(boolean focus) {
        button.setFocusPainted(focus);
        return this;
    }

    public GButton opaque(boolean opaque) {
        button.setOpaque(opaque);
        return this;
    }

    public GButton contentAreaFilled(boolean filled) {
        button.setContentAreaFilled(filled);
        return this;
    }

    public static JButton createTipButton (String iconPath, String toolTip) {
        final JButton b = new GButton (new ImageIcon(GButton.class.getResource(iconPath)))
                .focusPainted(false).opaque(false).contentAreaFilled(false).tooltip(NLS(toolTip)).get();
        final Border raisedBevelBorder = BorderFactory.createRaisedBevelBorder();
        final Insets insets = raisedBevelBorder.getBorderInsets(b);
        final EmptyBorder emptyBorder = new EmptyBorder(insets);
        b.setBorder(emptyBorder);
        b.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ButtonModel model = (ButtonModel) e.getSource();
                b.setBorder(model.isRollover() ? raisedBevelBorder : emptyBorder);
            }
        });
        return b;
    }

}
