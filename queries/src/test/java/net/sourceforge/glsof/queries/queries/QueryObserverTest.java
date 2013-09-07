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
package net.sourceforge.glsof.queries.queries;

import net.sourceforge.glsof.queries.thread.QueryNode;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class QueryObserverTest {

    private QueryNode _queryNode;

    private String[][] _mockLsofOutput = {
            {"fooProcess", "1st value", "2nd value"},
            {"fooProcess", "3rd value", "4th value"},
            {"barProcess", "5th value", "6th value"}
    };

    @Before
    public void setUp(){
        /*TreeItem treeItem = new TreeItem(new Tree(new Shell(), SWT.NONE), SWT.NONE);
        List<Boolean> columns = new ArrayList<Boolean>();
        for (int i=0; i<11; i++) columns.add(true);
        _queryNode = new QueryNode(treeItem, new Shell(), columns);
        ReflectionUtils.setField(_queryNode, "_tables", new HashMap<String, Table>());
        for (String [] processOutput : _mockLsofOutput) {
            _queryNode.setParsedOutput(processOutput);
            _queryNode.run();
        } */
    }

    @Test
    public void update(){
        JTable fooTable = _queryNode.getTableForProcess("fooProcess");
        assertNotNull(fooTable);
        assertEquals(2, fooTable.getRowCount());
        JTable barTable = _queryNode.getTableForProcess("barProcess");
        assertNotNull(barTable);
        assertEquals(1, barTable.getRowCount());
        assertEquals(2, getNumberOfTables());
    }

    @Test
    public void getTableForProcess(){
        assertNotNull(_queryNode.getTableForProcess("fooProcess"));
        assertNotNull(_queryNode.getTableForProcess("barProcess"));
        assertEquals(2, getNumberOfTables());
        assertNotNull(_queryNode.getTableForProcess("terProcess"));
        assertEquals(3, getNumberOfTables());
    }

    private int getNumberOfTables() {
        return 0; // ((HashMap<String, Table>)ReflectionUtils.getField(_queryNode, "_tables")).size();
    }
}
