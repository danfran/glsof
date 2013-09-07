package net.sourceforge.glsof.common.lsof;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.model.Preferences;

import static net.sourceforge.glsof.common.preferences.LsofParameterType.PATH;
import static net.sourceforge.glsof.common.preferences.LsofParameterType.getParameterType;


public class LsofParametersBuilder {
	
	private static final String COMMAND = "lsof -F -T +c0";

    public String build(Preferences preferences) {
        final StringBuilder sb1 = new StringBuilder(COMMAND);
        final StringBuilder sb2 = new StringBuilder(); // Path
        for (Filter tv : preferences.getFilters()) {
            final String type = tv.getType();
            final boolean isPathType = type.equals(PATH.getId());
            getParameterType(type).appendParameter(isPathType ? sb2 : sb1, tv);
        }
        preferences.getOtherPreferences().toParameters(sb1);
        if (sb2.length() > 0) sb1.append(" --").append(sb2);
        return sb1.toString();
    }

}
