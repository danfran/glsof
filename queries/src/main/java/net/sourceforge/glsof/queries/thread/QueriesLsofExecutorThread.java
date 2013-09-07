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
package net.sourceforge.glsof.queries.thread;

import net.sourceforge.glsof.common.lsof.LsofExecutor;
import net.sourceforge.glsof.common.lsof.LsofExecutorImpl;
import net.sourceforge.glsof.common.lsof.LsofParametersBuilder;
import net.sourceforge.glsof.common.model.Preferences;

import java.util.Observer;

public class QueriesLsofExecutorThread implements Runnable {

    private final LsofExecutor _lsofExecutor = new LsofExecutorImpl(null);

    private final Preferences _parameters;

    public QueriesLsofExecutorThread(final Observer observer, final Preferences parameters) {
        _parameters = parameters;
    }

    public void run() {
        final String lsofParameters = new LsofParametersBuilder().build(_parameters);
        _lsofExecutor.fetch(null);
    }

    public void stop() {
        _lsofExecutor.stop();
    }

    public boolean isRunning() {
        return true;
//        return _lsofExecutor.isRunning();
    }

}
