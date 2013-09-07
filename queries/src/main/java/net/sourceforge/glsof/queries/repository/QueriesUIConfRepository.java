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

import net.sourceforge.glsof.common.io.XMLRepository;

import java.awt.*;
import java.io.File;
import java.util.Map;

public class QueriesUIConfRepository extends XMLRepository<QueriesUIConf> {

    private static final String QUERIES_UI_CONF_FILE_NAME = "queries_ui_conf";

    public QueriesUIConfRepository(File baseDir) {
        super(baseDir);
        if (!new File(baseDir, QUERIES_UI_CONF_FILE_NAME + ".xml").exists()) createQueriesUIConf();
    }

    private void createQueriesUIConf() {
        QueriesUIConf conf = new QueriesUIConf();
        conf.setWidth(800);
        conf.setHeight(600);
        conf.setPosX(0);
        conf.setPosY(0);
        conf.setQueriesTreeWidth(150);
        conf.setQueriesHeight(450);
        conf.addLogStartColor(0);
        conf.addLogStartColor(255);
        conf.addLogStartColor(0);
        conf.addLogStopColor(255);
        conf.addLogStopColor(255);
        conf.addLogStopColor(0);
        conf.addLogErrColor(127);
        conf.addLogErrColor(127);
        conf.addLogErrColor(127);
        conf.setMenuItemLogs(true);
        conf.setMenuItemStatusBar(true);
        write(conf);
    }

    public void save(Rectangle bounds, Map<String, Color> colors, int queriesWidth, int queriesHeight, boolean menuItemLogs, boolean menuItemStatusBar) {
        QueriesUIConf conf = new QueriesUIConf();
        conf.setWidth(bounds.width);
        conf.setHeight(bounds.height);
        conf.setPosX(bounds.x);
        conf.setPosY(bounds.y);
        conf.setQueriesTreeWidth(queriesWidth);
        conf.setQueriesHeight(queriesHeight);
        conf.addLogStartColor(colors.get("start").getRed());
        conf.addLogStartColor(colors.get("start").getGreen());
        conf.addLogStartColor(colors.get("start").getBlue());
        conf.addLogStopColor(colors.get("stop").getRed());
        conf.addLogStopColor(colors.get("stop").getGreen());
        conf.addLogStopColor(colors.get("stop").getBlue());
        conf.addLogErrColor(colors.get("err").getRed());
        conf.addLogErrColor(colors.get("err").getGreen());
        conf.addLogErrColor(colors.get("err").getBlue());
        conf.setMenuItemLogs(menuItemLogs);
        conf.setMenuItemStatusBar(menuItemStatusBar);
        write(conf);
    }

    private void write(QueriesUIConf conf) {
        write(conf, QUERIES_UI_CONF_FILE_NAME);
    }

    public QueriesUIConf read() {
        return read(QueriesUIConf.class, QUERIES_UI_CONF_FILE_NAME);
    }
}
