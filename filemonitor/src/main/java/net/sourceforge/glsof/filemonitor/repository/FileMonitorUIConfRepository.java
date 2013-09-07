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

import net.sourceforge.glsof.common.io.XMLRepository;

import java.io.File;

public class FileMonitorUIConfRepository extends XMLRepository<FileMonitorUIConf> {

    private static final String FILEMONITOR_UI_CONF_FILE_NAME = "filemonitor_ui_conf";

    private static FileMonitorUIConf _uiConf;

    public FileMonitorUIConfRepository(File baseDir) {
        super(baseDir);
        if (!new File(baseDir, FILEMONITOR_UI_CONF_FILE_NAME + ".xml").exists())
            save(new FileMonitorUIConf());
    }

    public void save(FileMonitorUIConf conf) {
        write(conf, FILEMONITOR_UI_CONF_FILE_NAME);
    }

    public FileMonitorUIConf read() {
        _uiConf = read(FileMonitorUIConf.class, FILEMONITOR_UI_CONF_FILE_NAME);
        return _uiConf;
    }

    public static FileMonitorUIConf getUiConf() {
        return _uiConf;
    }
}
