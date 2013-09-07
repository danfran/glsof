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

import net.sourceforge.glsof.common.testUtils.BaseRepositoryIntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.Assert.assertEquals;

public class FileMonitorUIConfRepositoryIntegrationTest extends BaseRepositoryIntegrationTest {

    private static final int[] START_VALUES = new int[]{1000, 640, 0, 0};

    private FileMonitorUIConfRepository _repository;

    @Before
    public void setUp() {
        _repository = new FileMonitorUIConfRepository(getBaseDir());
        checkConfStatus(_repository.read(), START_VALUES, null, true, true, 800);
    }

    @Test
    public void save() {
        final FileMonitorUIConf conf = new FileMonitorUIConf();
        conf.setWindowBounds(new Rectangle(2, 3, 0, 1));
        conf.setSelectedPreference("testPrefs");
        conf.setShowPreferences(true);
        conf.setPreferencesSize(1000);
        _repository.save(conf);
        checkConfStatus(_repository.read(), new int[]{0, 1, 2, 3}, "testPrefs", true, true, 1000);
    }

    private void checkConfStatus(FileMonitorUIConf conf, int[] values, String prefName, boolean show, boolean autoscroll, int sizePrefs) {
        assertEquals(values[0], conf.getWidth());
        assertEquals(values[1], conf.getHeight());
        assertEquals(values[2], conf.getPosX());
        assertEquals(values[3], conf.getPosY());
        assertEquals(prefName, conf.getSelectedPreference());
        assertEquals(sizePrefs, conf.getPreferencesSize());
        assertEquals(autoscroll, conf.isAutoscroll());
        assertEquals(show, conf.isShowPreferences());
    }

    @After
    public void tearDown() {
        removeAllFiles();
    }

}