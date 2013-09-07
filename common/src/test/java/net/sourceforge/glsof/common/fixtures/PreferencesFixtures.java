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
package net.sourceforge.glsof.common.fixtures;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.model.OtherPreferences;
import net.sourceforge.glsof.common.model.Preferences;
import net.sourceforge.glsof.common.testUtils.BaseRepositoryIntegrationTest;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static net.sourceforge.glsof.common.preferences.LsofParameterType.*;

public class PreferencesFixtures extends BaseRepositoryIntegrationTest {
	
	protected void addFilters(Preferences p) {
		final List<Filter> filters = new LinkedList<Filter>();
        for (String type : new String[] { PROCESS.getId(), NETWORK.getId(), ID.getId(), FD.getId(), DIRECTORY.getId(), PID.getId(), PGID.getId(), PATH.getId() }) {
        	Filter filter = new Filter();
        	filter.setType(type);
        	filter.setValues(Arrays.asList(new String[]{ type + " sample value", type + " another value" }));
        	filters.add(filter);
        }
        p.setFilters(filters);
	}

    protected Preferences createPreferences() {
        final Preferences prefs = new Preferences();
        prefs.getFilters().addAll(createDirectoryPage());
        prefs.getFilters().addAll(createFileDescriptorPage());
        prefs.getFilters().addAll(createPathPage());
        prefs.getFilters().addAll(createPGIDPage());
        prefs.getFilters().addAll(createLoginPage());
        prefs.getFilters().addAll(createNetworkPage());
        prefs.getFilters().addAll(createPIDPage());
        prefs.getFilters().addAll(createProcessPage());
        prefs.setOtherPreferences(createOtherPage());
        return prefs;
    }
    
    private Filter createFilter(String type, String... values) {
    	final Filter page = new Filter();
        page.setType(type);
        page.setValues(Arrays.asList(values));
        return page;
    }
    
    protected List<Filter> createDirectoryPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(DIRECTORY.getId(), "/path1", "true", "false", "false"),
    			createFilter(DIRECTORY.getId(), "/path2", "false", "false", "false")});
    }

    protected List<Filter> createFileDescriptorPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(FD.getId(), "one", "false"),
    			createFilter(FD.getId(), "two", "false")});
    }
    
    protected List<Filter> createFileDescriptorPageWithExclusions() {
    	return Arrays.asList(new Filter[]{
    			createFilter(FD.getId(), "one", "true"),
    			createFilter(FD.getId(), "two", "true")});
    }

    protected List<Filter> createPathPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PATH.getId(), "/home/daniele"),
    			createFilter(PATH.getId(), "/usr/lib/libXdmcp.so.6.0.0")});
    }

    protected List<Filter> createPGIDPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PGID.getId(), "123", "false"),
    			createFilter(PGID.getId(), "456", "true"),
    			createFilter(PGID.getId(), "789", "true")});
    }
    
    protected List<Filter> createPGIDPage2() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PGID.getId(), "456,789,123", "true")});
    }

    protected List<Filter> createLoginPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(ID.getId(), "123", "true"),
    			createFilter(ID.getId(), "daniele", "false")});
    }
    
    protected List<Filter> createLoginPage2() {
    	return Arrays.asList(new Filter[]{
    			createFilter(ID.getId(), "123,nobody,ntpd", "true"),
    			createFilter(ID.getId(), "daniele,root", "false")});
    }

    protected List<Filter> createNetworkPage() {
        //"Address", "Port", "IPV", "Protocol"
    	return Arrays.asList(new Filter[]{
    			createFilter(NETWORK.getId(), "localhost", "123", "TCP", "6"),
    			createFilter(NETWORK.getId(), "", "987", "", "4")});
    }

    protected List<Filter> createPIDPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PID.getId(), "123", "true"),
    			createFilter(PID.getId(), "456", "false"),
    			createFilter(PID.getId(), "789", "true")});
    }
    
    protected List<Filter> createPIDPage2() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PID.getId(), "123,456,789", "true")});
    }

    protected List<Filter> createProcessPage() {
    	return Arrays.asList(new Filter[]{
    			createFilter(PROCESS.getId(), "lsof", "false"),
    			createFilter(PROCESS.getId(), "glsof", "true")});
    }

    protected OtherPreferences createOtherPage() {
        final OtherPreferences page = new OtherPreferences();
        page.setIdNumber(true);
        page.setSize(true);
        page.setAnd(true);
        page.setLinksFile(true);
        page.setLinksFileValue(5);
        page.setIpFormat(true);
        page.setNfs(true);
        page.setPortNumbers(true);
        page.setDomainSocket(true);
        page.setTimeout(true);
        page.setTimeoutValue(100);
        page.setIdNumber(true);
        page.setAvoid(true);
        return page;
    }
    
}
