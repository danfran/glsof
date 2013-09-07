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

import net.sourceforge.glsof.common.utils.GLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class AboutDialog extends AbstractButtonsDialog {

    private static final String LICENSE_LABEL = NLS("License");

    private static final String LICENSE_FILE_PATH = "/COPYING";

    private String _imageFilePath;

    private String _description;

    private String _version;

    private String _websiteUrl;

    private String _documentationUrl;

    public AboutDialog(final String filePath, final String description, final String version, String websiteUrl, String documentationUrl) {
        super(NLS("About"));
        _imageFilePath = filePath;
        _description = description;
        _version = version;
        _websiteUrl = websiteUrl;
        _documentationUrl = documentationUrl;
    }

    @Override
    protected void createDialogArea(JPanel parent) {
        parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        parent.setName("about-content");
        ImageIcon image = new ImageIcon(getClass().getResource(_imageFilePath));
        parent.add(new GLabel(image).alignmentX(Component.CENTER_ALIGNMENT).get());

        createLabel(parent, "description", _description);
        createLabel(parent, "version", NLS("Version") + " " + _version);
        createLabel(parent, "copyright", NLS("Copyright"));
        createLabel(parent, "email", "Email: dfrancesconi12@gmail.com");
        createLabelLink(parent, "website-link", NLS("Website"), _websiteUrl);
        createLabelLink(parent, "documentation-link", NLS("Online_Documentation"), _documentationUrl);
    }

    private void createLabel(JPanel parent, String name, String text) {
        parent.add(new GLabel(text).name(name).alignmentX(Component.CENTER_ALIGNMENT).get());
    }

    private void createLabelLink(JPanel parent, String name, String text, String url) {
        parent.add(new GLabel("<html><div align=center width=368px><u>" + text + "</u></div></html>").name(name)
                .foreground(new Color(0x0000aa)).alignmentX(Component.CENTER_ALIGNMENT).mouseListener(new LinkAction(url)).get());
    }

    @Override
    protected void createButtonsForButtonBar(JPanel parent) {
        createButton(parent, AbstractButtonsDialog.CLIENT_ID, LICENSE_LABEL);
        createButton(parent, AbstractButtonsDialog.CLOSE_ID, NLS("Close"));
    }

    @Override
    protected void buttonPressed(int buttonId) {
        if (buttonId == AbstractButtonsDialog.CLIENT_ID) {
            TextDialog dialog = new TextDialog(LICENSE_FILE_PATH);
            dialog.create();
            dialog.show();
        } else dispose();
    }

    private class LinkAction extends MouseAdapter {

        private String _url;

        public LinkAction(String url) {
            _url = url;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() <= 0 && !Desktop.isDesktopSupported()) return;
            try {
                Desktop.getDesktop().browse(new URI(_url));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}