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
package net.sourceforge.glsof.common.about;

import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public abstract class AbstractButtonsDialog extends JDialog {

    final public static int CLOSE_ID = 0;
    final public static int SAVE_ID = 1;
    final public static int CANCEL_ID = 2;
    final public static int CLIENT_ID = 1024;

    private Dimension _size;

    private String _caption;

    private int _buttonNextPlace = 0;

    private int _buttonClickedId;

    protected abstract void createDialogArea(JPanel parent);

    protected void buttonPressed(final int buttonId) {
        dispose();
    }

    protected AbstractButtonsDialog(final String caption) {
        this(null, caption);
    }

    protected AbstractButtonsDialog(final Dimension size, final String caption) {
        _size = size;
        _caption = caption;
        setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
    }

    public void create() {
        createContents();
        setTitle(_caption);
        if (_size != null) setSize(_size);
        pack();
        setLocationRelativeTo(null);
    }

    private void createContents() {
        JPanel main = new JPanel();
        BorderLayout mainLayout = new BorderLayout(5, 5);
        main.setLayout(mainLayout);
        getContentPane().add(main);

        JPanel content = new JPanel();
        main.add(content, BorderLayout.CENTER);

        JPanel buttonBar = new JPanel();
        main.add(buttonBar, BorderLayout.PAGE_END);

        createDialogArea(content);
        createButtonsForButtonBar(buttonBar);
    }

    protected void createButtonsForButtonBar(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        createButton(panel, CANCEL_ID, NLS("Cancel"));
        createButton(panel, SAVE_ID, NLS("Save"));
    }

    protected void createButton(final JPanel panel, final int id, final String label) {
        final JButton button = new JButton(label);
        panel.add(button, new GBC().gridx(_buttonNextPlace++).gridy(0).insets(new Insets(5, 5, 5, 5)).get());
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                _buttonClickedId = id;
                buttonPressed(id);
            }
        });
    }

    public int getButtonClickedId() {
        return _buttonClickedId;
    }
}
