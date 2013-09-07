package net.sourceforge.glsof.common.main;

public interface Observer {

    public enum NOTIFY {
        CLEAR,
        AUTOSCROLL,
        START,
        STOP,
        CLOSE,
        TOGGLE
    }

    public void update(NOTIFY notify, Object... params);

}
