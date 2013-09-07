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
package net.sourceforge.glsof.common.i18n;

import java.util.ResourceBundle;

public class Messages {

    private static ResourceBundle[] RESOURCE_BUNDLES;
    public static String YES;
    public static String NO;
    public static String START;
    public static String STOP;

    public static void initNLS(String... resources) {
        RESOURCE_BUNDLES = new ResourceBundle[resources.length];
        for (int i = 0; i < resources.length; i++) {
            RESOURCE_BUNDLES[i] = ResourceBundle.getBundle(resources[i]);
        }
        YES = NLS("Yes");
        NO = NLS("No");
        START = NLS("Start");
        STOP = NLS("Stop");
    }

    public static String NLS(String key) {
        for (ResourceBundle rb : RESOURCE_BUNDLES) {
            if (rb.containsKey(key)) return rb.getString(key);
        }
        return "";
    }
}
