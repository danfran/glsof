package net.sourceforge.glsof.common.preferences;

import net.sourceforge.glsof.common.model.Filter;
import net.sourceforge.glsof.common.utils.GButton;

import javax.swing.*;
import java.util.List;

import static net.sourceforge.glsof.common.i18n.Messages.NLS;

public enum LsofParameterType {

    PROCESS("Process", " -c "),
    ID("ID_Login_name", " -u "),
    FD("File_Descriptor", " -d "),
    PID("PID", " -p ") {
        @Override
        public AbstractCommandDialog createDialog() {
            return new NumericExcludeCommandDialog(this);
        }
    },
    PGID("PGID", " -g ") {
        @Override
        public AbstractCommandDialog createDialog() {
            return new NumericExcludeCommandDialog(this);
        }
    },
    NETWORK("Network", " -i ") {
        @Override
        public void appendParameter(StringBuilder sb, Filter tv)  {
            sb.append(getParam());
            sb.append(tv.getValues().get(3).trim());
            sb.append(tv.getValues().get(2).trim());
            if (isNotEmpty(tv.getValues().get(0).trim())) sb.append("@").append(tv.getValues().get(0));
            if (isNotEmpty(tv.getValues().get(1).trim())) sb.append(":").append(tv.getValues().get(1));
        }

        private boolean isNotEmpty(String value) {
            return value!=null && !value.equals("");
        }

        @Override
        protected String getAction(Filter tv) {
            return INCLUDE;
        }

        @Override
        protected String getValue(Filter tv) {
            final List<String> values = tv.getValues();
            final StringBuffer value = new StringBuffer(values.get(0));
            if (!values.get(1).equals(" ")) value.append(" ").append(values.get(1));
            if (!values.get(2).equals(" ")) value.append(" ").append(values.get(2));
            if (!values.get(3).equals(" ")) value.append(" IPV").append(values.get(3));
            return value.toString();
        }

        @Override
        public AbstractCommandDialog createDialog() {
            return new NetworkCommandDialog(this);
        }
    },
    PATH("Path", "") {
        @Override
        public void appendParameter(StringBuilder sb, Filter tv) {
            sb.append(" ").append(tv.getValues().get(0));
        }

        @Override
        protected String getAction(Filter tv) {
            return INCLUDE;
        }

        @Override
        public AbstractCommandDialog createDialog() {
            return new PathCommandDialog(this);
        }
    },
    DIRECTORY("Directory", "") {
        @Override
        public void appendParameter(StringBuilder sb, Filter tv) {
            sb.append(followParameters(Boolean.valueOf(tv.getValues().get(2)), Boolean.valueOf(tv.getValues().get(3))));
            sb.append(Boolean.valueOf(tv.getValues().get(1)) ? " +D " : " +d ").append(tv.getValues().get(0));
        }

        private String followParameters(final boolean mountPoints, final boolean symbolicLinks) {
            if (!mountPoints & !symbolicLinks) return "";
            if (mountPoints & symbolicLinks) return " -x";
            if (mountPoints) return " -x f";
            return " -x l";
        }

        @Override
        protected String getAction(Filter tv) {
            return INCLUDE;
        }

        @Override
        protected String getValue(Filter tv) {
            final List<String> values = tv.getValues();
            final StringBuffer value = new StringBuffer(values.get(0));
            if (Boolean.valueOf(values.get(1))) value.append(" D");
            if (Boolean.valueOf(values.get(2))) value.append(" M");
            if (Boolean.valueOf(values.get(3))) value.append(" L");
            return value.toString();
        }

        @Override
        public AbstractCommandDialog createDialog() {
            return new DirectoryCommandDialog(this);
        }
    };

    private static final String EXCLUDE = "Exclude";
    private static final String INCLUDE = "Include";

    private String _id;
    private String _param;

    LsofParameterType(String id, String param) {
        _id = id;
        _param = param;
    }

    public String getId() {
        return _id;
    }

    public String getParam() {
        return _param;
    }

    public JButton createButton() {
        return new GButton("+ " + getLabel()).tooltip(NLS(_id +"_tip")).get();
    }

    public String getLabel() {
        return NLS(_id);
    }

    public static LsofParameterType getParameterType(String id) {
        for (LsofParameterType type : LsofParameterType.values()) {
            if (type.getId().equals(id))
                return type;
        }
        return null;
    }

    public void appendParameter(StringBuilder sb, Filter tv) {
        sb.append(getParam());
        if (tv.getValues().get(1).equals("true"))
            sb.append("^").append(tv.getValues().get(0).replaceAll(",", ",^"));
        else
            sb.append(tv.getValues().get(0));
    }

    public Object[] toTableRow(Filter tv) {
        return new Object[] { NLS(tv.getType()), getValue(tv).replaceAll("^\\s+", ""), getAction(tv), "Edit", "Remove", tv };
    }

    protected String getAction(Filter tv) {
        return getInclusionLabel(tv.getValues().get(1));
    }

    public String getInclusionLabel(String bool) {
        return Boolean.valueOf(bool) ? EXCLUDE : INCLUDE;
    }

    protected String getValue(Filter tv) {
        return new StringBuffer(tv.getValues().get(0)).toString();
    }

    public AbstractCommandDialog createDialog() {
        return new TextExcludeCommandDialog(this);
    }

}