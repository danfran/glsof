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
package net.sourceforge.glsof.queries.stats;

import net.sourceforge.glsof.queries.fixtures.QueriesFixtures;
import org.junit.Before;
import org.junit.Test;

public class QueryStatisticsTest extends QueriesFixtures {

    private QueryStatistics _stats;

    @Before
    public void setUp(){
        _stats = new QueryStatistics();
    }

    @Test
    public void countsForADummyQuery(){
        /*_stats.calculate(createQueryItems(new TreeItem(_queryTree, SWT.NONE), QUERY_1_PROCESSES), createFirstQueryTables());
        assertEquals(8, _stats.getTotalRowsCount());
        Map<String, Integer> processes = _stats.getProcesses();
        assertEquals(5, processes.get("a process").intValue());
        assertEquals(3, processes.get("foo-proc").intValue());
        Map<String, Integer> users = _stats.getUsers();
        assertEquals(3, users.get("user1").intValue());
        assertEquals(1, users.get("user2").intValue());
        assertEquals(2, users.get("user3").intValue());
        assertEquals(2, users.get("user4").intValue());*/
    }

    @Test
    public void countsForAnotherDummyQuery(){
        /*_stats.calculate(createQueryItems(new TreeItem(_queryTree, SWT.NONE), QUERY_2_PROCESSES), createSecondQueryTables());
        assertEquals(7, _stats.getTotalRowsCount());
        Map<String, Integer> processes = _stats.getProcesses();
        assertEquals(2, processes.get("user4").intValue());
        assertEquals(3, processes.get("foo-proc").intValue());
        assertEquals(2, processes.get("slime").intValue());
        Map<String, Integer> users = _stats.getUsers();
        assertEquals(3, users.get("user1").intValue());
        assertEquals(2, users.get("user2").intValue());
        assertEquals(1, users.get("user3").intValue());
        assertEquals(1, users.get("user4").intValue());*/
    }

}
