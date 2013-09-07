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

public abstract class AbstractQueriesSearchFixtures extends QueriesFixtures {

    private final static String FIRST_QUERY_NAME = "first query";

    private final static String SECOND_QUERY_NAME = "second query";

/*    @Mock
    protected MessageBox _messageBox;

    protected QueriesSearchBar _searchBar;
    
    protected Map<String, QueryNode> _queriesMap;

    protected abstract void initSearcher();

    @Before
    public void setUp(){
        initMocks(this);
        createQueries();
        initSearcher();
    }

    private void createQueries() {
        _queriesMap = new LinkedHashMap<String, QueryNode>();
        _queriesMap.put(FIRST_QUERY_NAME, createFirstQuery());
        _queriesMap.put(SECOND_QUERY_NAME, createSecondQuery());
        _searchBar = new QueriesSearchBar(new Shell(), _queryTree, _queriesMap);
        ReflectionUtils.setField(_searchBar, "_messageBox", _messageBox);
    }

    private QueryNode createFirstQuery() {
        QueryNode firstQuery = createQuery(FIRST_QUERY_NAME, QUERY_1_PROCESSES);
        ReflectionUtils.setField(firstQuery, "_tables", createFirstQueryTables());
        return firstQuery;
    }

    private QueryNode createSecondQuery() {
        QueryNode secondQuery = createQuery(SECOND_QUERY_NAME, QUERY_2_PROCESSES);
        ReflectionUtils.setField(secondQuery, "_tables", createSecondQueryTables());
        return secondQuery;
    }

    private QueryNode createQuery(String queryName, String ... processesNames) {
        TreeItem queryTreeItem = new TreeItem(_queryTree, SWT.NONE);
        queryTreeItem.setText(queryName);
        createQueryItems(queryTreeItem, processesNames);
        Shell tablesContainer = new Shell();
        return new QueryNode(new Composite(tablesContainer, SWT.NONE), tablesContainer, queryTreeItem, new Logger());
    }
  */
}
