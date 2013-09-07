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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLRepository<T> {

    protected static final String FILE_EXTENSION = ".xml"; 

    protected File _baseDir;

    protected XMLRepository(final File baseDir) {
        if (baseDir!=null && !baseDir.exists()) baseDir.mkdirs();
        _baseDir = baseDir;
    }
    
    protected T read(Class<T> clazz, String fileName) {
    	T obj = null;
		try {
			final Unmarshaller um = JAXBContext.newInstance(clazz).createUnmarshaller();
			obj = (T) um.unmarshal(new FileReader(getCompleteFileName(fileName)));
		} catch (Exception e) { e.printStackTrace(); }
		return obj;
    }

    protected void write(T obj, String fileName) {
    	Writer w = null;
    	try {
			final JAXBContext context = JAXBContext.newInstance(obj.getClass());
			final Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			w = new FileWriter(getCompleteFileName(fileName));
			m.marshal(obj, w);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { w.close();}
			catch (Exception e) {}
		}
    }
    
    protected String getCompleteFileName(String fileName) {
		return _baseDir.getPath() + File.separator + getFileName(fileName);
	}

    public boolean fileExists(final String filename) {
        return getRealFile(filename).exists();
    }

	protected File getRealFile(final String filename) {
		return new File(_baseDir, getFileName(filename));
	}

    protected String getFileName(final String fileName) {
        return fileName + FILE_EXTENSION;
    }

}
