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


import java.util.HashMap;
import java.util.Map;

class QueryStatistics {

    private int _totalRowsCount = 0;

    private Map<String, Integer> _processes = new HashMap<String, Integer>();

    private Map<String, Integer> _users = new HashMap<String, Integer>();

    /*public void calculate(TreeItem[] items, Map<String, Table> tables) {
        for (TreeItem item : items) {
            String processName = item.getText();
            Table table = tables.get(processName);
            int tableRows = table.getItemCount();
            _totalRowsCount += tableRows;
            _processes.put(processName, tableRows);
            calculateUsersFor(table);
        }

    }

    private void calculateUsersFor(Table table) {
        for (TableItem item : table.getItems()) {
            String user = item.getText(3);
            Integer countForUser = _users.get(user);
            _users.put(user, Integer.valueOf(1 + (countForUser==null ? 0 : countForUser)));
        }
    }

    public int getTotalRowsCount() {
        return _totalRowsCount;
    }

    public Map<String, Integer> getProcesses() {
        return _processes;
    }

    public Map<String, Integer> getUsers() {
        return _users;
    } */
}
