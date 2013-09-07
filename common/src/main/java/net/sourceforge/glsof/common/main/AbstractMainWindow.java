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

import net.sourceforge.glsof.common.about.AboutDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.KeyStroke.getKeyStroke;
import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public abstract class AbstractMainWindow extends JFrame {

    public static final String ABOUT_LOGO = "/icons/glsof-banner-fm.png";
    protected static final String MENU_VIEW_REFRESH = "/icons/view-refresh.png";
    private static final String MENU_EXIT = "/icons/gtk-close.png";

    protected JMenuBar _menuBar;

    private Rectangle _bounds;

    private GraphicsDevice _device;

    private boolean _fullscreen = false;

    abstract protected void initRepositories();

    abstract protected void createContents(JPanel panel);

    abstract protected void close();

    abstract protected String version();

    abstract protected void createMenuItems();

    protected AbstractMainWindow(GraphicsDevice device, String caption) {
        super(device.getDefaultConfiguration());
        initRepositories();
        ToolTipManager.sharedInstance().setDismissDelay(10000000);
        _device = device;
        setTitle(caption);
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridBagLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        _menuBar = new JMenuBar();
        _menuBar.setOpaque(false);
        createMenuItems();
        createContents(panel);
        setLocation(_bounds.x, _bounds.y);
        setSize(_bounds.width, _bounds.height);
        setVisible(true);
    }

    protected JMenu createMenu(ImageIcon icon, String text) {
        JMenu menu = new JMenu();
        menu.setIcon(icon);
        menu.setToolTipText(text);
        _menuBar.add(menu);
        return menu;
    }

    protected JMenuItem createMenuItem(JMenu menu, String name, String text, KeyStroke acc, String imagePath) {
        JMenuItem item = new JMenuItem();
        item.setName(name);
        menu.add(item);
        item.setText(text);
        if (acc != null) item.setAccelerator(acc);
        if (imagePath != null && !imagePath.equals("")) {
            ImageIcon image = new ImageIcon(imagePath);
            item.setIcon(image);
        }
        return item;
    }

    protected void destroyWindow() {
        close();
        dispose();
    }

    protected File createSettingsDir(String path) {
        final String userHome = System.getProperty("user.home");
        final File dir = new File(userHome, path);
        if (!dir.exists() || dir.isFile()) {
            dir.mkdir();
        }
        return dir;
    }

    protected void createExitItem(JMenu menu) {
        createMenuItem(menu, "exit", NLS("Exit_CtrlQ"), getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK), MENU_EXIT).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                destroyWindow();
            }
        });
    }

    protected void createAboutItem(JMenu menu, final String logoPath, final String caption, final String version, final String websiteUrl, final String documentationUrl) {
        createMenuItem(menu, "about", NLS("About"), null, MENU_VIEW_REFRESH).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                AboutDialog dialog = new AboutDialog(logoPath, caption, version, websiteUrl, documentationUrl);
                dialog.create();
                dialog.setVisible(true);
            }
        });
    }

    protected void createFullscreenItem(JMenu menu) {
        createMenuItem(menu, "fullscreen", NLS("Fullscreen_F11"), getKeyStroke("F11"), "").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                _fullscreen = !_fullscreen;
                switchFullscreenMode();
            }
        });
    }

    private void switchFullscreenMode() {
        boolean isFullScreen = _device.isFullScreenSupported() && _fullscreen;
        dispose();
        setUndecorated(isFullScreen);
        setResizable(!isFullScreen);
        if (isFullScreen) { // Full-screen mode
            _bounds = getBounds();
            _device.setFullScreenWindow(this);
            validate();
        } else { // Windowed mode
            pack();
            setLocation(_bounds.x, _bounds.y);
            setSize(_bounds.width, _bounds.height);
            setVisible(true);
        }
    }

    protected void setWindowBounds(Rectangle bounds) {
        _bounds = bounds;
    }

}
