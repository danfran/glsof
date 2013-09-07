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
package net.sourceforge.glsof.filemonitor.thread;

import net.sourceforge.glsof.common.lsof.LsofExecutor;
import net.sourceforge.glsof.common.lsof.LsofExecutorImpl;
import net.sourceforge.glsof.common.lsof.LsofParametersBuilder;
import net.sourceforge.glsof.filemonitor.MonitorTable;

import java.io.IOException;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;
import static net.sourceforge.glsof.filemonitor.repository.FileMonitorPreferencesRepository.getCurrentPreferences;


public class LocalMonitorTableUpdater extends AbstractMonitorTableUpdater {

    private LsofExecutor _lsofExecutor;

    public LocalMonitorTableUpdater(MonitorTable table) {
        super(table);
    }

    @Override
    protected Void doInBackground() {
        _lsofExecutor = new LsofExecutorImpl(this);
        final String lsofParameters = new LsofParametersBuilder().build(getCurrentPreferences());
        System.out.println(lsofParameters);
        Process process;

        while (!isCancelled()) {
            try {
                process = Runtime.getRuntime().exec(lsofParameters);
                _lsofExecutor.fetch(process.getInputStream());
                checkErrors(_lsofExecutor.parseErrors(process.getErrorStream()));
                process.destroy();
            } catch (IOException e) {
                checkErrors(NLS("Lsof_not_found"));
            }
        }

        return null;
    }

    @Override
    protected void stopLsofExecutor() {
        _lsofExecutor.stop();
    }

    private void checkErrors(String errors) {
        if (errors.isEmpty()) return;

        displayErrorMessage(errors);
        stop();
    }

}
