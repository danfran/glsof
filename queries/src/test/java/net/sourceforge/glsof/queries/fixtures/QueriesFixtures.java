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
package net.sourceforge.glsof.queries.fixtures;


public class QueriesFixtures {

    protected final static String [] QUERY_1_PROCESSES = {
            "a process", "foo-proc"
    };

    protected final static String [] QUERY_2_PROCESSES = {
            "user4", "foo-proc", "slime"
    };

    private static String[][] _table1 = new String[][]{
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
            {"pid2", "pgid2", "ppid2", "user2", "fd2", "type2", "device2", "size2", "nlink2", "node2", "name2", "state2"},
            {"pid3", "pgid3", "ppid3", "user3", "fd3", "type3", "device3", "size3", "nlink3", "node3", "name3", "state3"},
            {"pid4", "pgid4", "ppid4", "user4", "fd4", "type4", "device4", "size4", "nlink4", "node4", "name4", "state4"},
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
    };

    private static String[][] _table2 = new String[][]{
            {"pid3", "pgid3", "ppid3", "user3", "fd3", "type3", "device3", "size3", "nlink3", "node3", "name3", "state3"},
            {"pid4", "pgid4", "ppid4", "user4", "fd4", "type4", "device4", "size4", "nlink4", "node4", "name4", "state4"},
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
    };

    private static String[][] _table3 = new String[][]{
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
            {"pid2", "pgid2", "ppid2", "user2", "fd2", "type2", "device2", "size2", "nlink2", "node2", "name2", "state2"},
    };

    private static String[][] _table4 = new String[][]{
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
            {"pid3", "pgid3", "ppid3", "user3", "fd3", "type3", "device3", "size3", "nlink3", "node3", "name3", "state3"},
            {"pid1", "pgid1", "ppid1", "user1", "fd1", "type1", "device1", "size1", "nlink1", "node1", "name1", "state1"},
    };
    
    private static String[][] _table5 = new String[][]{
            {"pid2", "pgid2", "ppid2", "user2", "fd2", "type2", "device2", "size2", "nlink2", "node2", "name2", "state2"},
            {"pid4", "pgid4", "ppid4", "user4", "fd4", "type4", "device4", "size4", "nlink4", "node4", "name4", "state4"},
    };

    /*protected Tree _queryTree = new Tree(new Shell(), SWT.NONE);

    protected Map<String, Table> _tables;

    protected Map<String, Table> createFirstQueryTables() {
        Map<String, Table> tables = new LinkedHashMap<String, Table>();
        tables.put(QUERY_1_PROCESSES[0], createTable(_table1));
        tables.put(QUERY_1_PROCESSES[1], createTable(_table2));
        return tables;
    }

    protected Map<String, Table> createSecondQueryTables() {
        Map<String, Table> tables = new LinkedHashMap<String, Table>();
        tables.put(QUERY_2_PROCESSES[0], createTable(_table3));
        tables.put(QUERY_2_PROCESSES[1], createTable(_table4));
        tables.put(QUERY_2_PROCESSES[2], createTable(_table5));
        return tables;
    }

    protected TreeItem[] createQueryItems(TreeItem treeItem, String ... processesNames) {
        TreeItem[] items = new TreeItem[processesNames.length];
        int i=0;
        for (String processName : processesNames) {
            items[i] = new TreeItem(treeItem, SWT.NONE);
            items[i].setText(processName);
            i++;
        }
        return items;
    }

    private Table createTable(String rows[][]) {
        Table table = new Table(new Shell(), SWT.NONE);
        for (int i=0; i<12; i++) new TableColumn(table, SWT.CENTER).setText("col" + i);
        for (String[] row : rows) new TableItem(table, SWT.NONE).setText(row);
        return table;
    }
      */
}
