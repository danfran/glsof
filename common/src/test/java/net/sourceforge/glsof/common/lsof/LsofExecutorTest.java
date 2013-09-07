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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.MockitoAnnotations.initMocks;

public class LsofExecutorTest {

    private LsofExecutor _parser;

    private List<String[]> _rows;

    @Mock
    private LsofExecutorObserver _observer;

    @Before
    public void setUp() {
        initMocks(this);
        _rows = new LinkedList<String[]>();
        _parser = new LsofExecutorImpl(_observer);
    }

    @Test
    public void createRowsForOneProcess() throws UnsupportedEncodingException, RemoteException {
        final String lsofOutput = "p1\n" +
                "g1\n" +
                "R0\n" +
                "cinit\n" +
                "u0\n" +
                "Lroot\n" +
                "fcwd\n" +
                "a \n" +
                "l \n" +
                "tunknown\n" +
                "n/proc/1/cwd (readlink: Permission denied)\n" +
                "frtd\n" +
                "a \n" +
                "l \n" +
                "tunknown\n" +
                "n/proc/1/root (readlink: Permission denied)\n" +
                "ftxt\n" +
                "a \n" +
                "l \n" +
                "tunknown\n" +
                "n/proc/1/exe (readlink: Permission denied)\n" +
                "fNOFD\n" +
                "a \n" +
                "l \n" +
                "n/proc/1/fd (opendir: Permission denied)\n";

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                _rows.add((String[])invocation.getArguments()[0]);
                return null;
            }}).when(_observer).flush(any(String[].class));
        _parser.fetch(new ByteArrayInputStream(lsofOutput.getBytes("UTF-8")));

        String[][] expected = new String[][]{
                {"init", "1", "", "1", "0", "root", "cwd  ", "unknown", "", "", "", "", "/proc/1/cwd (readlink: Permission denied)"},
                {"init", "1", "", "1", "0", "root", "rtd  ", "unknown", "", "", "", "", "/proc/1/root (readlink: Permission denied)"},
                {"init", "1", "", "1", "0", "root", "txt  ", "unknown", "", "", "", "", "/proc/1/exe (readlink: Permission denied)"},
                {"init", "1", "", "1", "0", "root", "NOFD  ", "", "", "", "", "", "/proc/1/fd (opendir: Permission denied)"}
        };

        assertTrue(Arrays.deepEquals(expected, _rows.toArray(new String[_rows.size()][])));

    }

    @Test
    public void createRowsWithNetworkConnections() throws UnsupportedEncodingException, RemoteException {
        final String lsofOutput = "p18405\n" +
                "g9660\n" +
                "R1\n" +
                "cdropbox\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f26\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x2;0x0\n" +
                "d7829644\n" +
                "o0t0\n" +
                "PUDP\n" +
                "n*:17500\n" +
                "f29\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x802;0x0\n" +
                "d7829647\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n*:17500\n" +
                "f30\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x802;0x0\n" +
                "d11261886\n" +
                "o0t0\n" +
                "PUDP\n" +
                "n127.0.0.1:33565->127.0.1.1:domain\n" +
                "p19025\n" +
                "g18477\n" +
                "R1\n" +
                "cubuntu-geoip-pr\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f11\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x80802;0x0\n" +
                "d11075706\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n192.168.1.3:39137->91.189.89.144:http\n" +
                "p19390\n" +
                "g18477\n" +
                "R1\n" +
                "cgvfsd-http\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f9\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x80802;0x0\n" +
                "d10652825\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n192.168.1.3:36710->79.140.82.74:https\n" +
                "p20238\n" +
                "g18438\n" +
                "R1\n" +
                "cchrome\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f113\n" +
                "au\n" +
                "l \n" +
                "tIPv4\n" +
                "G0x802;0x0\n" +
                "d11261865\n" +
                "o0t0\n" +
                "PUDP\n" +
                "n127.0.0.1:47939->127.0.1.1:domain\n" +
                "p26419\n" +
                "g18438\n" +
                "R26385\n" +
                "cjava\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f78\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11259902\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n127.0.0.1:43357->127.0.0.1:44869\n" +
                "f142\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11122123\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n127.0.0.1:6942\n" +
                "f149\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11122180\n" +
                "o0t0\n" +
                "PUDP\n" +
                "n127.0.1.1:51559\n" +
                "f150\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11122181\n" +
                "o0t0\n" +
                "PUDP\n" +
                "n*:9876\n" +
                "f380\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11122205\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n127.0.0.1:63342\n" +
                "f399\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11124098\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n*:43357\n" +
                "p26496\n" +
                "g18438\n" +
                "R26419\n" +
                "cjava\n" +
                "u1000\n" +
                "Lgnele\n" +
                "f27\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11124090\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n*:45676\n" +
                "f30\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11262332\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n127.0.0.1:44869->127.0.0.1:43357\n" +
                "f31\n" +
                "au\n" +
                "l \n" +
                "tIPv6\n" +
                "G0x2;0x0\n" +
                "d11124092\n" +
                "o0t0\n" +
                "PTCP\n" +
                "n*:36150";

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                _rows.add((String[])invocation.getArguments()[0]);
                return null;
            }}).when(_observer).flush(any(String[].class));
        _parser.fetch(new ByteArrayInputStream(lsofOutput.getBytes("UTF-8")));

        String[][] expected = new String[][]{
                {"dropbox", "18405", "", "9660", "1", "gnele", "26u ", "IPv4", "7829644", "0t0", "", "UDP", "*:17500"},
                {"dropbox", "18405", "", "9660", "1", "gnele", "29u ", "IPv4", "7829647", "0t0", "", "TCP", "*:17500"},
                {"dropbox", "18405", "", "9660", "1", "gnele", "30u ", "IPv4", "11261886", "0t0", "", "UDP", "127.0.0.1:33565->127.0.1.1:domain"},
                {"ubuntu-geoip-pr", "19025", "", "18477", "1", "gnele", "11u ", "IPv4", "11075706", "0t0", "", "TCP", "192.168.1.3:39137->91.189.89.144:http"},
                {"gvfsd-http", "19390", "", "18477", "1", "gnele", "9u ", "IPv4", "10652825", "0t0", "", "TCP", "192.168.1.3:36710->79.140.82.74:https"},
                {"chrome", "20238", "", "18438", "1", "gnele", "113u ", "IPv4", "11261865", "0t0", "", "UDP", "127.0.0.1:47939->127.0.1.1:domain"},
                {"java", "26419", "", "18438", "26385", "gnele", "78u ", "IPv6", "11259902", "0t0", "", "TCP", "127.0.0.1:43357->127.0.0.1:44869"},
                {"java", "26419", "", "18438", "26385", "gnele", "142u ", "IPv6", "11122123", "0t0", "", "TCP", "127.0.0.1:6942"},
                {"java", "26419", "", "18438", "26385", "gnele", "149u ", "IPv6", "11122180", "0t0", "", "UDP", "127.0.1.1:51559"},
                {"java", "26419", "", "18438", "26385", "gnele", "150u ", "IPv6", "11122181", "0t0", "", "UDP", "*:9876"},
                {"java", "26419", "", "18438", "26385", "gnele", "380u ", "IPv6", "11122205", "0t0", "", "TCP", "127.0.0.1:63342"},
                {"java", "26419", "", "18438", "26385", "gnele", "399u ", "IPv6", "11124098", "0t0", "", "TCP", "*:43357"},
                {"java", "26496", "", "18438", "26419", "gnele", "27u ", "IPv6", "11124090", "0t0", "", "TCP", "*:45676"},
                {"java", "26496", "", "18438", "26419", "gnele", "30u ", "IPv6", "11262332", "0t0", "", "TCP", "127.0.0.1:44869->127.0.0.1:43357"},
                {"java", "26496", "", "18438", "26419", "gnele", "31u ", "IPv6", "11124092", "0t0", "", "TCP", "*:36150"}
        };

        assertTrue(Arrays.deepEquals(expected, _rows.toArray(new String[_rows.size()][])));
    }

}
