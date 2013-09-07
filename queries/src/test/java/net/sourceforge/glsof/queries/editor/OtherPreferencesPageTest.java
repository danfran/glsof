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
package net.sourceforge.glsof.queries.editor;

import net.sourceforge.glsof.common.model.OtherPreferences;

public class OtherPreferencesPageTest {
//    private OtherPreferencesUIPage _pageGUI;
    private OtherPreferences _page;

/*    @Before
    public void setUp() {
        _page = createOtherPage();
        _pageGUI = new OtherPreferencesUIPage(_page);
        _pageGUI.createContents(new Shell());
        checkInit();
    }

    void checkInit() {
        Button[] ids = (Button[])ReflectionUtils.getField(_pageGUI, "_idLogin");
        Button[] size = (Button[])ReflectionUtils.getField(_pageGUI, "_sizeOffset");
        Control[] timeout = (Control[])ReflectionUtils.getField(_pageGUI, "_timeout");
        Control[] links = (Control[])ReflectionUtils.getField(_pageGUI, "_links");
        Button avoid = (Button)ReflectionUtils.getField(_pageGUI, "_avoid");
        Button ip = (Button)ReflectionUtils.getField(_pageGUI, "_ipFormat");
        Button nfs = (Button)ReflectionUtils.getField(_pageGUI, "_nfs");
        Button ports = (Button)ReflectionUtils.getField(_pageGUI, "_ports");
        Button sockets = (Button)ReflectionUtils.getField(_pageGUI, "_sockets");
        Button and = (Button)ReflectionUtils.getField(_pageGUI, "_and");

        assertTrue(ids[0].getSelection());
        assertTrue(size[0].getSelection());
        assertTrue(((Button)timeout[0]).getSelection());
        assertEquals(100, ((Spinner)timeout[1]).getSelection());
        assertTrue(((Button)links[0]).getSelection());
        assertEquals(5, ((Spinner)links[1]).getSelection());
        assertTrue(avoid.getSelection());
        assertTrue(ip.getSelection());
        assertTrue(nfs.getSelection());
        assertTrue(ports.getSelection());
        assertTrue(sockets.getSelection());
        assertTrue(and.getSelection());
    }

    @Test
    public void okPressed() {
        resetPageData();
        _pageGUI.performOk();
        assertTrue(_page.isIdNumber());
        assertTrue(_page.isSize());
        assertTrue(_page.isTimeout());
        assertEquals(100, _page.getTimeoutValue());
        assertTrue(_page.isLinksFile());
        assertEquals(5, _page.getLinksFileValue());
        assertTrue(_page.isAvoid());
        assertTrue(_page.isIpFormat());
        assertTrue(_page.isNfs());
        assertTrue(_page.isPortNumbers());
        assertTrue(_page.isDomainSocket());
        assertTrue(_page.isAnd());
    }

    @Test
    public void okCancel() {
        resetPageData();
        assertFalse(_page.isIdNumber());
        assertFalse(_page.isSize());
        assertFalse(_page.isTimeout());
        assertEquals(1, _page.getTimeoutValue());
        assertFalse(_page.isLinksFile());
        assertEquals(1, _page.getLinksFileValue());
        assertFalse(_page.isAvoid());
        assertFalse(_page.isIpFormat());
        assertFalse(_page.isNfs());
        assertFalse(_page.isPortNumbers());
        assertFalse(_page.isDomainSocket());
        assertFalse(_page.isAnd());
    }

    private void resetPageData() {
        _page.setIdNumber(false);
        _page.setSize(false);
        _page.setTimeout(false);
        _page.setTimeoutValue(1);
        _page.setLinksFile(false);
        _page.setLinksFileValue(1);
        _page.setAvoid(false);
        _page.setIpFormat(false);
        _page.setNfs(false);
        _page.setPortNumbers(false);
        _page.setDomainSocket(false);
        _page.setAnd(false);
    }
  */
}
