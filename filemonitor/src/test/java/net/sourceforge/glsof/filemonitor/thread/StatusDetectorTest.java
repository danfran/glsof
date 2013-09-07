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
package net.sourceforge.glsof.filemonitor.thread;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StatusDetectorTest {

    private StatusDetector _statusDetector;

    @Before
    public void setUp() {
        _statusDetector = new StatusDetector();
        _statusDetector.add(new String[]{"a", "b", "c"});
        _statusDetector.add(new String[]{"d", "e", "f"});
        _statusDetector.add(new String[]{"g", "h", "i"});
        _statusDetector.swapCaches();
    }

    @Test
    public void seekOpen_2C_0O() {
        assertFalse(_statusDetector.isOpenRow(new String[]{"a", "b", "c"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"d", "e", "f"}));
        assertEquals(1, _statusDetector.getOldCache().size());
    }

    @Test
    public void seekOpen_2C_1O() {
        assertFalse(_statusDetector.isOpenRow(new String[]{"a", "b", "c"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"d", "e", "f"}));
        assertTrue(_statusDetector.isOpenRow(new String[]{"x", "h", "i"}));
        assertEquals(1, _statusDetector.getOldCache().size());
    }

    @Test
    public void seekOpen_3C_0O() {
        assertFalse(_statusDetector.isOpenRow(new String[]{"a", "b", "c"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"d", "e", "f"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"g", "h", "i"}));
        assertEquals(0, _statusDetector.getOldCache().size());
    }

    @Test
    public void seekOpen_3C_1O() {
        assertFalse(_statusDetector.isOpenRow(new String[]{"a", "b", "c"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"d", "e", "f"}));
        assertFalse(_statusDetector.isOpenRow(new String[]{"g", "h", "i"}));
        assertTrue(_statusDetector.isOpenRow(new String[]{"x", "h", "i"}));
        assertEquals(0, _statusDetector.getOldCache().size());
    }

}
