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
package net.sourceforge.glsof.queries.repository;

import java.util.ArrayList;

public class QueriesUIConf {

    private int _posX;

    private int _posY;

    private int _width;

    private int _height;

    private int _queriesTreeWidth;

    private int _queriesHeight;

    private ArrayList<Integer> _logStartColor = new ArrayList<Integer>();

    private ArrayList<Integer> _logStopColor = new ArrayList<Integer>();

    private ArrayList<Integer> _logErrColor = new ArrayList<Integer>();

    private boolean menuItemLogs;

    private boolean menuItemStatusBar;

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

    public int getQueriesTreeWidth() {
        return _queriesTreeWidth;
    }

    public void setQueriesTreeWidth(int queriesTreeWidth) {
        _queriesTreeWidth = queriesTreeWidth;
    }

    public int getQueriesHeight() {
        return _queriesHeight;
    }

    public void setQueriesHeight(int queriesHeight) {
        _queriesHeight = queriesHeight;
    }

    public ArrayList<Integer> getLogStartColor() {
        return _logStartColor;
    }

    public void addLogStartColor(Integer i) {
        _logStartColor.add(i);
    }

    public ArrayList<Integer> getLogStopColor() {
        return _logStopColor;
    }

    public void addLogStopColor(Integer i) {
        _logStopColor.add(i);
    }

    public ArrayList<Integer> getLogErrColor() {
        return _logErrColor;
    }

    public void addLogErrColor(Integer i) {
        _logErrColor.add(i);
    }

    public boolean isMenuItemLogs() {
        return menuItemLogs;
    }

    public void setMenuItemLogs(boolean menuItemLogs) {
        this.menuItemLogs = menuItemLogs;
    }

    public boolean isMenuItemStatusBar() {
        return menuItemStatusBar;
    }

    public void setMenuItemStatusBar(boolean menuItemStatusBar) {
        this.menuItemStatusBar = menuItemStatusBar;
    }
}
