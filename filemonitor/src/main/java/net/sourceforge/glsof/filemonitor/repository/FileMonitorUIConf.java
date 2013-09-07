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
package net.sourceforge.glsof.filemonitor.repository;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.*;

@XmlRootElement
public class FileMonitorUIConf {

    private int _posX;

    private int _posY;

    private int _width = 1000;

    private int _height = 640;

    private int _preferencesSize = 800;

    private boolean _showPreferences = true;

    private String _selectedPreference;

    private boolean _autoscroll = true;

    public int getPosX() {
        return _posX;
    }

    public void setPosX(int posX) {
        _posX = posX;
    }

    public int getPosY() {
        return _posY;
    }

    public void setPosY(int posY) {
        _posY = posY;
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    public int getPreferencesSize() {
        return _preferencesSize;
    }

    public void setPreferencesSize(int preferencesSize) {
        _preferencesSize = preferencesSize;
    }

    public boolean isShowPreferences() {
        return _showPreferences;
    }

    public void setShowPreferences(boolean showPreferences) {
        _showPreferences = showPreferences;
    }

    public String getSelectedPreference() {
        return _selectedPreference;
    }

    public void setSelectedPreference(String selectedPreference) {
        _selectedPreference = selectedPreference;
    }

    public boolean isAutoscroll() {
        return _autoscroll;
    }

    public void setAutoscroll(boolean autoscroll) {
        _autoscroll = autoscroll;
    }

    public int getDividerLocation() {
        return isShowPreferences() ? getPreferencesSize() : 0;
    }

    @XmlTransient
    public Rectangle getWindowBounds() {
        return new Rectangle(getPosX(), getPosY(), getWidth(), getHeight());
    }

    public void setWindowBounds(final Rectangle bounds) {
        setWidth(bounds.width);
        setHeight(bounds.height);
        setPosX(bounds.x);
        setPosY(bounds.y);
    }

}