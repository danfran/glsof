package net.sourceforge.glsof.filemonitor;

import net.sourceforge.glsof.common.main.Observer;

import javax.swing.*;

import static net.sourceforge.glsof.filemonitor.repository.FileMonitorUIConfRepository.getUiConf;

public class MonitorPreferencesContainer extends JSplitPane implements Observer {

    private int _rightPanelWidth;

    MonitorPreferencesContainer(JPanel left, JPanel right) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        setBorder (null);
        setLeftComponent(left);
        setRightComponent(right);
    }

    void hidePreferences() {
        _rightPanelWidth = getDividerLocation();
        getRightComponent().setVisible(false);
    }

    void showPreferences() {
        getRightComponent().setVisible(true);
        setDividerLocation(_rightPanelWidth);
    }

    void toggle(int width) {
        final boolean show = !getRightComponent().isVisible();
        if (!show) getUiConf().setPreferencesSize(getDividerLocation());
        getRightComponent().setVisible(show);
        setDividerLocation(show ? getUiConf().getPreferencesSize() : width);
    }

    void pack() {
        setDividerLocation(getUiConf().getDividerLocation());
        _rightPanelWidth = getUiConf().getPreferencesSize();
        getRightComponent().setVisible(getUiConf().isShowPreferences());
    }

    int getLocationPosition() {
        return getRightComponent().isVisible() ? getDividerLocation() : _rightPanelWidth;
    }

    @Override
    public void update(NOTIFY notify, Object... params) {
        if (notify == NOTIFY.START) {
            hidePreferences();
        } else if (notify == NOTIFY.STOP) {
            showPreferences();
        } else if (notify == NOTIFY.TOGGLE) {
            toggle((Integer) params[0]);
        } else if (notify == NOTIFY.CLOSE) {
            final boolean visible = getRightComponent().isVisible();
            getUiConf().setShowPreferences(visible);
            if (visible) getUiConf().setPreferencesSize(getLocationPosition());
        }
    }
}
