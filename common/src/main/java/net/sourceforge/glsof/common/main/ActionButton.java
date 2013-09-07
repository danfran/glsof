package net.sourceforge.glsof.common.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static net.sourceforge.glsof.common.main.Observer.NOTIFY;
import static net.sourceforge.glsof.common.utils.GButton.createTipButton;

public abstract class ActionButton {

    private List<Observer> _observers;

    private final JButton _button;

    abstract protected void execute();

    public ActionButton(List<Observer> observers, String iconPath, String toolTip) {
        this(iconPath, toolTip);
        _observers = observers;
    }

    public ActionButton(String iconPath, String toolTip) {
        _button = createTipButton(iconPath, toolTip);
        _button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                execute();
            }
        });
    }

    public JButton getButton() {
        return _button;
    }

    protected ImageIcon getIcon(String icon) {
        return new ImageIcon(getClass().getResource(icon));
    }

    protected void notify(NOTIFY notify, Object... params) {
        for (Observer observer : _observers) observer.update(notify, params);
    }

}
