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

import net.sourceforge.glsof.common.fixtures.PreferencesFixtures;
import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.model.OtherPreferences;
import net.sourceforge.glsof.common.model.Preferences;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.initNLS;
import static org.junit.Assert.assertEquals;

public class LsofParametersBuilderTest extends PreferencesFixtures {

    private LsofParametersBuilder _builder = new LsofParametersBuilder();

    @BeforeClass
    public static void init() {
        initNLS("nl.common");
    }

    @Test
    public void createParametersForDefaultQueryPreferences() {
        checkLsofParameters("lsof -F -T +c0 -o -n -P", _builder.build(Preferences.from("dummyQuery")));
    }

    @Test
    public void createParametersForQueryPreferences() {
        checkLsofParameters(
                "lsof -F -T +c0 +D /path1 +d /path2 -d one -d two " +
                        "-g 123 -g ^456 -g ^789 -u ^123 -u daniele -i 6TCP@localhost:123 -i 4:987 " +
                        "-p ^123 -p 456 -p ^789 -c lsof -c ^glsof -l -s -a +L 5 -n -N -P -U -S 100 -b " +
                        "-- /home/daniele /usr/lib/libXdmcp.so.6.0.0",
                _builder.build(createPreferences()));
    }

    @Test
    public void directory2LsofParameters() {
        checkLsofParametersForOneCommand(createDirectoryPage(), " +D /path1 +d /path2 -o -n -P");
    }

    @Test
    public void fileDescriptor2LsofParameters() {
        checkLsofParametersForOneCommand(createFileDescriptorPage(), " -d one -d two -o -n -P");
    }

    @Test
    public void fileDescriptorWithExclude2LsofParameters() {
        checkLsofParametersForOneCommand(createFileDescriptorPageWithExclusions(), " -d ^one -d ^two -o -n -P");
    }

    @Test
    public void path2LsofParameters() {
        checkLsofParametersForOneCommand(createPathPage(), " -o -n -P -- /home/daniele /usr/lib/libXdmcp.so.6.0.0");
    }

    @Test
    public void pgid2LsofParameters() {
        checkLsofParametersForOneCommand(createPGIDPage(), " -g 123 -g ^456 -g ^789 -o -n -P");
    }

    @Test
    public void pgid2LsofParameters_commaSeparated() {
        checkLsofParametersForOneCommand(createPGIDPage2(), " -g ^456,^789,^123 -o -n -P");
    }

    @Test
    public void login2LsofParameters() {
        checkLsofParametersForOneCommand(createLoginPage(), " -u ^123 -u daniele -o -n -P");
    }

    @Test
    public void login2LsofParameters_commaSeparated() {
        checkLsofParametersForOneCommand(createLoginPage2(), " -u ^123,^nobody,^ntpd -u daniele,root -o -n -P");
    }

    @Test
    public void network2LsofParameters() {
        checkLsofParametersForOneCommand(createNetworkPage(), " -i 6TCP@localhost:123 -i 4:987 -o -n -P");
    }

    @Test
    public void pid2LsofParameters() {
        checkLsofParametersForOneCommand(createPIDPage(), " -p ^123 -p 456 -p ^789 -o -n -P");
    }

    @Test
    public void pid2LsofParameters_commaSeparated() {
        checkLsofParametersForOneCommand(createPIDPage2(), " -p ^123,^456,^789 -o -n -P");
    }

    @Test
    public void process2LsofParameters() {
        checkLsofParametersForOneCommand(createProcessPage(), " -c lsof -c ^glsof -o -n -P");
    }

    @Test
    public void other2LsofParameters() {
        assertEquals("lsof -F -T +c0 -l -s -a +L 5 -n -N -P -U -S 100 -b",
                _builder.build(createPreferencesWithEmptyFilters(createOtherPage())));
    }

    private void checkLsofParameters(String expected, String params) {
        assertEquals(1, params.split("  ").length);
        assertEquals(expected, params);
    }

    private void checkLsofParametersForOneCommand(List<Filter> filters, String params) {
        Preferences p = createPreferencesWithEmptyFilters(new OtherPreferences());
        p.setFilters(filters);
        assertEquals("lsof -F -T +c0" + params, _builder.build(p));
    }

    private Preferences createPreferencesWithEmptyFilters(OtherPreferences op) {
        Preferences p = new Preferences();
        p.setFilters(new ArrayList<Filter>());
        p.setOtherPreferences(op);
        return p;
    }

}