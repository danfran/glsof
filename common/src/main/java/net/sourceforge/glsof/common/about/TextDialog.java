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

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;


public class TextDialog extends AbstractButtonsDialog {

    private String _filePath;

    public TextDialog(String filePath) {
        super(NLS("License"));
        _filePath = filePath;
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        JTextArea area = new JTextArea(20, 50);
        area.setEditable(false);
        try {
            InputStream is = getClass().getResourceAsStream(_filePath);
            byte buf[] = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) area.append(new String(buf).substring(0, len));
            is.close();
        } catch (IOException e) {
        }
        JScrollPane pScroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        area.setCaretPosition(0);
        parent.add(pScroll);
    }

    @Override
    protected void createButtonsForButtonBar(JPanel composite) {
        createButton(composite, AbstractButtonsDialog.CLOSE_ID, NLS("Close"));
    }

    @Override
    protected void buttonPressed(int buttonId) {
        dispose();
    }

}
