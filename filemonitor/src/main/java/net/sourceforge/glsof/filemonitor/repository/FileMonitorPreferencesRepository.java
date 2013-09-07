package net.sourceforge.glsof.filemonitor.repository;

import net.sourceforge.glsof.common.io.PreferencesRepository;
import net.sourceforge.glsof.common.model.Preferences;

import java.io.File;
import java.util.List;

public class FileMonitorPreferencesRepository extends PreferencesRepository {

    private static Preferences _currentPreferences;

    public FileMonitorPreferencesRepository(File baseDir) {
        super(baseDir);
    }

    public static Preferences getCurrentPreferences() {
        return _currentPreferences;
    }

    public void load(String preferences) {
        if (fileExists(preferences)) _currentPreferences = read(preferences);
    }

    public void loadIfCurrentPreferenceName(String preferences) {
        if (isCurrentPreferencesName(preferences)) load(preferences);
    }

    public void delete(String preferences) {
        remove(preferences);
        if (isCurrentPreferencesName(preferences)) _currentPreferences = null;
    }

    public void updateColumns(List<Boolean> views) {
        if (_currentPreferences != null) {
            _currentPreferences.setColumns(views);
            save(_currentPreferences);
        }
    }

    public String copyPreferenceFrom(final String oldName) {
        int i = 0;
        String newName;
        do { newName = oldName + "(" + ++i + ")"; } while (fileExists(newName));
        copy(oldName, newName);
        return newName;
    }

    public boolean rename(String newName, String oldName) {
        if (newName.isEmpty() || fileExists(newName)) return false;
        boolean rename = super.rename(oldName, newName);
        if (rename && _currentPreferences.getName().equals(oldName)) _currentPreferences = read(newName);
        return rename;
    }

    public List<Boolean> loadColumns(String preferences) {
        _currentPreferences = read(preferences);
        return _currentPreferences.getColumns();
    }

    private boolean isCurrentPreferencesName(String preferences) {
        return _currentPreferences != null && _currentPreferences.getName().equals(preferences);
    }

}
