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
package net.sourceforge.glsof.queries.main;

import net.sourceforge.glsof.common.testUtils.BaseRepositoryIntegrationTest;
import net.sourceforge.glsof.queries.repository.QueriesUIConfRepository;
import org.junit.Before;

public class QueriesUIConfRepositoryTest extends BaseRepositoryIntegrationTest {

    private static final Object[] INITIAL_VALUES = new Object[]{800, 600, 0, 0, 20, 80, 750, 250, 0, 255, 0, 255, 255, 0, 127, 127, 127, true, true};

    private QueriesUIConfRepository _repository;

    @Before
    public void setUp() {
        _repository = new QueriesUIConfRepository(getBaseDir());
        //checkConfStatus(_repository.read(), INITIAL_VALUES);
    }

/*    private void checkConfStatus(QueriesUIConf conf, Object... values) {
        assertEquals(values[0], conf.getWidth());
        assertEquals(values[1], conf.getHeight());
        assertEquals(values[2], conf.getPosX());
        assertEquals(values[3], conf.getPosY());
        assertEquals(values[4], conf.getQueriesTreeWidth());
        assertEquals(values[5], conf.getQueriesTablesWidth());
        assertEquals(values[6], conf.getQueriesHeight());
        assertEquals(values[7], conf.getLogHeight());
        assertEquals(values[8], conf.getLogStartColor().get(0).intValue());
        assertEquals(values[9], conf.getLogStartColor().get(1).intValue());
        assertEquals(values[10], conf.getLogStartColor().get(2).intValue());
        assertEquals(values[11], conf.getLogStopColor().get(0).intValue());
        assertEquals(values[12], conf.getLogStopColor().get(1).intValue());
        assertEquals(values[13], conf.getLogStopColor().get(2).intValue());
        assertEquals(values[14], conf.getLogErrColor().get(0).intValue());
        assertEquals(values[15], conf.getLogErrColor().get(1).intValue());
        assertEquals(values[16], conf.getLogErrColor().get(2).intValue());
        assertEquals(values[17], conf.isMenuItemLogs());
        assertEquals(values[18], conf.isMenuItemStatusBar());
    }

    @Test
    public void save() {
        Object[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, true, false};
        Map<String, Color> colors = new HashMap<String, Color>();
        Display display = new Shell().getDisplay();
        colors.put("start", new Color(display, (Integer)values[8], (Integer)values[9], (Integer)values[10]));
        colors.put("stop", new Color(display, (Integer)values[11], (Integer)values[12], (Integer)values[13]));
        colors.put("err", new Color(display, (Integer)values[14], (Integer)values[15], (Integer)values[16]));
        _repository.save(
                new Point((Integer)values[2], (Integer)values[3]),
                new Point((Integer)values[0], (Integer)values[1]),
                colors,
                new int[]{(Integer)values[4], (Integer)values[5]},
                new int[]{(Integer)values[6], (Integer)values[7]},
                (Boolean)values[17],
                (Boolean)values[18]
                );
        checkConfStatus(_repository.read(), values);
    }

    @After
    public void tearDown() {
        removeAllFiles();
    }
  */
}
