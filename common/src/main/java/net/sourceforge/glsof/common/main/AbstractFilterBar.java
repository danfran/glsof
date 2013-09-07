/* This file is part of Glsof.

   Glsof is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   Glsof is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with Glsof.  If not, see <http://www.gnu.org/licenses/>. */
package net.sourceforge.glsof.common.main;

import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public abstract class AbstractFilterBar extends JPanel {

    private static final String FILTER_BUTTON = "/icons/filter.png";

    protected JTextField _comboFilter;
    protected JComboBox _comboColumnsList;
    protected JCheckBox _buttonCase;

    protected abstract void filter();

    protected abstract void createColumnsList();

    protected AbstractFilterBar() {
        setLayout(new GridBagLayout());
        JLabel label = new JLabel(NLS("Filter") + ":");
        add(label, new GBC().gridx(0).gridy(0).fill(GridBagConstraints.NONE).weightx(0.0).insets(new Insets(0, 5, 0, 5)).get());

        _comboFilter = new JTextField(25);
        _comboFilter.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_ENTER) filter();
            }
        });
        add(_comboFilter, new GBC().gridx(1).gridy(0).fill(GridBagConstraints.HORIZONTAL).weightx(1.0).insets(new Insets(0, 5, 0, 5)).get());

        createColumnsList();
        add(_comboColumnsList, new GBC().gridx(2).gridy(0).fill(GridBagConstraints.NONE).weightx(0.0).insets(new Insets(0, 5, 0, 5)).get());

        _buttonCase = new JCheckBox(NLS("Case_sensitive"));
        _buttonCase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                filter();
            }
        });
        add(_buttonCase, new GBC().gridx(3).gridy(0).fill(GridBagConstraints.NONE).weightx(0.0).insets(new Insets(0, 5, 0, 5)).get());

        JButton buttonFilter = new ActionButton(FILTER_BUTTON, "Filter") {
            @Override
            protected void execute() {
                filter();
            }
        }.getButton();
        add(buttonFilter, new GBC().gridx(4).gridy(0).fill(GridBagConstraints.NONE).weightx(0.0).insets(new Insets(0, 5, 0, 15)).get());
    }

}
