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
package net.sourceforge.glsof.common.lsof;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LsofExecutorImpl implements LsofExecutor {

    /* ordered by columns output (buffer) - 'T' is a special case not indexed */
    private static final Map<Character, Integer> LSOF_IDS;

    static {
        //"c", "p", "K", "g", "R", "uL", "fal", "t", "dD", "os", "k", "iP", "Sn"
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('c', 0);
        map.put('p', 1);
        map.put('K', 2);
        map.put('g', 3);
        map.put('R', 4);
        map.put('u', 5);
        map.put('L', 5);
        map.put('f', 6);
        map.put('a', 6);
        map.put('l', 6);
        map.put('t', 7);
        map.put('d', 8);
        map.put('D', 8);
        map.put('o', 9);
        map.put('s', 9);
        map.put('k', 10);
        map.put('i', 11);
        map.put('P', 11);
        map.put('S', 12);
        map.put('n', 12);

        LSOF_IDS = Collections.unmodifiableMap(map);
    }

    private boolean _running;

    private final LsofExecutorObserver _observer;

    public LsofExecutorImpl(LsofExecutorObserver observer) {
        _observer = observer;
    }

    @Override
    public void fetch(InputStream is) {
        _running = true;
        parse(new BufferedReader(new InputStreamReader(is)));
        _running = false;
    }

    private void parse(final BufferedReader bufferedReader) {
        char id;
        Integer index;
        String line;
        boolean parsedFD = false;
        String tmpFD = "";
        String[] buffer = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", ""};
        try {
            while (_running && (line = bufferedReader.readLine()) != null) {
                id = line.charAt(0);
                switch (id) {
                    case 'p': // parse process
                        if (parsedFD) {
                            buffer[6] = tmpFD;
                            flush(buffer);
                        }
                        buffer = new String[]{"", line.substring(1), "", "", "", "", "", "", "", "", "", "", ""};
                        parsedFD = false;
                        break;

                    case 'f': // parse file descriptor
                        if (parsedFD) {
                            buffer[6] = tmpFD;
                            flush(buffer);
                            buffer = new String[]{buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5], "", "", "", "", "", "", ""};
                        }
                        tmpFD = line.substring(1);
                        parsedFD = true;
                        break;

                    case 'T':
                        buffer[12] += " (" + line.substring(1) + ")";
                        break;

                    default:
                        if ((index = LSOF_IDS.get(id)) != null) {
                            if (index == 6) {
                                tmpFD += line.substring(1);
                            } else {
                                buffer[index] = line.substring(1);
                            }
                        }
                        break;
                }
            }
            buffer[6] = tmpFD; // update file descriptor
            if (parsedFD) flush(buffer);
            if (_running) _observer.finished();
            bufferedReader.close();
        } catch (IOException e) {
            // flush some error message
        }
    }

    private void flush(final String[] buffer) {
        if (_running) try {
            _observer.flush(buffer);
        } catch (RemoteException e) {
            _running = false;
            System.out.println("Connection Error >>> Please Check The Connection Between Client And Server.");
        }
    }

    @Override
    public String parseErrors(final InputStream errorStream) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(errorStream));
        String message = null;
        try {
            String line;
            final StringBuffer sb = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null)
                if (line.startsWith("lsof: "))
                    sb.append(line.substring(6)).append("\n");
            bufferedReader.close();
            message = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void stop() {
        _running = false;
    }

}
