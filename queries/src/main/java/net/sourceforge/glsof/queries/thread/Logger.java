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
package net.sourceforge.glsof.queries.thread;

import net.sourceforge.glsof.common.utils.GBC;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public class Logger {

    public static final String START_COLOR = "start";
    public static final String STOP_COLOR = "stop";
    public static final String ERR_COLOR = "err";

    private static final String CLEAR_BUTTON_IMAGE = "/icons/q-clear.png";

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    private Map<String, Color> _colors;

    private StyledDocument _styledDocument;

    public void create(JSplitPane parent, ArrayList<Integer> start, ArrayList<Integer> stop, ArrayList<Integer> err) {
        _colors = new HashMap<String, Color>();
        JPanel main = new JPanel();
        parent.setBottomComponent(main);
        main.add(createLogger(), createConstraints(0, 0, GridBagConstraints.BOTH));
//        main.add(createMenu(start, stop, err), createConstraints(1, 0, GridBagConstraints.HORIZONTAL));
    }

    private GridBagConstraints createConstraints(final int x, final int y, final int fill) {
        return new GBC().gridx(x).gridy(y).fill(fill).insets(new Insets(5, 5, 5, 5)).get();
    }

    private JScrollPane createLogger() {
        _styledDocument = new DefaultStyledDocument();
        JTextPane textPane = new JTextPane(_styledDocument);
        textPane.setEditable(false);
        return new JScrollPane(textPane);
    }

    private void createMenu(ArrayList<Integer> start, ArrayList<Integer> stop, ArrayList<Integer> err) {
        _colors.put(START_COLOR, new Color(start.get(0), start.get(1), start.get(2)));
        createColorLabel(createConstraints(1, 0, GridBagConstraints.VERTICAL), NLS("Start"), START_COLOR);
        _colors.put(STOP_COLOR, new Color(stop.get(0), stop.get(1), stop.get(2)));
        createColorLabel(createConstraints(1, 1, GridBagConstraints.VERTICAL), NLS("Stop"), STOP_COLOR);
        _colors.put(ERR_COLOR, new Color(err.get(0), err.get(1), err.get(2)));
        createColorLabel(createConstraints(1, 2, GridBagConstraints.VERTICAL), NLS("Error"), ERR_COLOR);

//        final JButton clear = new JButton(NLS("Clear"),new ImageIcon(getClass().getResource(CLEAR_BUTTON_IMAGE)));
//        clear.addMouseListener(new MouseAdapter() {
//            public void mouseUp(MouseEvent event) {
//                try {
//                    _styledDocument.remove(0, _styledDocument.getLength()-1);
//                } catch (BadLocationException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    private void createColorLabel(final GridBagConstraints composite, final String text, final String logName) {
//        final JButton button = new JButton(text);
//        button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_BEGINNING));
//        final JButton label = new JButton("                  ");
//        label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
//        label.setBackground(_colors.get(logName));
//        button.addListener(SWT.Selection, new Listener() {
//            public void handleEvent(Event event) {
//                ColorDialog dlg = new ColorDialog(composite.getShell());
//                dlg.setRGB(label.getBackground().getRGB());
//                dlg.setText(NLS("Choose_a_Color"));
//                RGB rgb = dlg.open();
//                if (rgb != null) {
//                    Color newColor = new Color(composite.getDisplay(), rgb);
//                    for (int i = 0; i < _styledDocument.getLineCount(); i++) {
//                        if (_styledDocument.getLineBackground(i) == _colors.get(logName)) {
//                            _styledDocument.setLineBackground(i, 1, null);
//                            _colors.get(logName).dispose();
//                            _styledDocument.setLineBackground(i, 1, newColor);
//                        }
//                    }
//                    _styledDocument.redraw();
//                    _colors.put(logName, newColor);
//                    label.setBackground(newColor);
//                }
//            }
//        });
    }

    public void logMessage(final String text, final String color) {
        if (_styledDocument == null) return;
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
//        _styledDocument.append("[" + sdf.format(cal.getTime()) + "] [" + color.toUpperCase() + "] " + text + "\n");
//        _styledDocument.setLineBackground(_styledDocument.getLineCount() - 2, 1, _colors.get(color));
//        ScrollBar vb = _styledDocument.getVerticalBar();
//        vb.setSelection(vb.getMaximum());
//        Event e = new Event();
//        e.widget = vb;
//        vb.notifyListeners(SWT.Selection, e);
    }

    public Map<String, Color> getColors() {
        return _colors;
    }
}
