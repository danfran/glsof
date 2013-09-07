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
package net.sourceforge.glsof.common.io;

import net.sourceforge.glsof.common.fixtures.PreferencesFixtures;
import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.model.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.sourceforge.glsof.common.preferences.LsofParameterType.*;
import static org.junit.Assert.*;


public class PreferencesRepositoryIntegrationTest extends PreferencesFixtures {

    private static final String PREF_NAME = "pref-test";

    private static final String ANOTHER_PREF_NAME = "another-pref-test";

    private PreferencesRepository _repository;

    @Before
    public void setUp() {
        _repository = new PreferencesRepository(getBaseDir());
    }

    @Test
    public void findAllQueryNames() {
        _repository.save(Preferences.from(PREF_NAME));
        _repository.save(Preferences.from(ANOTHER_PREF_NAME));
        List<String> ps = _repository.findAllNames();
        assertEquals(2, ps.size());
        assertTrue(ps.contains(PREF_NAME));
        assertTrue(ps.contains(ANOTHER_PREF_NAME));
        _repository.remove(ANOTHER_PREF_NAME);
        ps = _repository.findAllNames();
        assertEquals(1, ps.size());
        assertEquals(PREF_NAME, ps.get(0));
    }

    @Test
    public void rename() {
        _repository.save(Preferences.from(PREF_NAME));
        assertTrue(_repository.rename(PREF_NAME, ANOTHER_PREF_NAME));
        assertFalse(_repository.fileExists(PREF_NAME));
        assertTrue(_repository.fileExists(ANOTHER_PREF_NAME));
        _repository.save(Preferences.from(PREF_NAME));
        assertFalse(_repository.rename(ANOTHER_PREF_NAME, PREF_NAME));
        assertTrue(_repository.fileExists(ANOTHER_PREF_NAME));
        assertTrue(_repository.fileExists(PREF_NAME));
        _repository.remove(ANOTHER_PREF_NAME);
    }

    @Test
    public void createOkPressed() {
        Preferences prefs = Preferences.from(PREF_NAME);
        addFilters(prefs);
        _repository.save(prefs);
        assertTrue(_repository.fileExists(PREF_NAME));
        checkPreferencesCreate();
    }

    @Test
    public void createCancelPressed() {
        Preferences.from(PREF_NAME);
        assertFalse(_repository.fileExists(PREF_NAME));
    }

    @Test
    public void copy() {
        Preferences prefs = Preferences.from(PREF_NAME);
        _repository.save(prefs);
        _repository.copy(PREF_NAME, ANOTHER_PREF_NAME);
        assertTrue(_repository.fileExists(PREF_NAME));
        assertTrue(_repository.fileExists(ANOTHER_PREF_NAME));
        assertEquals(ANOTHER_PREF_NAME, _repository.read(ANOTHER_PREF_NAME).getName());
    }

    @Test
    public void saveAndUpdate() {
        Preferences prefs = Preferences.from(PREF_NAME);
        addFilters(prefs);
        _repository.save(prefs);
        checkPreferencesCreate();

        prefs = _repository.read(PREF_NAME);
        String[] values = {"a", "b", "c", "d"};
        initFileDescriptorPage(
                getFilterById(prefs, FD.getId()),
                new ArrayList<String>(Arrays.asList(values)));

        _repository.save(_repository.read(PREF_NAME));
        checkPreferencesCreate();
        checkFileDescriptorPage(prefs, values);
    }

    private void checkFileDescriptorPage(Preferences prefs, String[] values) {
        Filter page = getFilterById(prefs, FD.getId());
        assertEquals(FD.getId(), page.getType());
        List<String> rowValues = page.getValues();
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], rowValues.get(i));
        }
    }

    private void initFileDescriptorPage(Filter fdPage, ArrayList<String> row) {
        fdPage.setValues(row);
    }

    private void checkPreferencesCreate() {
        Preferences prefs = _repository.read(PREF_NAME);
        assertNotNull(prefs.getLocation());
        assertFalse(prefs.getLocation().isRemote());
        assertEquals("localhost", prefs.getLocation().getAddress());
        assertEquals(1099, prefs.getLocation().getPort());
        assertEquals(13, prefs.getColumns().size());
        assertEquals(8, prefs.getFilters().size());
        assertNotNull(prefs.getOtherPreferences());
        assertEquals(1, prefs.getOtherPreferences().getLinksFileValue());
        assertNotNull(getFilterById(prefs, FD.getId()));
        assertNotNull(getFilterById(prefs, DIRECTORY.getId()));
        assertNotNull(getFilterById(prefs, ID.getId()));
        assertNotNull(getFilterById(prefs, PGID.getId()));
        assertNotNull(getFilterById(prefs, PATH.getId()));
        assertNotNull(getFilterById(prefs, NETWORK.getId()));
        assertNotNull(getFilterById(prefs, PID.getId()));
        assertNotNull(getFilterById(prefs, PROCESS.getId()));
    }

    private Filter getFilterById(Preferences preferences, String id) {
        for (Filter filter : preferences.getFilters())
            if (filter.getType().equals(id))
                return filter;
        return null;
    }

    @After
    public void tearDown() {
        removeAllFiles();
    }

}
