package net.sourceforge.glsof.common.lsof;

import java.io.InputStream;

public interface LsofExecutor {

    public abstract void fetch(InputStream is);

    public abstract String parseErrors(InputStream errorStream);

    public abstract void stop();

}