package net.sourceforge.glsof.common.utils;

import javax.swing.*;
import java.awt.*;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class MessageDialog {

    public static void info(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message, NLS("Ok"), JOptionPane.PLAIN_MESSAGE);
    }

    public static void warning(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message, NLS("Warning"), JOptionPane.WARNING_MESSAGE);
    }

    public static void error(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message, NLS("Error"), JOptionPane.ERROR_MESSAGE);
    }

    public static String input(Component parentComponent, String title, String label) {
        return JOptionPane.showInputDialog(parentComponent, label + ":", title, JOptionPane.PLAIN_MESSAGE);
    }

}
