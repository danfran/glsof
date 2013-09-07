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

import net.sourceforge.glsof.common.model.Preferences;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class PreferencesRepository extends XMLRepository<Preferences> {

    public PreferencesRepository(File baseDir) {
        super(baseDir);
    }

    public List<Boolean> getColumns(final String name) {
        return read(name).getColumns();
    }

    public void saveColumns(List<Boolean> views, final String name) {
        Preferences prefs = read(name);
        prefs.setColumns(views);
        save(prefs);
    }

    public Preferences read(final String name) {
    	return read(Preferences.class, name);
    }

    public void save(Preferences preferences) {
    	write(preferences, preferences.getName());
    }

    public boolean rename(String oldName, String newName) {
        if (fileExists(newName)) return false;
        Preferences prefs = read(oldName);
        prefs.setName(newName);
        save(prefs);
        remove(oldName);
        return true;
    }

    public List<String> findAllNames() {
        final String[] fileNames = _baseDir.list();
        List<String> names = new LinkedList<String>();
        if (fileNames == null) return names;
        for (String fileName : fileNames)
            if (fileName.endsWith(FILE_EXTENSION))
                names.add(fileName.substring(0, fileName.indexOf(FILE_EXTENSION)));
        return names;
    }
    
	public void copy(final String src, final String dest) {
		Preferences p = read(src);
		p.setName(dest);
		save(p);
	}

    public boolean remove(String name) {
        return getRealFile(name).delete();
    }

}
