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
package net.sourceforge.glsof.common.testUtils;

import java.io.File;

public class BaseRepositoryIntegrationTest {
	
	private File _baseDir;

    protected BaseRepositoryIntegrationTest() {
    	_baseDir = new File(System.getProperty("java.io.tmpdir") + "/glsof-integration-tests");
    }
    
    protected File getBaseDir() {
        return _baseDir;
    }
    
    protected void removeAllFiles() {
        for (String name : _baseDir.list()) new File(_baseDir, name).delete();
    }
}

